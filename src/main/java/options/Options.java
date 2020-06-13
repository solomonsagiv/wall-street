package options;

import com.ib.client.Types;
import exp.Exp;
import lists.MyChartList;
import locals.IJson;
import locals.L;
import myJson.MyJson;
import options.optionsCalcs.IOptionsCalcs;
import org.json.JSONObject;
import serverObjects.BASE_CLIENT_OBJECT;
import tws.MyContract;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Options implements IJson {

    OptionsDDeCells optionsDDeCells;
    List< Strike > strikes;
    HashMap< Integer, Option > optionsMap;
    BASE_CLIENT_OBJECT client;
    double bidMin = 0;
    double askMax = 0;
    private boolean requested = false;
    protected OptionsProps props;

    protected Exp exp;

    // Exp date
    LocalDate expDate;

    protected int baseID = 0;
    protected int minId = 0;
    protected int maxId = 0;
    protected MyContract twsContract;
    protected boolean gotData = false;

    private double contract = 0;
    protected double contractBid = 0;
    protected double contractAsk = 0;
    protected double currStrike = 0;
    protected int contractBidAskCounter = 0;

    List< Double > conList = new ArrayList<>( );
    List< Double > conBidList = new ArrayList<>( );
    List< Double > conAskList = new ArrayList<>( );
    List< Double > opAvgList = new ArrayList<>( );
    List< Double > opList = new ArrayList<>( );

    MyChartList conBidAskCounterList = new MyChartList( );
    MyChartList opAvgChartList = new MyChartList( );

    IOptionsCalcs iOptionsCalcs;

    public Options( BASE_CLIENT_OBJECT client, Exp exp, IOptionsCalcs iOptionsCalcs ) {
        this.client = client;
        this.exp = exp;
        this.iOptionsCalcs = iOptionsCalcs;

        strikes = new ArrayList<>( );
        optionsMap = new HashMap<>( );
        props = new OptionsProps();
    }

    public Options( BASE_CLIENT_OBJECT client, Exp exp, IOptionsCalcs iOptionsCalcs, OptionsDDeCells dDeCells ) {
        this( client, exp, iOptionsCalcs );
        this.optionsDDeCells = dDeCells;
    }

    public Call getCall( double targetStrike ) {
        for ( Strike strike : strikes ) {
            if ( targetStrike == strike.getStrike( ) ) {
                return strike.getCall( );
            }
        }
        return null;
    }

    public Put getPut( double targetStrike ) {
        for ( Strike strike : strikes ) {
            if ( targetStrike == strike.getStrike( ) ) {
                return strike.getPut( );
            }
        }
        return null;
    }

    public void initOptions() {

        double startStrike = client.getStartStrike( );
        double endStrike = client.getEndStrike( );

        int id = getBaseID( );

        for ( double strike = startStrike; strike < endStrike; strike += client.getStrikeMargin( ) ) {

            // ----- Call ------ //
            Call call = new Call( strike, id );

            MyContract contractCall = new MyContract( getTwsContract( ) );

            // MyTwsContract
            contractCall.setMyId( id );
            contractCall.strike( strike );
            contractCall.right( Types.Right.Call );

            client.getTwsHandler( ).addContract( contractCall );

            call.setMyContract( contractCall );

            setOption( call );
            id++;

            // ----- Put ------ //
            Put put = new Put( strike, id );

            MyContract contractPut = new MyContract( getTwsContract( ) );

            // MyTwsContract
            contractPut.setMyId( id );
            contractPut.strike( strike );
            contractPut.right( Types.Right.Put );
            client.getTwsHandler( ).addContract( contractPut );

            put.setMyContract( contractPut );

            setOption( put );
            id++;

        }
    }

    public void setOpValues( double val ) {
        if ( !opList.isEmpty( ) ) {
            double size = getOpList( ).size( );
            opList.clear( );
            for ( int i = 0; i < size; i++ ) {
                opList.add( val );
            }
        }
    }

    public void removeStrike( double strikeToRemove ) {
        for ( Strike strike : getStrikes( ) ) {
            if ( strikeToRemove == strike.getStrike( ) ) {
                getStrikes( ).remove( strike );
            }
        }

        for ( Map.Entry< Integer, Option > entry : optionsMap.entrySet( ) ) {
            Option option = entry.getValue( );
            if ( strikeToRemove == option.getStrike( ) ) {
                optionsMap.remove( option );
            }
        }
    }

    public HashMap< Integer, Option > getOptionsMap() {
        return optionsMap;
    }

    public Option getOption( String name ) {

        double targetStrike = Double.parseDouble( name.substring( 1 ) );

        for ( Strike strike : strikes ) {
            if ( strike.getStrike( ) == targetStrike ) {
                if ( name.toLowerCase( ).contains( "c" ) ) {
                    return strike.getCall( );
                } else {
                    return strike.getPut( );
                }
            }
        }
        return null;
    }

    public double getOpAvg() {

        double sum = 0;

        if ( !opList.isEmpty( ) ) {

            try {

                for ( int i = 0; i < opList.size( ); i++ ) {
                    sum += opList.get( i );
                }

            } catch ( Exception e ) {
                e.printStackTrace( );
            }

            return L.floor( sum / opList.size( ), 100 );
        } else {
            return 0;
        }
    }

    public void setOpAvg( double opAvg ) {
        int size = opList.size( );
        opList.clear( );

        for ( int i = 0; i < size; i++ ) {
            opList.add( opAvg );
        }
    }

    public void checkOptionData() {
        new Thread( () -> {

            while ( !isGotData( ) ) {
                try {

                    // Sleep
                    Thread.sleep( 1000 );
                    boolean bool = true;

                    double increment = client.getStrikeMargin( );

                    // For each strike
                    double strikInMoney = iOptionsCalcs.getStrikeInMoney(  );
                    double startStrike = strikInMoney - increment * 2;
                    double endStrike = strikInMoney + increment * 2;

                    for ( double strikePrice = startStrike; strikePrice < endStrike; strikePrice += client.getStrikeMargin( ) ) {

                        Strike strike = getStrike( strikePrice );

                        Option call = strike.getCall( );
                        Option put = strike.getPut( );

                        if ( call.getBid( ) == 0 || call.getAsk( ) == 0 || put.getBid( ) == 0 || put.getAsk( ) == 0 ) {
                            bool = false;
                            break;
                        }
                    }

                    // Exit the function
                    if ( bool ) {
                        setGotData( bool );
                        Thread.currentThread( ).interrupt( );
                    }

                } catch ( InterruptedException e ) {
                    break;
                } catch ( Exception e ) {

                }
            }
        } ).start( );
    }

    // Claculate the index from options
    public double getContract() {

        if ( contract != 0 ) {
            return contract;
        }

        try {
            ArrayList< Double > buys = new ArrayList<>( );
            ArrayList< Double > sells = new ArrayList<>( );

            double callAsk = 0;
            double callBid = 0;
            double putAsk = 0;
            double putBid = 0;

            for ( Strike strike : getStrikes( ) ) {
                try {
                    callAsk = strike.getCall( ).getAsk( );
                    callBid = strike.getCall( ).getBid( );
                    putAsk = strike.getPut( ).getAsk( );
                    putBid = strike.getPut( ).getBid( );

                    if ( callAsk <= 0 ) {
                        callAsk = 99999999;
                    }
                    if ( putAsk <= 0 ) {
                        putAsk = 99999999;
                    }

                    final double v = strike.getStrike( ) * ( Math.exp( ( -props.getInterestZero( ) - 0.002 + iOptionsCalcs.getCalcDevidend(  ) ) * ( getProps( ).getDays( ) / 360.0 ) ) );
                    double buy = callAsk - putBid + v;
                    double sell = callBid - putAsk + v;
                    buys.add( buy );
                    sells.add( sell );

                } catch ( Exception e ) {
                    System.out.println( client.getName( ) + " getContract() Exception " );
                }
            }

            double currentBidMin = Collections.min( buys );
            double currentAskMax = Collections.max( sells );

            // Update contract bid, ask
            setContractBid( floor( currentBidMin, 100 ) );
            setContractAsk( floor( currentAskMax, 100 ) );

            bidMin = currentBidMin;
            askMax = currentAskMax;

            return floor( ( ( bidMin + askMax ) / 2 ), 100 );
        } catch ( Exception e ) {
            return 0;
        }
    }

    // Claculate the index from options
    public double calcContractAbsolute() {

        try {
            ArrayList< Double > buys = new ArrayList<>( );
            ArrayList< Double > sells = new ArrayList<>( );

            double strikeInMoney = iOptionsCalcs.getStrikeInMoney( );
            double startStrike = strikeInMoney - ( client.getStrikeMargin( ) * 5 );
            double endStrike = strikeInMoney + ( client.getStrikeMargin( ) * 5 );

            for ( double strikePrice = startStrike; strikePrice <= endStrike; strikePrice += client.getStrikeMargin( ) ) {

                Strike strike;
                double call_ask = 0;
                double call_bid = 0;
                double put_ask = 0;
                double put_bid = 0;

                try {
                    strike = getStrike( strikePrice );

                    call_ask = strike.getCall( ).getAsk( );
                    call_bid = strike.getCall( ).getBid( );
                    put_ask = strike.getPut( ).getAsk( );
                    put_bid = strike.getPut( ).getBid( );

                    if ( call_ask <= 0 ) {
                        call_ask = 99999999;
                    }
                    if ( put_ask <= 0 ) {
                        put_ask = 99999999;
                    }

                    double v = ( strikePrice / ( Math.pow( props.getInterest( ), ( getAbsolutDays( ) / 360.0 ) ) ) );

                    double buy = ( call_ask - put_bid ) + v;
                    double sell = ( call_bid - put_ask ) + v;

                    buys.add( buy );
                    sells.add( sell );

                } catch ( Exception e ) {
                    // TODO: handle exception
                }
            }

            double currentBidMin = Collections.min( buys );
            double currentAskMax = Collections.max( sells );

            double future = floor( ( currentBidMin + currentAskMax ) / 2, 100 );

            return future;
        } catch ( Exception e ) {
            e.printStackTrace( );
            return 0;
        }
    }

    public double getAbsolutDays() {
        double d = ( int ) ChronoUnit.DAYS.between( LocalDate.now( ), getExpDate( ) );
        return d + 1;
    }


    public double getOp() {
        return L.floor( getContract( ) - client.getIndex( ), 100 );
    }

    public Option getOption( String side, double targetStrike ) {
        for ( Strike strike : strikes ) {
            if ( strike.getStrike( ) == targetStrike ) {
                if ( side.toLowerCase( ).contains( "c" ) ) {
                    return strike.getCall( );
                } else {
                    return strike.getPut( );
                }
            }
        }
        return null;
    }

    public Option getOption( Class c, double targetStrike ) {
        for ( Strike strike : strikes ) {
            if ( strike.getStrike( ) == targetStrike ) {
                if ( c == Call.class ) {
                    return strike.getCall( );
                } else {
                    return strike.getPut( );
                }
            }
        }
        return null;
    }

    // Return single strike by strike price (double)
    public Strike getStrike( double strikePrice ) {
        for ( Strike strike : strikes ) {
            if ( strikePrice == strike.getStrike( ) ) {
                return strike;
            }
        }
        return null;
    }

    // Return list of strikes prices
    public ArrayList< Double > getStrikePricesList() {
        ArrayList< Double > list = new ArrayList<>( );
        strikes.forEach( strike -> list.add( strike.getStrike( ) ) );
        return list;
    }

    // Remove strike from strikes arr by strike class
    public void removeStrike( Strike strike ) {
        strikes.remove( strike );
    }

    // Add strike to strikes arr
    public void addStrike( Strike strike ) {

        boolean contains = getStrikePricesList( ).contains( strike.getStrike( ) );

        // Not inside
        if ( !contains ) {
            strikes.add( strike );
        }
    }

    public Option getOptionById( int id ) {
        return optionsMap.get( id );
    }

    // Set option in strikes arr
    public void setOption( Option option ) {

        // Set min || max ID
        setMinId( option.getId( ) );
        setMaxId( option.getId( ) );

        // HashMap
        optionsMap.put( option.getId( ), option );

        // Strikes list
        boolean callPut = option instanceof Call;

        Strike strike = getStrike( option.getStrike( ) );

        if ( strike != null ) {

            if ( callPut ) {
                if ( strike.getCall( ) == null ) {
                    strike.setCall( ( Call ) option );
                }
            } else {
                if ( strike.getPut( ) == null ) {
                    strike.setPut( ( Put ) option );
                }
            }
        } else {

            // Create new if doesn't exist
            strike = new Strike( );
            strike.setStrike( option.getStrike( ) );

            if ( callPut ) {
                strike.setCall( ( Call ) option );
            } else {
                strike.setPut( ( Put ) option );
            }

            // Add strike
            addStrike( strike );
        }
    }

    public List< Strike > getStrikes() {
        return strikes;
    }

    public void setStrikes( List< Strike > strikes ) {
        this.strikes = strikes;
    }

    public String toStringVertical() {
        String string = "";

        for ( Strike strike : strikes ) {
            string += strike.toString( ) + "\n\n";
        }
        return string;
    }

    private JSONObject getDataAsJson() {

        JSONObject mainJson = new JSONObject( );

        JSONObject optionsData = new JSONObject( );

        JSONObject callJson;
        JSONObject putJson;
        JSONObject strikeJson;

        for ( Strike strike : strikes ) {

            callJson = new JSONObject( );
            putJson = new JSONObject( );
            strikeJson = new JSONObject( );

            Call call = strike.getCall( );
            callJson.put( JsonStrings.bid.toString( ), call.getBid( ) );
            callJson.put( JsonStrings.ask.toString( ), call.getAsk( ) );
            callJson.put( JsonStrings.optBidAskCounter.toString( ), call.getBidAskCounter( ) );

            Put put = strike.getPut( );
            putJson.put( JsonStrings.bid.toString( ), put.getBid( ) );
            putJson.put( JsonStrings.ask.toString( ), put.getAsk( ) );
            putJson.put( JsonStrings.optBidAskCounter.toString( ), put.getBidAskCounter( ) );

            strikeJson.put( JsonStrings.call.toString( ), callJson );
            strikeJson.put( JsonStrings.put.toString( ), putJson );

            optionsData.put( str( strike.getStrike( ) ), strikeJson );
        }

        mainJson.put( JsonStrings.contract.toString( ), getContract( ) );
        mainJson.put( JsonStrings.opAvg.toString( ), L.floor( getOpAvg( ), 100 ) );
        mainJson.put( JsonStrings.data.toString( ), optionsData );
        mainJson.put( JsonStrings.conBidAskCounter.toString( ), getConBidAskCounter( ) );

        return mainJson;
    }

    public JSONObject getResetDataAsJson() {
        JSONObject mainJson = new JSONObject( );

        JSONObject optionsData = new JSONObject( );

        JSONObject callJson;
        JSONObject putJson;
        JSONObject strikeJson;

        for ( Strike strike : strikes ) {

            callJson = new JSONObject( );
            putJson = new JSONObject( );
            strikeJson = new JSONObject( );

            callJson.put( JsonStrings.bid.toString( ), 0 );
            callJson.put( JsonStrings.ask.toString( ), 0 );
            callJson.put( JsonStrings.optBidAskCounter.toString( ), 0 );

            putJson.put( JsonStrings.bid.toString( ), 0 );
            putJson.put( JsonStrings.ask.toString( ), 0 );
            putJson.put( JsonStrings.optBidAskCounter.toString( ), 0 );

            strikeJson.put( JsonStrings.call.toString( ), callJson );
            strikeJson.put( JsonStrings.put.toString( ), putJson );

            optionsData.put( str( strike.getStrike( ) ), strikeJson );
        }

        mainJson.put( JsonStrings.contract.toString( ), 0 );
        mainJson.put( JsonStrings.opAvg.toString( ), 0 );
        mainJson.put( JsonStrings.data.toString( ), optionsData );
        mainJson.put( JsonStrings.futureBidAskCounter.toString( ), 0 );

        return mainJson;
    }

    public void setOptionsData( JSONObject json ) {
        for ( Strike strike : getStrikes( ) ) {
            try {
                double strikePrice = strike.getStrike( );

                // Get data from json
                JSONObject callJson = json.getJSONObject( str( strikePrice ) ).getJSONObject( "call" );
                JSONObject putJson = json.getJSONObject( str( strikePrice ) ).getJSONObject( "put" );

                // Set data to options
                strike.getCall( ).setBidAskCounter( callJson.getInt( "bid_ask_counter" ) );
                strike.getPut( ).setBidAskCounter( putJson.getInt( "bid_ask_counter" ) );

            } catch ( Exception e ) {
            }
        }
    }

    public void resetOptionsBidAskCounter() {
        try {
            for ( Strike strike : getStrikes( ) ) {

                Call call = strike.getCall( );
                Put put = strike.getPut( );

                call.setBidAskCounter( 0 );
                call.getBidAskCounterList( ).clear( );

                put.setBidAskCounter( 0 );
                put.getBidAskCounterList( ).clear( );
            }
        } catch ( Exception e ) {
            e.printStackTrace( );
        }
    }

    public double floor( double d, int zeros ) {
        return Math.floor( d * zeros ) / zeros;
    }

    public String str( Object o ) {
        return String.valueOf( o );
    }

    public LocalDate getExpDate() {
        return expDate;
    }

    public List<Double> getOpList() {
        return opList;
    }

    public void setExpDate(LocalDate expDate ) {
        this.expDate = expDate;
    }

    public int getConBidAskCounter() {
        return contractBidAskCounter;
    }

    public void setContractBidAskCounter( int contractBidAskCounter ) {
        this.contractBidAskCounter = contractBidAskCounter;
    }

    public LocalDate getToDay() {
        return LocalDate.now();
    }

    public int getMinId() {
        return minId;
    }

    public void setMinId( int minId ) {

        if ( this.minId != 0 ) {

            if ( minId < this.minId ) {
                this.minId = minId;
            }

        } else {
            this.minId = minId;
        }
    }

    public int getMaxId() {
        return maxId;
    }

    public void setMaxId( int maxId ) {

        if ( this.maxId != 0 ) {

            if ( maxId > this.maxId ) {
                this.maxId = maxId;
            }

        } else {
            this.maxId = maxId;
        }

    }

    public int getBaseID() {
        return baseID;
    }

    public void setBaseID( int baseID ) {
        this.baseID = baseID;
    }

    // ---------- Basic Functions ---------- //
    private double dbl( String s ) {
        return Double.parseDouble( s );
    }

    private int INT( String s ) {
        return Integer.parseInt( s );
    }

    public boolean isRequested() {
        return requested;
    }

    public void setRequested( boolean requested ) {
        this.requested = requested;
    }

    public MyContract getTwsContract() {
        return twsContract;
    }

    public boolean isGotData() {
        return gotData;
    }

    public void setGotData( boolean gotData ) {
        this.gotData = gotData;
    }

    public double getContractBid() {
        return contractBid;
    }

    private double conAskForCheck = 0;

    public List< Double > getConAskList() {
        return conAskList;
    }

    public MyChartList getConBidAskCounterList() {
        return conBidAskCounterList;
    }

    public List< Double > getConBidList() {
        return conBidList;
    }

    public List< Double > getOpAvgList() {
        return opAvgList;
    }

    public List< Double > getConList() {
        return conList;
    }

    public void setContractBid( double contractBid ) {

        // If increment state
        if ( contractBid > this.contractBid && conAskForCheck == this.contractAsk ) {
            contractBidAskCounter++;
        }
        this.contractBid = contractBid;

        // Ask for bid change state
        conBidForCheck = contractBid;
        conAskForCheck = this.contractAsk;

    }

    public IOptionsCalcs getiOptionsCalcs() {
        return iOptionsCalcs;
    }

    public double getCurrStrike() {
        return currStrike;
    }

    public void setCurrStrike(double currStrike) {
        this.currStrike = currStrike;
    }

    public OptionsProps getProps() {
        return props;
    }

    public double getContractAsk() {
        return contractAsk;
    }

    private double conBidForCheck = 0;

    public OptionsDDeCells getOptionsDDeCells() {
        return optionsDDeCells;
    }

    public void setContractAsk( double contractAsk ) {

        // If increment state
        if ( contractAsk > this.contractAsk && conBidForCheck == this.contractBid ) {
            contractBidAskCounter--;
        }
        this.contractAsk = contractAsk;

        // Ask for bid change state
        conAskForCheck = contractAsk;
        conBidForCheck = this.contractBid;
    }

    @Override
    public MyJson getAsJson() {
        MyJson json = new MyJson();
        json.put( JsonStrings.contract, contract);
        json.put( JsonStrings.conBid, contractBid);
        json.put( JsonStrings.conAsk, contractAsk);
        json.put( JsonStrings.conBidAskCounter, contractBidAskCounter);
        json.put( JsonStrings.opAvg, L.floor( getOpAvg( ), 100) );
        return json;
    }

    @Override
    public void loadFromJson(MyJson json) {
        setContractBidAskCounter(json.getInt( JsonStrings.conBidAskCounter));
    }

    @Override
    public MyJson getResetJson() {
        return null;
    }

}