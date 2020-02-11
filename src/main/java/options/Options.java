package options;

import OPs.EqualMoveCalculator;
import OPs.EqualMoveService;
import OPs.OpAvgEqualMoveCalculator;
import OPs.OpAvgMoveService;
import com.ib.client.Contract;
import gui.WallStreetWindow;
import lists.MyDoubleList;
import lists.MyList;
import locals.L;
import locals.MyObjects;
import logic.LogicService;
import options.fullOptions.PositionCalculator;
import org.json.JSONObject;
import serverObjects.BASE_CLIENT_OBJECT;
import service.MyBaseService;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Options {

    public static final int DAY = 1;
    public static final int MONTH = 2;
    public static final int QUARTER = 3;
    public static final int QUARTER_FAR = 4;
    public static final int FUTURE = 5;
    List< Strike > strikes;
    HashMap< Integer, Option > optionsMap;
    BASE_CLIENT_OBJECT client;
    double bidMin = 0;
    double askMax = 0;
    private boolean requested = false;
    private int type;
    private String name = "";
    private LocalDate toDay = LocalDate.now( );
    private LocalDate expDate;
    private double daysToExp = -1;
    private double strikeMarginForContract = 0;
    private double interest = 1;
    private double interestZero = 0;
    private double devidend = -1;
    private double borrow = 0;
    private int contractBidAskCounter = 0;
    private int baseID = 0;
    private int minId = 0;
    private int maxId = 0;
    private Contract twsContract;
    private boolean gotData = false;

    private MyObjects.MySimpleDouble op;

    MyList opList;
    MyList opAvgList;
    MyList contractList;
    MyList contractBidList;
    MyList contractAskList;

    private EqualMoveCalculator equalMoveCalculator;
    private OpAvgEqualMoveCalculator opAvgEqualMoveCalculator;
    private PositionCalculator positionCalculator;

    private MyObjects.MySimpleDouble contractBid;
    private MyObjects.MySimpleDouble contractAsk;
    private MyObjects.MyDouble contract;
    private MyObjects.MyDouble opAvg;
    private MyObjects.MyJSONObject optionsJson;

    EqualMoveService equalMoveService;
    OpAvgMoveService opAvgMoveService;
    LogicService logicService;

    public Options( BASE_CLIENT_OBJECT client, int type, Contract twsContract ) {
        this.type = type;
        this.twsContract = twsContract;
        this.client = client;

        initMyObjects( );
        initType( );

        strikes = new ArrayList<>( );
        optionsMap = new HashMap<>( );

        opList = new MyDoubleList( client, getOp( ), getName( ) + " Op" );
        opAvgList = new MyDoubleList( client, getOpAvg( ), getName( ) + " OpAvg" );
        contractList = new MyDoubleList( client, getContract( ), getName( ) + " Contract" );
        contractBidList = new MyDoubleList( client, getContractBid( ), getName( ) + " ContractBid" );
        contractAskList = new MyDoubleList( client, getContractAsk( ), getName( ) + " ContractAsk" );

        equalMoveCalculator = new EqualMoveCalculator( client, client.getEqualMovePlag( ), this );
        opAvgEqualMoveCalculator = new OpAvgEqualMoveCalculator( client, client.getEqualMovePlag( ), this );
        positionCalculator = new PositionCalculator( client );

        // Services
        equalMoveService = new EqualMoveService( client, "equalMove", MyBaseService.EQUAL_MOVE, 200, this, client.getEqualMovePlag( ) );
        opAvgMoveService = new OpAvgMoveService( client, "opAvgMove", MyBaseService.OP_AVG_MOVE, 200, client.getEqualMovePlag( ), this );


        if (type == Options.MONTH ) {
            logicService = new LogicService( client, "logicService", MyBaseService.LOGIC, this, client.getPanel( ), 200 );
        }

    }

    public Call getCall( double targetStrike ) {
        for ( Strike strike: strikes) {
            if ( targetStrike == strike.getStrike() ) {
                return strike.getCall();
            }
        }
        return null;
    }

    public Put getPut( double targetStrike ) {
        for ( Strike strike: strikes) {
            if ( targetStrike == strike.getStrike() ) {
                return strike.getPut();
            }
        }
        return null;
    }

    private void initMyObjects() {

        op = new MyObjects.MySimpleDouble( ) {
            @Override
            public double getVal() {
                return floor( calcContract( ) - client.getIndex( ).getVal( ), 100 );
            }
        };

        // Contract
        contract = new MyObjects.MyDouble( client.getMyObjects( ) ) {
            @Override
            public void initMe( int sleepCount ) {
                if ( sleepCount % getSleep( ) == 0 ) {
                    contract.setVal( calcContract( ) );
                }
            }

            @Override
            public int getSleep() {
                return 100;
            }

            @Override
            public void calc() {
                setVal( calcContract( ) );
            }
        };

        contractBid = new MyObjects.MySimpleDouble( );

        contractAsk = new MyObjects.MySimpleDouble( );

        // Op avg
        opAvg = new MyObjects.MyDouble( client.getMyObjects( ) ) {
            @Override
            public void initMe( int sleepCount ) {
                if ( sleepCount % getSleep( ) == 0 ) {
                    opAvg.setVal( calcOpAvg( ) );
                }
            }

            @Override
            public int getSleep() {
                return 3000;
            }

            @Override
            public void calc() {
                setVal( calcOpAvg( ) );
            }
        };

        // Options json as string
        optionsJson = new MyObjects.MyJSONObject( client.getMyObjects( ) ) {
            @Override
            public void calc() {
                setVal( getOptionsAsJson( ) );
            }

            @Override
            public void initMe( int sleepCount ) {
                if ( sleepCount % getSleep( ) == 0 ) {
                    setVal( getOptionsAsJson( ) );
                }
            }

            public JSONObject getValByCurrentCalc() {
                return getOptionsAsJson( );
            }

            @Override
            public int getSleep() {
                return 500;
            }
        };

    }

    public void initOptions() {

        double startStrike = client.getStartStrike( );
        double endStrike = client.getEndStrike( );
        double strikeMrgin = client.getStrikeMargin( );

        int id = getBaseID( );

        for ( double strike = startStrike; strike < endStrike; strike += strikeMrgin ) {

            // Call
            Call call = new Call( strike, id );
            setOption( call );
            id++;

            // Put
            Put put = new Put( strike, id );
            setOption( put );
            id++;

        }

    }

    private void initType() {
        switch ( type ) {
            case DAY:
                setBaseID( client.getBaseId( ) + 2000 );
                setName( "Day" );
                break;
            case MONTH:
                setBaseID( client.getBaseId( ) + 4000 );
                setName( "Month" );
                break;
            case QUARTER:
                setBaseID( client.getBaseId( ) + 6000 );
                setName( "Quarter" );
                break;
            case QUARTER_FAR:
                setBaseID( client.getBaseId( ) + 8000 );
                setName( "Quarter Far" );
                break;
            default:
                break;
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
                    double strikInMoney = getStrikeInMoney( client.getFuture( ).getVal( ), 0 ).getStrike( );
                    double startStrike = strikInMoney - increment * 2;
                    double endStrike = strikInMoney + increment * 2;

                    for ( double strikePrice = startStrike; strikePrice < endStrike; strikePrice += getStrikeMarginForContract( ) ) {

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


    public double getContractByCurrentCalc() {
        return calcContract( );
    }

    // Claculate the index from options
    private double calcContract() {

        try {
            ArrayList< Double > buys = new ArrayList<>( );
            ArrayList< Double > sells = new ArrayList<>( );

            MyObjects.MySimpleDouble mySimpleDouble = client.getFuture();

            double strikeInMoney = getStrikeInMoney( client.getFuture( ).getVal( ), 0 ).getStrike( );
            double startStrike = strikeInMoney - ( getStrikeMarginForContract( ) * 5 );
            double endStrike = strikeInMoney + ( getStrikeMarginForContract( ) * 5 );

            Strike strike;
            double callAsk = 0;
            double callBid = 0;
            double putAsk = 0;
            double putBid = 0;

            for ( double strikePrice = startStrike; strikePrice <= endStrike; strikePrice += getStrikeMarginForContract( ) ) {
                try {
                    strike = getStrike( strikePrice );

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

                    final double v = strikePrice * ( Math.exp( ( -getInterestZero( ) - 0.002 + getCalcDevidend( ) ) * ( getDays( ) / 360.0 ) ) );
                    double buy = callAsk - putBid + v;
                    double sell = callBid - putAsk + v;

                    buys.add( buy );
                    sells.add( sell );

                } catch ( Exception e ) {
                    e.printStackTrace( );
                }

            }

            double currentBidMin = Collections.min( buys );
            double currentAskMax = Collections.max( sells );

            // Update contract bid, ask
            contractBid.setVal( currentBidMin );
            contractAsk.setVal( currentAskMax );

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
            e.printStackTrace( );
            return 0;
        }
    }

    // Claculate the index from options
    public double calcContractAbsolute() {

        try {
            ArrayList< Double > buys = new ArrayList<>( );
            ArrayList< Double > sells = new ArrayList<>( );

            double strikeInMoney = getStrikeInMoney( client.getFuture( ).getVal( ), 0 ).getStrike( );
            double startStrike = strikeInMoney - ( getStrikeMarginForContract( ) * 5 );
            double endStrike = strikeInMoney + ( getStrikeMarginForContract( ) * 5 );

            for ( double strikePrice = startStrike; strikePrice <= endStrike; strikePrice += getStrikeMarginForContract( ) ) {

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

    public Strike getStrikeInMoney( double index, int strikeFarLevel ) {

        double margin = 100000;
        int returnIndex = 0;

        List< Strike > strikes = getStrikes( );

        for ( int i = 0; i < strikes.size( ); i++ ) {

            Strike strike = strikes.get( i );
            double newMargin = absolute( strike.getStrike( ) - index );

            if ( newMargin < margin ) {
                margin = newMargin;
                returnIndex = i;
            } else {
                return strikes.get( returnIndex + strikeFarLevel );
            }
        }
        return null;
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
                sum += ( double ) opList.getList( ).get( i );
            }
            return floor( sum / seconds, 100 );
        } else {
            return calcOpAvg( );
        }

    }

    private double calcOpAvg() {

        double sum = 0;

        if ( !opList.getList( ).isEmpty( ) ) {

            try {

                for ( int i = 0; i < opList.getList( ).size( ); i++ ) {
                    sum += ( double ) opList.getList( ).get( i );
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
        props.put( "days", getDays( ) );
        return props;

    }

    public JSONObject getEmptyProps() {

        JSONObject json = new JSONObject( );
        json.put( "interest", 1 );
        json.put( "borrow", 0 );
        json.put( "devidend", 0 );
        json.put( "days", 0 );

        return json;
    }

    public void setPropsDataFromJson( JSONObject json ) {

        double interest = json.getDouble( "interest" );
        double devidend = json.getDouble( "devidend" );
        double borrow = json.getDouble( "borrow" );
        double days = json.getDouble( "days" );

        setInterestZero( interest - 1 );
        setInterest( interest );
        setDevidend( devidend );
        setBorrow( borrow );
        setDaysToExp( days );

    }

    public List< Strike > getStrikes() {
        return strikes;
    }

    public void setStrikes( List< Strike > strikes ) {
        this.strikes = strikes;
    }

    public String toStringVertical() {
        String string = "";

        string += getName( ) + "\n\n";

        for ( Strike strike : strikes ) {
            string += strike.toString( ) + "\n\n";
        }
        return string;
    }

    private JSONObject getOptionsAsJson() {

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
        mainJson.put( "equalMove", getEqualMoveCalculator( ).getMove( ).getVal( ) );
        mainJson.put( "con", calcContract( ) );
        mainJson.put( "props", getProps( ) );
        mainJson.put( "opAvg", L.floor( calcOpAvg( ), 100 ) );
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


    public void setDataFromJson( JSONObject json ) {

        getEqualMoveCalculator( ).setMoveIndex( json.getDouble( "equalMove" ) );
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
            WallStreetWindow.popup( "Reset option faild", e );
        }
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


    public EqualMoveCalculator getEqualMoveCalculator() {
        return equalMoveCalculator;
    }

    public OpAvgEqualMoveCalculator getOpAvgEqualMoveCalculator() {
        return opAvgEqualMoveCalculator;
    }

    public LocalDate getExpDate() {
        return expDate;
    }

    public void setExpDate( LocalDate expDate ) {
        this.expDate = expDate;
    }

    public double getStrikeMarginForContract() {
        return strikeMarginForContract;
    }

    public void setStrikeMarginForContract( double strikeMarginForContract ) {
        this.strikeMarginForContract = strikeMarginForContract;
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

    public double getCalcDevidend() {

        if ( devidend <= 0 ) {
            return 0;
        }

        double calcDev = getDevidend( ) * 360.0 / getDays( ) / client.getFuture( ).getVal( );

        if ( Double.isInfinite( calcDev ) ) {
            return 0;
        }

        return calcDev;
    }

    private double getBorrow() {
        return borrow;
    }

    public void setBorrow( double borrow ) {
        this.borrow = borrow;
    }

    public double getCalcBorrow() {
        if ( getBorrow( ) != 0 ) {
            return getBorrow( );
        } else {
            return floor( client.getFuture( ).getVal( ) * 0.002 / 360.0 * getDays( ), 10000 );
        }
    }

    public static int getDAY() {
        return DAY;
    }

    public MyObjects.MyDouble getContractBid() {
        return contractBid;
    }

    public MyObjects.MyDouble getContractAsk() {
        return contractAsk;
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

    public void setTwsContract( Contract twsContract ) {
        this.twsContract = twsContract;
    }

    public boolean isGotData() {
        return gotData;
    }

    public void setGotData( boolean gotData ) {
        this.gotData = gotData;
    }

    public int getType() {
        return type;
    }

    public void setType( int type ) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public PositionCalculator getPositionCalculator() {
        return positionCalculator;
    }

    public void setPositionCalculator( PositionCalculator positionCalculator ) {
        this.positionCalculator = positionCalculator;
    }

    public MyObjects.MyDouble getContract() {
        return contract;
    }

    public void setContract( MyObjects.MyDouble contract ) {
        this.contract = contract;
    }

    public MyObjects.MyDouble getOpAvg() {
        return opAvg;
    }

    public void setOpAvg( MyObjects.MyDouble opAvg ) {
        this.opAvg = opAvg;
    }


    public MyObjects.MyJSONObject getOptionsJson() {
        return optionsJson;
    }

    public MyList getOpList() {
        return opList;
    }

    public MyList getOpAvgList() {
        return opAvgList;
    }

    public MyList getContractList() {
        return contractList;
    }

    public MyList getContractBidList() {
        return contractBidList;
    }

    public MyList getContractAskList() {
        return contractAskList;
    }

    public MyObjects.MySimpleDouble getOp() {
        return op;
    }
}
