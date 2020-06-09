package exp;

import lists.MyChartList;
import locals.IJson;
import locals.L;
import myJson.MyJson;
import options.JsonStrings;
import options.Options;
import options.optionsCalcs.IOptionsCalcs;
import serverObjects.BASE_CLIENT_OBJECT;
import tws.MyContract;
import tws.TwsContractsEnum;

import java.net.UnknownHostException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Exp implements IJson, IOptionsCalcs {

    // Variables
    protected BASE_CLIENT_OBJECT client;
    ExpData expData;
    Options options;

    protected LocalDate toDay = LocalDate.now( );
    protected LocalDate expDate;

    protected double future = 0;
    protected double futureBid = 0;
    protected double futureAsk = 0;
    protected int futureBidAskCounter = 0;
    protected double futureDelta = 0;
    private int futureVolume = 0;

    List< Double > opFutureList = new ArrayList<>( );
    List< Double > futureList = new ArrayList<>( );
    MyChartList opAvgFutureList = new MyChartList( );
    MyChartList opAvg15FutureList = new MyChartList( );
    MyChartList futBidAskCounterList = new MyChartList( );

    // Constructor
    public Exp( BASE_CLIENT_OBJECT client ) {
        this.client = client;
        this.options = new Options( client, this );
    }

    // Functions
    public double getFutureOp() {
        return future - client.getIndex( );
    }

    public double getOpAvgFuture() throws UnknownHostException {
        double sum = 0;
        if ( !opFutureList.isEmpty( ) ) {
            try {
                for ( int i = 0; i < opFutureList.size( ); i++ ) {
                    sum += opFutureList.get( i );
                }
            } catch ( Exception e ) {
                e.printStackTrace( );
            }
            return L.floor( sum / opFutureList.size( ), 100 );
        } else {
            throw new NullPointerException( client.getName( ) + " op future list empty" );
        }
    }

    public double getOpAvgFuture( int secondes ) {
        try {

            // If op future list < seconds
            if ( secondes > opFutureList.size( ) - 1 ) {
                return getOpAvgFuture( );
            }

            double sum = 0;

            for ( int i = opFutureList.size( ) - secondes; i < opFutureList.size( ); i++ ) {
                sum += opFutureList.get( i );
            }

            return L.floor( sum / secondes, 100 );
        } catch ( Exception e ) {
            e.printStackTrace( );
            return 0;
        }
    }

    public double getOpFuture() {
        return future - client.getIndex( );
    }

    public void setOpAvgFuture( double opAvg ) {
        int size = opFutureList.size( );
        opFutureList.clear( );

        for ( int i = 0; i < size; i++ ) {
            opFutureList.add( opAvg );
        }
    }

    // Getters and setters
    public Options getOptions() {
        return options;
    }

    private double futureAskForCheck = 0;

    public void setFutureBid( double futureBid ) {

        // If increment state
        if ( futureBid > this.futureBid && futureAskForCheck == this.futureAsk && client.isStarted( ) ) {
            futureBidAskCounter++;
        }
        this.futureBid = futureBid;

        // Ask for bid change state
        futureAskForCheck = this.futureAsk;

    }

    private double futureBidForCheck = 0;

    public void setFutureAsk( double futureAsk ) {

        // If increment state
        if ( futureAsk < this.futureAsk && futureBidForCheck == this.futureBid && client.isStarted( ) ) {
            futureBidAskCounter--;
        }
        this.futureAsk = futureAsk;

        // Ask for bid change state
        futureBidForCheck = this.futureBid;

    }

    public LocalDate getExpDate() {
        return expDate;
    }

    public double getFuture() {
        return future;
    }

    public double getFutureBid() {
        return futureBid;
    }

    public double getFutureAsk() {
        return futureAsk;
    }

    public int getFutureBidAskCounter() {
        return futureBidAskCounter;
    }

    public void setFutureBidAskCounter( int futureBidAskCounter ) {
        this.futureBidAskCounter = futureBidAskCounter;
    }

    public double getFutureDelta() {
        return futureDelta;
    }

    public int getFutureVolume() {
        return futureVolume;
    }

    public List< Double > getOpFutureList() {
        return opFutureList;
    }

    public List< Double > getFutureList() {
        return futureList;
    }

    public MyChartList getOpAvgFutureList() {
        return opAvgFutureList;
    }

    public MyChartList getOpAvg15FutureList() {
        return opAvg15FutureList;
    }

    public MyChartList getFutBidAskCounterList() {
        return futBidAskCounterList;
    }

    @Override
    public MyJson getAsJson() {
        MyJson json = new MyJson( );
        json.put( JsonStrings.future, getFuture( ) );
        json.put( JsonStrings.futureBid, getFutureBid( ) );
        json.put( JsonStrings.futureAsk, getFutureAsk( ) );
        json.put( JsonStrings.futureBidAskCounter, getFutureBidAskCounter( ) );
        json.put( JsonStrings.options, getOptions( ).getAsJson( ) );
        json.put( JsonStrings.expData, expData.getAsJson() );
        try {
            json.put( JsonStrings.opAvgFuture, getOpAvgFuture( ) );
        } catch ( Exception e ) {
            e.printStackTrace( );
        }
        return json;
    }

    @Override
    public void loadFromJson( MyJson json ) {
        setFutureBidAskCounter( json.getInt( JsonStrings.futureBidAskCounter ) );
        options.loadFromJson( new MyJson( json.getJSONObject( JsonStrings.options ) ) );
        expData.loadFromJson( new MyJson( json.getJSONObject( JsonStrings.expData ) ) );
    }

    @Override
    public MyJson getResetJson() {
        MyJson json = new MyJson( );
        json.put( JsonStrings.expData, expData.getAsJson( ) );
        return json;
    }
}
