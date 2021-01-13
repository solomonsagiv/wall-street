package exp;

import charts.myChart.MyTimeSeries;
import locals.IJson;
import locals.L;
import myJson.MyJson;
import options.JsonStrings;
import options.Options;
import options.OptionsDDeCells;
import options.optionsCalcs.IOptionsCalcs;
import serverObjects.BASE_CLIENT_OBJECT;
import tws.TwsContractsEnum;

import java.net.UnknownHostException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Exp implements IJson {

    // Variables
    protected BASE_CLIENT_OBJECT client;
    protected LocalDate toDay = LocalDate.now( );
    protected LocalDate expDate;
    protected double calcFut = 0;
    protected double calcFutBid = 0;
    protected double calcFutAsk = 0;
    protected int futBidAskCounter = 0;
    protected double futDelta = 0;
    ExpData expData;
    List< Double > opFutList = new ArrayList<>( );
    List< Double > futList = new ArrayList<>( );

    MyTimeSeries opAvgFutSeries;
    MyTimeSeries opAvg15FutSeries;
    MyTimeSeries futBidAskCounterSeries;

    String expName;
    TwsContractsEnum twsContractsEnum;
    private double futureAskForCheck = 0;
    private double futureBidForCheck = 0;

    public Exp( BASE_CLIENT_OBJECT client, String expName ) {
        this.client = client;
        this.expName = expName;
        this.expData = new ExpData( expName, client );
        initSeries( );
    }

    public void initSeries() {
        opAvgFutSeries = new MyTimeSeries( "OpAvgFuture", client ) {
            @Override
            public double getData() throws UnknownHostException {
                return getOpAvgFut( );
            }
        };
        opAvg15FutSeries = new MyTimeSeries( "OpAvg15Future", client ) {
            @Override
            public double getData() throws UnknownHostException {
                return getOpAvgFut( 900 );
            }
        };
        futBidAskCounterSeries = new MyTimeSeries( "futBidAskCounter", client ) {
            @Override
            public double getData() throws UnknownHostException {
                return getFutBidAskCounter( );
            }
        };
    }

    // Functions
    public double getFutureOp() {
        return calcFut - client.getIndex( );
    }

    public double getOpAvgFut() throws UnknownHostException {
        double sum = 0;
        if ( !opFutList.isEmpty( ) ) {
            try {
                for ( int i = 0; i < opFutList.size( ); i++ ) {
                    sum += opFutList.get( i );
                }
            } catch ( Exception e ) {
                e.printStackTrace( );
            }
            return L.floor( sum / opFutList.size( ), 100 );
        } else {
            throw new NullPointerException( client.getName( ) + " op future list empty" );
        }
    }

    public double getOpAvgFut( int secondes ) {
        try {

            // If op future list < seconds
            if ( secondes > opFutList.size( ) - 1 ) {
                return getOpAvgFut( );
            }

            double sum = 0;

            for ( int i = opFutList.size( ) - secondes; i < opFutList.size( ); i++ ) {
                sum += opFutList.get( i );
            }

            return L.floor( sum / secondes, 100 );
        } catch ( Exception e ) {
            e.printStackTrace( );
            return 0;
        }
    }

    public double getOpFuture() {
        return calcFut - client.getIndex( );
    }

    public void setOpAvgFuture( double opAvg ) {
        int size = opFutList.size( );
        opFutList.clear( );

        for ( int i = 0; i < size; i++ ) {
            opFutList.add( opAvg );
        }
    }

    public LocalDate getExpDate() {
        return expDate;
    }

    public double getCalcFut() {
        return calcFut;
    }

    public void setCalcFut( double calcFut ) {
        if ( calcFut > 1 ) {
            this.calcFut = calcFut;
        }
    }

    public double getCalcFutBid() {
        return calcFutBid;
    }

    public void setCalcFutBid( double calcFutBid ) {

        // If increment state
        if ( calcFutBid > this.calcFutBid && futureAskForCheck == this.calcFutAsk && client.isStarted( ) ) {
            futBidAskCounter++;
        }
        this.calcFutBid = calcFutBid;

        // Ask for bid change state
        futureAskForCheck = this.calcFutAsk;

    }

    public double getCalcFutAsk() {
        return calcFutAsk;
    }

    public void setCalcFutAsk( double calcFutAsk ) {

        // If increment state
        if ( calcFutAsk < this.calcFutAsk && futureBidForCheck == this.calcFutBid && client.isStarted( ) ) {
            futBidAskCounter--;
        }
        this.calcFutAsk = calcFutAsk;

        // Ask for bid change state
        futureBidForCheck = this.calcFutBid;

    }

    public int getFutBidAskCounter() {
        return futBidAskCounter;
    }

    public void setFutBidAskCounter( int futBidAskCounter ) {
        this.futBidAskCounter = futBidAskCounter;
    }

    public double getFutDelta() {
        return futDelta;
    }

    public MyTimeSeries getFutBidAskCounterSeries() {
        return futBidAskCounterSeries;
    }

    public MyTimeSeries getOpAvg15FutSeries() {
        return opAvg15FutSeries;
    }

    public MyTimeSeries getOpAvgFutSeries() {
        return opAvgFutSeries;
    }

    public List< Double > getOpFutList() {
        return opFutList;
    }

    public List< Double > getFutList() {
        return futList;
    }

    public TwsContractsEnum getTwsContractsEnum() {
        return twsContractsEnum;
    }

    public tws.MyContract getTwsContract() {
        return client.getTwsHandler( ).getMyContract( getTwsContractsEnum( ) );
    }

    public String getName() {
        return expName;
    }

    @Override
    public MyJson getAsJson() {
        MyJson json = new MyJson( );
        json.put( JsonStrings.fut, getCalcFut( ) );
        json.put( JsonStrings.futBid, getCalcFutBid( ) );
        json.put( JsonStrings.futAsk, getCalcFutAsk( ) );
        json.put( JsonStrings.futBidAskCounter, getFutBidAskCounter( ) );
        json.put( JsonStrings.expData, expData.getAsJson( ) );
        try {
            json.put( JsonStrings.opAvgFut, getOpAvgFut( ) );
        } catch ( Exception e ) {
            e.printStackTrace( );
        }
        return json;
    }

    @Override
    public void loadFromJson( MyJson json ) {
        setFutBidAskCounter( json.getInt( JsonStrings.futBidAskCounter ) );
        expData.loadFromJson( new MyJson( json.getJSONObject( JsonStrings.expData ) ) );
    }

    @Override
    public MyJson getResetJson() {
        MyJson json = new MyJson( );
        json.put( JsonStrings.expData, expData.getAsJson( ) );
        return json;
    }
}
