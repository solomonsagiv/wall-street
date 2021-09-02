package serverObjects.indexObjects;

import IDDE.DDEHandler;
import IDDE.DDEReader_Spx;
import IDDE.DDEWriter_Spx;
import api.Manifest;
import charts.myCharts.Full_Chart_2;
import charts.myCharts.Full_Charts;
import charts.myCharts.FuturesChart;
import dataBase.mySql.MySqlService;
import dataBase.mySql.dataUpdaters.DataBaseHandler_Spx;
import dataBase.mySql.dataUpdaters.IDataBaseHandler;
import exp.ExpStrings;
import jibeDataGraber.*;
import logic.LogicService;
import roll.Roll;
import roll.RollEnum;
import roll.RollHandler;
import roll.RollPriceEnum;
import serverObjects.ApiEnum;
import java.util.HashMap;
import java.util.Map;

public class Spx extends INDEX_CLIENT_OBJECT {

    static Spx client = null;

    @Override
    public double get_strike_in_money() {
        return 0;
    }

    // Constructor
    public Spx() {
        setName("spx");
        setId_name("spx500");
        setMySqlService(new MySqlService(this, new DataBaseHandler_Spx(this)));
        setDdeHandler(new DDEHandler(this, new DDEReader_Spx(this), new DDEWriter_Spx(this)));
        setLogicService(new LogicService(this, ExpStrings.q1));
        roll();
        tickSpeedService = new TickSpeedService(this, getMySqlService().getDataBaseHandler().get_table_loc(IDataBaseHandler.FUT_E1_TICK_SPEED));
        bidAskCounterGrabberService = new BidAskCounterGrabberService(this);
    }

    // get instance
    public static Spx getInstance() {
        if (client == null) {
            client = new Spx();
        }
        return client;
    }

    private void roll() {
        rollHandler = new RollHandler(this);

        Roll quarter_quarterFar = new Roll(this, ExpStrings.q1, ExpStrings.q2, RollPriceEnum.FUTURE);
        rollHandler.addRoll(RollEnum.E1_E2, quarter_quarterFar);
    }

    @Override
    public void setIndexBid(double indexBid) {
        super.setIndexBid(indexBid);

        // Margin counter
        double bidMargin = index - indexBid;
        double askMargin = getIndexAsk() - index;
        double marginOfMarings = askMargin - bidMargin;

        if (marginOfMarings > 0) {
            indBidMarginCounter += marginOfMarings;
        }
    }

    @Override
    public void setIndexAsk(double indexAsk) {
        super.setIndexAsk(indexAsk);
        // Margin counter
        double bidMargin = index - getIndexBid();
        double askMargin = indexAsk - index;
        double marginOfMarings = bidMargin - askMargin;

        if (marginOfMarings > 0 && marginOfMarings < 5) {
            indAskMarginCounter += marginOfMarings;
        }
    }

    @Override
    public DecisionsFuncHandler getDecisionsFuncHandler() {
        if (decisionsFuncHandler == null) {
            Map<String, DecisionsFunc> map = new HashMap<>();
            map.put(DecisionsFuncFactory.SPEED_900, new DecisionsFunc(DecisionsFuncFactory.SPEED_900, "data.research_spx500_501_speed_900"));
            map.put(DecisionsFuncFactory.ACC_900, new DecisionsFunc(DecisionsFuncFactory.ACC_900, "data.research_spx500_501_speed2_900"));
            map.put(DecisionsFuncFactory.ACC_300, new DecisionsFunc(DecisionsFuncFactory.ACC_300, "data.research_spx500_501_speed2_300"));
            decisionsFuncHandler = new DecisionsFuncHandler(map);
        }
        return decisionsFuncHandler;
    }

    @Override
    public void setIndex(double index) {
        super.setIndex(index);
    }

    @Override
    public ApiEnum getApi() {
        return ApiEnum.DDE;
    }

    @Override
    public void openChartsOnStart() {
        if (Manifest.OPEN_CHARTS) {
            FuturesChart chart = new FuturesChart(this);
            chart.createChart();

            Full_Charts full_charts = new Full_Charts(this);
            full_charts.createChart();

            Full_Chart_2 full_chart_2 = new Full_Chart_2(this);
            full_chart_2.createChart();
        }
    }

    @Override
    public double getTheoAvgMargin() {
        return 0.05;
    }
}
