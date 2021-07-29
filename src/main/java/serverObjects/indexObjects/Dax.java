package serverObjects.indexObjects;

import IDDE.DDEHandler;
import IDDE.DDEReader_Dax;
import IDDE.DDEWriter_Dax;
import api.Manifest;
import baskets.BasketFinder_3;
import charts.myCharts.FuturesChart;
import charts.myCharts.Index_baskets_chart;
import charts.myCharts.StocksDeltaChart;
import dataBase.mySql.MySqlService;
import dataBase.mySql.dataUpdaters.DataBaseHandler_Dax;
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
import stocksHandler.stocksDelta.StocksDeltaService;
import java.util.HashMap;
import java.util.Map;

public class Dax extends INDEX_CLIENT_OBJECT {

    static Dax client = null;

    @Override
    public double get_strike_in_money() {
        return 0;
    }

    // Constructor
    public Dax() {
        setName("dax");
        setId_name("dax");
//        setIndexBidAskMargin(.5);
//        setStrikeMargin(5);
//        setIndexStartTime(LocalTime.of(10, 0, 10));
//        setIndexEndTime(LocalTime.of(23, 0, 0));
//        setFutureEndTime(LocalTime.of(18, 45, 0));
        setMySqlService(new MySqlService(this, new DataBaseHandler_Dax(this)));
        setStocksDeltaService(new StocksDeltaService(this));
        setBasketFinder(new BasketFinder_3(this, 24, 3));
        setDdeHandler(new DDEHandler(this, new DDEReader_Dax(this), new DDEWriter_Dax(this)));
        setLogicService(new LogicService(this, ExpStrings.q1));
        roll();
    }

    // get instance
    public static Dax getInstance() {
        if (client == null) {
            client = new Dax();
        }
        return client;
    }

    @Override
    public void initExpHandler() {
        // Add to
        Exps exps = new Exps(this);
        exps.addExp(new ExpReg(this, ExpStrings.week));
        exps.addExp(new ExpReg(this, ExpStrings.month));
        exps.addExp(new E(this, ExpStrings.q1));
        exps.addExp(new E(this, ExpStrings.q2));
        exps.setMainExp(exps.getExp(ExpStrings.q1));
        setExps(exps);
    }

    private void roll() {
        rollHandler = new RollHandler(this);

        Roll roll = new Roll(this, ExpStrings.week, ExpStrings.month, RollPriceEnum.FUTURE);
        rollHandler.addRoll(RollEnum.WEEK_MONTH, roll);
    }

    @Override
    public DecisionsFuncHandler getDecisionsFuncHandler() {
        if (decisionsFuncHandler == null) {
            Map<String, DecisionsFunc> map = new HashMap<>();
            map.put(DecisionsFuncFactory.SPEED_900, DecisionsFuncFactory.get_decision_func(client, DecisionsFuncFactory.SPEED_900));
            decisionsFuncHandler = new DecisionsFuncHandler(map);
        }
        return decisionsFuncHandler;
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

            Index_baskets_chart basketsChart = new Index_baskets_chart(this);
            basketsChart.createChart();

            StocksDeltaChart stocksDeltaChart = new StocksDeltaChart(this);
            stocksDeltaChart.createChart();
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
