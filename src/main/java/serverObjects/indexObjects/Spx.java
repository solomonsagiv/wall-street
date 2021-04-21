package serverObjects.indexObjects;

import IDDE.DDEHandler;
import IDDE.DDEReader_Spx;
import IDDE.DDEWriter_Spx;
import api.Manifest;
import charts.myCharts.FuturesChart;
import dataBase.mySql.MySqlService;
import dataBase.mySql.dataUpdaters.DataBaseHandler_Spx;
import exp.ExpStrings;
import logic.LogicService;
import roll.Roll;
import roll.RollEnum;
import roll.RollHandler;
import roll.RollPriceEnum;
import serverObjects.ApiEnum;

import java.time.LocalTime;

public class Spx extends INDEX_CLIENT_OBJECT {

    static Spx client = null;

    // Constructor
    public Spx() {
        setName("spx");
        setId_name("spx500");
        setIndexBidAskMargin(.5);
        setStrikeMargin(5);
        setIndexStartTime(LocalTime.of(16, 31, 0));
        setIndexEndTime(LocalTime.of(23, 0, 0));
        setFutureEndTime(LocalTime.of(23, 15, 0));
        setMySqlService(new MySqlService(this, new DataBaseHandler_Spx(this)));
        setDdeHandler(new DDEHandler(this, new DDEReader_Spx(this), new DDEWriter_Spx(this), "C:/Users/yosef/OneDrive/Desktop/Wall Street/[SPX.xlsx]Spx"));
        setLogicService(new LogicService(this, ExpStrings.q1));
        roll();
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
        }
    }

    @Override
    public double getTheoAvgMargin() {
        return 0.05;
    }

}
