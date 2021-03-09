package serverObjects.indexObjects;

import IDDE.DDEHandler;
import IDDE.DDEReader_B;
import IDDE.DDEWriter_B;
import api.Manifest;
import baskets.BasketFinder;
import charts.myCharts.FuturesChart;
import dataBase.mySql.MySqlService;
import dataBase.mySql.dataUpdaters.DataBaseHandler_B;
import exp.E;
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

public class Ndx extends INDEX_CLIENT_OBJECT {

    static Ndx client = null;

    // Constructor
    public Ndx() {
        setName( "ndx" );
        setIndexBidAskMargin( .5 );
        setStrikeMargin( 5 );
        setIndexStartTime( LocalTime.of( 16, 31, 0 ) );
        setIndexEndTime( LocalTime.of( 23, 0, 0 ) );
        setFutureEndTime( LocalTime.of( 23, 15, 0 ) );
        setLogicService( new LogicService( this, ExpStrings.week ) );
        setMySqlService( new MySqlService( this, new DataBaseHandler_B( this ) ) );
        setBasketFinder( new BasketFinder( this, 80, 5000 ) );
        setDdeHandler( new DDEHandler( this, new DDEReader_B( this ), new DDEWriter_B( this ), "C:/Users/user/Desktop/[SPX.xlsx]Ndx" ) );
        roll( );
    }

    // get instance
    public static Ndx getInstance() {
        if ( client == null ) {
            client = new Ndx( );
        }
        return client;
    }

    private void roll() {
        rollHandler = new RollHandler( this );

        Roll quarter_quarterFar = new Roll( this, ExpStrings.e1, ExpStrings.e2, RollPriceEnum.FUTURE );
        rollHandler.addRoll( RollEnum.E1_E2, quarter_quarterFar );
    }

    @Override
    public void initExpHandler() {
        // Add to
        Exps exps = new Exps( this );
        exps.addExp( new ExpReg( this, ExpStrings.week ) );
        exps.addExp( new ExpReg( this, ExpStrings.month ) );
        exps.addExp( new E( this, ExpStrings.e1 ) );
        exps.addExp( new E( this, ExpStrings.e2 ) );
        exps.setMainExp( exps.getExp( ExpStrings.week ) );
        setExps( exps );
    }

    @Override
    public void setIndexBid( double indexBid ) {
        super.setIndexBid( indexBid );

        // Margin counter
        double bidMargin = index - indexBid;
        double askMargin = getIndexAsk( ) - index;
        double marginOfMarings = askMargin - bidMargin;

        if ( marginOfMarings > 0 ) {
            indBidMarginCounter += marginOfMarings;
        }
    }

    @Override
    public void setIndexAsk( double indexAsk ) {
        super.setIndexAsk( indexAsk );
        // Margin counter
        double bidMargin = index - getIndexBid( );
        double askMargin = indexAsk - index;
        double marginOfMarings = bidMargin - askMargin;

        if ( marginOfMarings > 0 && marginOfMarings < 5 ) {
            indAskMarginCounter += marginOfMarings;
        }
    }

    @Override
    public void setIndex( double index ) {
        super.setIndex( index );
    }

    @Override
    public ApiEnum getApi() {
        return ApiEnum.DDE;
    }

    @Override
    public void openChartsOnStart() {
        if ( Manifest.OPEN_CHARTS ) {
            FuturesChart chart = new FuturesChart( this );
            chart.createChart( );
        }
    }

    @Override
    public double getTheoAvgMargin() {
        return 0.05;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append( super.toString() );
        str.append( "Baskets= " + getBasketFinder().toString() );
        return str.toString();
    }

}
