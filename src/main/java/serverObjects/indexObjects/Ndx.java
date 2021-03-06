package serverObjects.indexObjects;

import IDDEReaderUpdater.DDEReaderUpdater_A;
import api.Manifest;
import charts.myCharts.FuturesChart;
import exp.ExpStrings;
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
        setLogicService( new LogicService( this, ExpStrings.day ) );
        setDdeReaderUpdater( new DDEReaderUpdater_A( this ) );
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

}
