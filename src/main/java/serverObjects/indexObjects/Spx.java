package serverObjects.indexObjects;

import DataUpdater.DataUpdaterService;
import IDDE.DDEHandler;
import IDDE.DDEReader_Spx;
import IDDE.DDEWriter_Spx;
import api.Manifest;
import charts.myCharts.Full_Chart_4;
import charts.myCharts.FuturesChartLong_300;
import dataBase.mySql.MySqlService;
import dataBase.mySql.dataUpdaters.DataBaseHandler_Spx;
import dataBase.mySql.dataUpdaters.IDataBaseHandler;
import exp.ExpStrings;
import jibeDataGraber.*;
import roll.Roll;
import roll.RollEnum;
import roll.RollHandler;
import roll.RollPriceEnum;
import serverObjects.ApiEnum;
import java.util.HashMap;
import java.util.Map;

public class Spx extends INDEX_CLIENT_OBJECT {

    static Spx client = null;

    // Constructor
    public Spx() {
        setName("spx");
        setId_name("spx500");
        setMySqlService(new MySqlService(this, new DataBaseHandler_Spx(this)));
        setDdeHandler(new DDEHandler(this, new DDEReader_Spx(this), new DDEWriter_Spx(this)));
        setDataUpdaterService(new DataUpdaterService(this));

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
            map.put(DecisionsFuncFactory.DF_3, new DecisionsFunc(DecisionsFuncFactory.DF_3, "data.spx500_decision_func", 3, 503));
            map.put(DecisionsFuncFactory.DF_5, new DecisionsFunc(DecisionsFuncFactory.DF_5, "data.spx500_decision_func", 3, 505));
            map.put(DecisionsFuncFactory.DF_7, new DecisionsFunc(DecisionsFuncFactory.DF_7, "data.spx500_decision_func", 3, 507));
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
            FuturesChartLong_300 chart = new FuturesChartLong_300(this);
            chart.createChart();
            
            Full_Chart_4 full_chart_4 = new Full_Chart_4(this);
            full_chart_4.createChart();
        }
    }

    @Override
    public double getTheoAvgMargin() {
        return 0.05;
    }
}
