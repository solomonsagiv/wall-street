package serverObjects.indexObjects;

import IDDE.DDEHandler;
import IDDE.DDEReader_Ndx;
import IDDE.DDEWriter_Ndx;
import api.Manifest;
import baskets.BasketFinder_3;
import charts.myCharts.FuturesChart;
import charts.myCharts.Index_baskets_chart;
import dataBase.mySql.MySqlService;
import dataBase.mySql.dataUpdaters.DataBaseHandler_Ndx;
import exp.E;
import exp.ExpReg;
import exp.ExpStrings;
import exp.Exps;
import jibeDataGraber.DecisionsFunc;
import jibeDataGraber.DecisionsFuncFactory;
import jibeDataGraber.DecisionsFuncHandler;
import logic.LogicService;
import roll.Roll;
import roll.RollEnum;
import roll.RollHandler;
import roll.RollPriceEnum;
import serverObjects.ApiEnum;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class Ndx extends INDEX_CLIENT_OBJECT {

    static Ndx client = null;

    @Override
    public double get_strike_in_money() {
        return 0;
    }

    // Constructor
    public Ndx() {
        setName("ndx");
        setId_name("ndx");
        setIndexBidAskMargin(.5);
        setStrikeMargin(5);
        setIndexStartTime(LocalTime.of(16, 31, 0));
        setIndexEndTime(LocalTime.of(23, 0, 0));
        setFutureEndTime(LocalTime.of(23, 15, 0));
        setMySqlService(new MySqlService(this, new DataBaseHandler_Ndx(this)));
        setBasketFinder(new BasketFinder_3(this, 80, 3));
        setDdeHandler(new DDEHandler(this, new DDEReader_Ndx(this), new DDEWriter_Ndx(this), "C:/Users/yosef/Desktop/[bbg index.xlsm]Ndx"));
        setLogicService(new LogicService(this, ExpStrings.q1));
        roll();
    }

    // get instance
    public static Ndx getInstance() {
        if (client == null) {
            client = new Ndx();
        }
        return client;
    }

    private void roll() {
        rollHandler = new RollHandler(this);

        Roll quarter_quarterFar = new Roll(this, ExpStrings.q1, ExpStrings.q2, RollPriceEnum.FUTURE);
        rollHandler.addRoll(RollEnum.E1_E2, quarter_quarterFar);
    }

    @Override
    public void initExpHandler() {

        Exps exps = new Exps(this);
        exps.addExp(new ExpReg(this, ExpStrings.day));
        exps.addExp(new ExpReg(this, ExpStrings.week));
        exps.addExp(new ExpReg(this, ExpStrings.month));
        exps.addExp(new E(this, ExpStrings.q1));
        exps.addExp(new E(this, ExpStrings.q2));
        exps.setMainExp(exps.getExp(ExpStrings.q1));
        setExps(exps);
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
            map.put(DecisionsFuncFactory.SPEED_900, new DecisionsFunc(DecisionsFuncFactory.SPEED_900, "data.research_ndx_speed_900"));
            map.put(DecisionsFuncFactory.ACC_900, new DecisionsFunc(DecisionsFuncFactory.ACC_900, "data.research_ndx_speed2_900"));
            map.put(DecisionsFuncFactory.ACC_300, new DecisionsFunc(DecisionsFuncFactory.ACC_300, "data.research_ndx_speed2_300"));
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
            FuturesChart chart = new FuturesChart(this, null, null);
            chart.createChart();

            Index_baskets_chart basketsChart = new Index_baskets_chart(this);
            basketsChart.createChart();
        }
    }

    @Override
    public double getTheoAvgMargin() {
        return 0.05;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(super.toString());
        str.append("Baskets 2= " + getBasketFinder().toString());
        return str.toString();
    }

}
