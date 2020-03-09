package options;

import OPs.EqualMoveService;
import OPs.OpAvgMoveService;
import com.ib.client.Contract;
import com.ib.client.Types;
import gui.WallStreetWindow;
import locals.L;
import logic.LogicService;
import options.fullOptions.PositionCalculator;
import org.json.JSONObject;
import serverObjects.ApiEnum;
import serverObjects.BASE_CLIENT_OBJECT;
import tws.MyContract;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public abstract class Options {

    List< Strike > strikes;
    HashMap< Integer, Option > optionsMap;
    BASE_CLIENT_OBJECT client;
    double bidMin = 0;
    double askMax = 0;
    private boolean requested = false;
    protected OptionsEnum type;
    protected LocalDate toDay = LocalDate.now( );
    protected LocalDate expDate;
    protected double daysToExp = -1;
    protected double interest = 1;
    protected double interestZero = 0;
    protected double devidend = -1;
    protected double borrow = 0;
    protected int contractBidAskCounter = 0;
    protected int baseID = 0;
    protected int minId = 0;
    protected int maxId = 0;
    protected Contract twsContract;
    protected boolean gotData = false;

    protected double contractBid = 0;
    protected double contractAsk = 0;

    protected double currStrike = 0;

    Set<Integer> dates = new HashSet<>();

    protected PositionCalculator positionCalculator;

    EqualMoveService equalMoveService;
    OpAvgMoveService opAvgMoveService;
    LogicService logicService;

    List< Double > opList = new ArrayList<>( );
    List< Double > opAvgList = new ArrayList<>( );
    List< Double > conList = new ArrayList<>( );
    List< Double > conBidList = new ArrayList<>( );
    List< Double > conAskList = new ArrayList<>( );

    public Options( int baseID, BASE_CLIENT_OBJECT client, OptionsEnum type, Contract twsContract ) {
        this.baseID = baseID;
        this.type = type;
        this.twsContract = twsContract;
        this.client = client;

        strikes = new ArrayList<>();
        optionsMap = new HashMap<>();

        positionCalculator = new PositionCalculator( client );

        // Services
        equalMoveService = new EqualMoveService( client, this, client.getEqualMovePlag( ) );
        opAvgMoveService = new OpAvgMoveService( client, this, client.getEqualMovePlag( ) );

        if ( type == OptionsEnum.MONTH ) {
//            logicService = new LogicService( client, this, client.getPanel( ) );
        }

    }

    // Abstracts functions
    public abstract double getStrikeInMoney();

    public abstract Strike getStrikeInMoneyIfZero();

    public abstract double getCalcDevidend();

    public abstract double getCalcBorrow();

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

        int id = getBaseID();

        for ( double strike = startStrike; strike < endStrike; strike += client.getStrikeMargin( ) ) {

            // ----- Call ------ //
            Call call = new Call( strike, id );

            MyContract contractCall = getCopyTwsContract();

            // MyTwsContract
            contractCall.setMyId( id );
            contractCall.strike( strike );
            contractCall.right( Types.Right.Call );
            client.getTwsHandler().addContract( contractCall );

            call.setMyContract( contractCall );

            setOption( call );
            id++;

            // ----- Put ------ //
            Put put = new Put( strike, id );

            MyContract contractPut = getCopyTwsContract();

            // MyTwsContract
            contractPut.setMyId( id );
            contractPut.strike( strike );
            contractPut.right( Types.Right.Put );
            client.getTwsHandler().addContract( contractPut );

            put.setMyContract( contractPut );

            setOption( put );
            id++;

        }
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

    public void checkOptionData() {
        new Thread( () -> {

            while ( !isGotData( ) ) {
                try {

                    // Sleep
                    Thread.sleep( 1000 );
                    boolean bool = true;

                    double increment = client.getStrikeMargin( );

                    // For each strike
                    double strikInMoney = getStrikeInMoney( );
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

                    final double v = strike.getStrike( ) * ( Math.exp( ( -getInterestZero( ) - 0.002 + getCalcDevidend( ) ) * ( getDays( ) / 360.0 ) ) );
                    double buy = callAsk - putBid + v;
                    double sell = callBid - putAsk + v;

                    buys.add( buy );
                    sells.add( sell );

                } catch ( Exception e ) {
                    System.out.println( "Exeption" );
                }
            }

            double currentBidMin = Collections.min( buys );
            double currentAskMax = Collections.max( sells );

            // Update contract bid, ask
            setContractBid( currentBidMin );
            setContractAsk( currentAskMax );

            if ( currentBidMin > bidMin && bidMin != 0 ) {
                setContractBidAskCounter( getContractBidAskCounter( ) + 1 );
            }

            if ( currentAskMax < askMax && askMax != 0 ) {
                setContractBidAskCounter( getContractBidAskCounter( ) - 1 );
            }

            bidMin = currentBidMin;
            askMax = currentAskMax;

            double future = floor( ( ( bidMin + askMax ) / 2 ), 100 );

            return future;
        } catch ( Exception e ) {
            return 0;
        }
    }

    // Claculate the index from options
    public double calcContractAbsolute() {

        try {
            ArrayList< Double > buys = new ArrayList<>( );
            ArrayList< Double > sells = new ArrayList<>( );

            double strikeInMoney = getStrikeInMoney( );
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

                    double v = ( strikePrice / ( Math.pow( getInterest( ), ( getAbsolutDays( ) / 360.0 ) ) ) );

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

    // Remove strike from strikes arr by strike price (double)
    public void removeStrike( double strike ) {
        int indexToRemove = 0;

        for ( int i = 0; i < strikes.size( ); i++ ) {
            if ( strikes.get( i ).getStrike( ) == strike ) {
                indexToRemove = i;
            }
        }
        strikes.remove( indexToRemove );
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

    // Op avg
    public double getOpAvg15() {

        double sum = 0;
        int seconds = 900;
        if ( opList.size( ) > seconds ) {
            for ( int i = opList.size( ) - seconds; i < opList.size( ); i++ ) {
                sum += ( double ) opList.get( i );
            }
            return floor( sum / seconds, 100 );
        } else {
            return getOpAvg( );
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

    public JSONObject getProps() {

        JSONObject props = new JSONObject( );
        props.put( "interest", getInterest( ) );
        props.put( "borrow", getCalcBorrow( ) );
        props.put( "devidend", getDevidend( ) );
        props.put( "date", getExpDate() );
        props.put( "days", getDays( ) );
        System.out.println(props );
        return props;

    }

    public JSONObject getEmptyProps() {

        JSONObject json = new JSONObject( );
        json.put( "interest", 1 );
        json.put( "borrow", 0 );
        json.put( "devidend", 0 );
        json.put( "date", getExpDate() );
        json.put( "days", 0 );

        return json;
    }

    public void setPropsDataFromJson( JSONObject json ) throws Exception {

        double interest = json.getDouble( "interest" );
        double devidend = json.getDouble( "devidend" );
        double borrow = json.getDouble( "borrow" );
        LocalDate date =  LocalDate.parse( json.getString( "date" ) );
        double days = json.getDouble( "days" );

        setInterestZero( interest - 1 );
        setInterest( interest );
        setDevidend( devidend );
        setExpDate( date );
        setBorrow( borrow );
        setDaysToExp( days );

        getTwsContract().lastTradeDateOrContractMonth( date.toString().replace( "-", "" ) );

    }

    public List< Strike > getStrikes() {
        return strikes;
    }

    public void setStrikes( List< Strike > strikes ) {
        this.strikes = strikes;
    }

    public String toStringVertical() {
        String string = "";

        string += getType().toString() + "\n\n";

        for ( Strike strike : strikes ) {
            string += strike.toString( ) + "\n\n";
        }
        return string;
    }

    public JSONObject getOptionsAsJson() {

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
            callJson.put( "bid", call.getBid( ) );
            callJson.put( "ask", call.getAsk( ) );
            callJson.put( "bid_ask_counter", call.getBidAskCounter( ) );

            Put put = strike.getPut( );
            putJson.put( "bid", put.getBid( ) );
            putJson.put( "ask", put.getAsk( ) );
            putJson.put( "bid_ask_counter", put.getBidAskCounter( ) );

            strikeJson.put( "call", callJson );
            strikeJson.put( "put", putJson );

            optionsData.put( str( strike.getStrike( ) ), strikeJson );
        }

        mainJson.put( "contractBidAskCounter", getContractBidAskCounter( ) );
        mainJson.put( "equalMove", getEqualMoveService( ).getMove( ) );
        mainJson.put( "con", getContract( ) );
        mainJson.put( "props", getProps( ) );
        mainJson.put( "opAvg", L.floor( getOpAvg( ), 100 ) );
        mainJson.put( "opAvg15", L.floor( getOpAvg15( ), 100 ) );

        mainJson.put( "data", optionsData );

        return mainJson;
    }


    public JSONObject getEmptyOptionsAsJson() {

        JSONObject mainJson = new JSONObject( );

        JSONObject optionsData = new JSONObject( );

        JSONObject callJson;
        JSONObject putJson;
        JSONObject strikeJson;

        for ( Strike strike : strikes ) {

            callJson = new JSONObject( );
            putJson = new JSONObject( );
            strikeJson = new JSONObject( );

            callJson.put( "bid", 0 );
            callJson.put( "ask", 0 );
            callJson.put( "bid_ask_counter", 0 );

            putJson.put( "bid", 0 );
            putJson.put( "ask", 0 );
            putJson.put( "bid_ask_counter", 0 );

            strikeJson.put( "call", callJson );
            strikeJson.put( "put", putJson );

            optionsData.put( str( strike.getStrike( ) ), strikeJson );
        }

        mainJson.put( "contractBidAskCounter", 0 );
        mainJson.put( "equalMove", 0 );
        mainJson.put( "con", 0 );
        mainJson.put( "props", getEmptyProps( ) );
        mainJson.put( "opAvg", 0 );
        mainJson.put( "opAvg15", 0 );

        mainJson.put( "data", optionsData );


        return mainJson;
    }


    public void setDataFromJson( JSONObject json ) throws Exception {

        getEqualMoveService( ).setMove( json.getDouble( "equalMove" ) );
        setContractBidAskCounter( json.getInt( "contractBidAskCounter" ) );
        setPropsDataFromJson( json.getJSONObject( "props" ) );
        setOptionsData( json.getJSONObject( "data" ) );

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
//				e.printStackTrace ( );
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
            WallStreetWindow.popup( "Reset option failed", e );
        }
    }

    public OptionsEnum getType() {
        return type;
    }

    public void setType( OptionsEnum type ) {
        this.type = type;
    }

    public double floor( double d, int zeros ) {
        return Math.floor( d * zeros ) / zeros;
    }

    public String str( Object o ) {
        return String.valueOf( o );
    }

    public double absolute( double d ) {
        return Math.abs( d );
    }

    public EqualMoveService getEqualMoveService() {
        return equalMoveService;
    }

    public OpAvgMoveService getOpAvgMoveService() {
        return opAvgMoveService;
    }

    public LocalDate getExpDate() {
        return expDate;
    }

    public void setExpDate( LocalDate expDate ) {
        this.expDate = expDate;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest( double interest ) {
        this.interest = interest;
    }

    public void setInterestWithCalc( double interest ) {
        this.interestZero = interest * 0.01;
        this.interest = 1 + ( interest * 0.01 );
    }

    public double getInterestZero() {
        return interestZero;
    }

    public void setInterestZero( double interestZero ) {
        this.interestZero = interestZero;
    }

    public double getDevidend() {
        return devidend;
    }

    public void setDevidend( double devidend ) {
        this.devidend = devidend;
    }

    protected double getBorrow() {
        return borrow;
    }

    public void setBorrow( double borrow ) {
        this.borrow = borrow;
    }

    public int getContractBidAskCounter() {
        return contractBidAskCounter;
    }

    public void setContractBidAskCounter( int contractBidAskCounter ) {
        this.contractBidAskCounter = contractBidAskCounter;
    }

    public LocalDate getToDay() {
        return toDay;
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
    public double getDays() {

        if ( daysToExp == -1 ) {

            if ( expDate != null ) {

                double d = ( int ) ChronoUnit.DAYS.between( getToDay( ), expDate );
                daysToExp = d + 2;

            }
        }
        return daysToExp;
    }

    public void setDaysToExp( double daysToExp ) {
        this.daysToExp = daysToExp;
    }

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

    public Contract getTwsContract() {
        return twsContract;
    }

    public MyContract getCopyTwsContract() {

        // Base contract
        MyContract contract = ( MyContract ) getTwsContract();

        // Copy
        MyContract copy = new MyContract();
        copy.setMyId( contract.getMyId() );
        copy.symbol( contract.symbol() );
        copy.secType( contract.secType() );
        copy.primaryExch( contract.primaryExch() );
        copy.currency(contract.currency());
        copy.tradingClass( contract.tradingClass() );
        copy.multiplier(contract.multiplier());
        copy.includeExpired( contract.includeExpired() );
        copy.exchange( contract.exchange() );
        copy.localSymbol( contract.localSymbol() );
        copy.lastTradeDateOrContractMonth( contract.lastTradeDateOrContractMonth() );

        return copy;
    }

    public void setTwsContract( Contract twsContract ) {
        this.twsContract = twsContract;
    }

    public boolean isGotData() {
        return gotData;
    }

    public void setGotData( boolean gotData ) {
        this.gotData = gotData;
    }

    public PositionCalculator getPositionCalculator() {
        return positionCalculator;
    }

    public void setPositionCalculator( PositionCalculator positionCalculator ) {
        this.positionCalculator = positionCalculator;
    }

    public double getContractBid() {
        return contractBid;
    }

    public void setContractBid( double contractBid ) {
        this.contractBid = contractBid;
    }

    public double getContractAsk() {
        return contractAsk;
    }

    public void setContractAsk( double contractAsk ) {
        this.contractAsk = contractAsk;
    }

    public List< Double > getOpList() {
        return opList;
    }

    public List< Double > getOpAvgList() {
        return opAvgList;
    }

    public List< Double > getConList() {
        return conList;
    }

    public List< Double > getConBidList() {
        return conBidList;
    }

    public List< Double > getConAskList() {
        return conAskList;
    }

    public LogicService getLogicService() {
        return logicService;
    }

    public Set<Integer> getDates() {
        return dates;
    }

    public void setDates(Set<Integer> dates) {
        this.dates = dates;
    }
}
