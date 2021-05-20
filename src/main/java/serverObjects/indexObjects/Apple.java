package serverObjects.indexObjects;

import IDDE.DDEHandler;
import IDDE.DDEReader_Apple;
import IDDE.DDEWriter_Apple;
import api.Manifest;
import charts.myCharts.FuturesChart;
import exp.ExpReg;
import exp.ExpStrings;
import exp.Exps;
import logic.LogicService;
import roll.Roll;
import roll.RollEnum;
import roll.RollHandler;
import roll.RollPriceEnum;
import serverObjects.ApiEnum;
import java.time.LocalTime;

public class Apple extends INDEX_CLIENT_OBJECT {

    static Apple client = null;

    // Constructor
    public Apple() {
        setName("apple");
        setId_name("apple");
        setIndexBidAskMargin(.5);
        setStrikeMargin(5);
        setIndexStartTime(LocalTime.of(16, 31, 0));
        setIndexEndTime(LocalTime.of(23, 0, 0));
        setFutureEndTime(LocalTime.of(23, 15, 0));
        setDdeHandler(new DDEHandler(this, new DDEReader_Apple(this), new DDEWriter_Apple(this), "C:/Users/yosef/OneDrive/Desktop/Wall Street/[SPX.xlsx]Aapl"));
        setLogicService(new LogicService(this, ExpStrings.week));
        roll();
    }

    // get instance
    public static Apple getInstance() {
        if (client == null) {
            client = new Apple();
        }
        return client;
    }

    @Override
    public void initExpHandler() {
        // Add to
        Exps exps = new Exps(this);
        exps.addExp(new ExpReg(this, ExpStrings.week));
        exps.addExp(new ExpReg(this, ExpStrings.month));
        exps.setMainExp(exps.getExp(ExpStrings.week));
        setExps(exps);
    }

    private void roll() {
        rollHandler = new RollHandler(this);

        Roll roll = new Roll(this, ExpStrings.week, ExpStrings.month, RollPriceEnum.FUTURE);
        rollHandler.addRoll(RollEnum.WEEK_MONTH, roll);
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

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(super.toString());
        str.append("Baskets 2= " + getBasketFinder().toString());
        return str.toString();
    }
}
