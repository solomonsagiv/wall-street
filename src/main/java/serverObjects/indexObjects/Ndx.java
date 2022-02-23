package serverObjects.indexObjects;

import DataUpdater.DataUpdaterService;
import IDDE.DDEHandler;
import IDDE.DDEReader_Ndx;
import IDDE.DDEWriter_Ndx;
import api.Manifest;
import baskets.BasketFinder_3;
import charts.myCharts.Full_Chart_4;
import charts.myCharts.FuturesChartLong_1000;
import dataBase.mySql.MySqlService;
import dataBase.mySql.dataUpdaters.DataBaseHandler_Ndx;
import exp.ExpStrings;
import jibeDataGraber.DecisionsFunc;
import jibeDataGraber.DecisionsFuncFactory;
import jibeDataGraber.DecisionsFuncHandler;
import roll.Roll;
import roll.RollEnum;
import roll.RollHandler;
import roll.RollPriceEnum;
import serverObjects.ApiEnum;

import java.util.HashMap;
import java.util.Map;

public class Ndx extends INDEX_CLIENT_OBJECT {

    static Ndx client = null;

    // Constructor
    public Ndx() {
        setName("ndx");
        setId_name("ndx");
        setMySqlService(new MySqlService(this, new DataBaseHandler_Ndx(this)));
        setBasketFinder(new BasketFinder_3(this, 80, 3));
        setDdeHandler(new DDEHandler(this, new DDEReader_Ndx(this), new DDEWriter_Ndx(this)));
        setDataUpdaterService(new DataUpdaterService(this));
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
            map.put(DecisionsFuncFactory.DF_N_DAY, new DecisionsFunc(DecisionsFuncFactory.DF_N_DAY, "data.ndx_decision_func", 4, 602));
            map.put(DecisionsFuncFactory.DF_DAY, new DecisionsFunc(DecisionsFuncFactory.DF_DAY, "data.ndx_decision_func", 4, 601));
            map.put(DecisionsFuncFactory.DF_N_DAY, new DecisionsFunc(DecisionsFuncFactory.DF_N_DAY_SPEED, "data.research_ndx_602_speed_300", 1));
            map.put(DecisionsFuncFactory.DF_DAY, new DecisionsFunc(DecisionsFuncFactory.DF_DAY_SPEED, "data.research_ndx_601_speed_300", 1));
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
            FuturesChartLong_1000 chart = new FuturesChartLong_1000(this);
            chart.createChart();

            Full_Chart_4 chart_4 = new Full_Chart_4(this);
            chart_4.createChart();
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
