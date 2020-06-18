package exp;

import charts.myChart.MyTimeSeries;
import lists.MyChartList;
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

    MyTimeSeries opAvgFutureSeries;
    MyTimeSeries opAvg15FutureSeries;
    MyTimeSeries futBidAskCounterSeries;

    ExpEnum expEnum;
    TwsContractsEnum contractsEnum;

    private Exp( BASE_CLIENT_OBJECT client, ExpEnum expEnum, TwsContractsEnum contractsEnum ) {
        this.client = client;
        this.expEnum = expEnum;
        this.contractsEnum = contractsEnum;
        initSeries( );
    }

    // Constructor
    public Exp( BASE_CLIENT_OBJECT client, ExpEnum expEnum, TwsContractsEnum contractsEnum, IOptionsCalcs iOptionsCalcs ) {
        this( client, expEnum, contractsEnum );
        this.options = new Options( client, this, iOptionsCalcs );
    }

    // Constructor
    public Exp( BASE_CLIENT_OBJECT client, ExpEnum expEnum, IOptionsCalcs iOptionsCalcs, OptionsDDeCells optionsDDeCells ) {
        this.client = client;
        this.expEnum = expEnum;
        this.options = new Options( client, this, iOptionsCalcs, optionsDDeCells );
    }

    public void initSeries() {
        opAvgFutureSeries = new MyTimeSeries( "OpAvgFuture" ) {
            @Override
            public double getData() throws UnknownHostException {
                return getOpAvgFuture( );
            }
        };
        opAvg15FutureSeries = new MyTimeSeries( "OpAvg15Future") {
            @Override
            public double getData() throws UnknownHostException {
                return getOpAvgFuture( 900 );
            }
        };
        futBidAskCounterSeries = new MyTimeSeries( "futBidAskCounter" ) {
            @Override
            public double getData() throws UnknownHostException {
                return getFutureBidAskCounter();
            }
        };
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

    public void setFuture( double future ) {
        this.future = future;
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

    public void setFutureVolume( int futureVolume ) {
        this.futureVolume = futureVolume;
    }

    public int getFutureVolume() {
        return futureVolume;
    }

    public MyTimeSeries getFutBidAskCounterSeries() {
        return futBidAskCounterSeries;
    }

    public MyTimeSeries getOpAvg15FutureSeries() {
        return opAvg15FutureSeries;
    }

    public MyTimeSeries getOpAvgFutureSeries() {
        return opAvgFutureSeries;
    }

    public List< Double > getOpFutureList() {
        return opFutureList;
    }

    public List< Double > getFutureList() {
        return futureList;
    }

    public ExpEnum getEnum() {
        return expEnum;
    }

    @Override
    public MyJson getAsJson() {
        MyJson json = new MyJson( );
        json.put( JsonStrings.future, getFuture( ) );
        json.put( JsonStrings.futureBid, getFutureBid( ) );
        json.put( JsonStrings.futureAsk, getFutureAsk( ) );
        json.put( JsonStrings.futureBidAskCounter, getFutureBidAskCounter( ) );
        json.put( JsonStrings.options, getOptions( ).getAsJson( ) );
        json.put( JsonStrings.expData, expData.getAsJson( ) );
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
