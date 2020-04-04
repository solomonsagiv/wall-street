package options;

import com.ib.client.Types;
import locals.IJsonDataBase;
import locals.L;
import myJson.MyJson;
import options.fullOptions.PositionCalculator;
import options.optionsCalcs.IOptionsCalcs;
import org.json.JSONObject;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Spx;
import tws.MyContract;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public abstract class Options implements IJsonDataBase {

    public static void main(String[] args) {

        MyJson json = new MyJson("{\"TWS_CONTRACT\":{\"ID\":0,\"SEC_TYPE\":\"None\",\"INCLUDE_EXPIRED\":true},\"DATA\":{\"DATA\":{},\"OP_AVG\":54,\"CONTRACT\":33,\"BID_ASK_COUNTER\":767676},\"PROPS\":{\"DEVIDEND\":445,\"DAYS\":67,\"INTEREST\":4,\"DATE\":\"2020-09-08\"}}");

        IndexOptions options = new IndexOptions(33, Spx.getInstance(), OptionsEnum.QUARTER);
        options.loadFromJson(json);
        System.out.println(options.getAsJson());
    }

    OptionsDDeCells optionsDDeCells;
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
    protected OptionsProps props;
    protected int contractBidAskCounter = 0;
    protected int baseID = 0;
    protected int minId = 0;
    protected int maxId = 0;
    protected MyContract twsContract;
    protected boolean gotData = false;
    private double contract = 0;
    protected double contractBid = 0;
    protected double contractAsk = 0;
    public double currStrike = 0;
    IOptionsCalcs optionsCalcs;

    Set<Integer> dates = new HashSet<>();

    protected PositionCalculator positionCalculator;

    List< Double > opList = new ArrayList<>( );
    List< Double > opAvgList = new ArrayList<>( );
    List< Double > conList = new ArrayList<>( );
    List< Double > conBidList = new ArrayList<>( );
    List< Double > conAskList = new ArrayList<>( );

    public Options( int baseID, BASE_CLIENT_OBJECT client, OptionsEnum type ) {
        this.baseID = baseID;
        this.type = type;
        this.client = client;

        strikes = new ArrayList<>();
        optionsMap = new HashMap<>();
        twsContract = new MyContract();
        positionCalculator = new PositionCalculator( client );
        props = new OptionsProps();
    }

    public Options ( int baseID, BASE_CLIENT_OBJECT client, OptionsEnum type, OptionsDDeCells dDeCells ) {
        this( baseID,  client,  type );
        this.optionsDDeCells = dDeCells;
    }

    // Inherith functions
    public double getStrikeInMoney() {
        return optionsCalcs.getStrikeInMoney();
    }

    public Strike getStrikeInMoneyIfZero() {
        return optionsCalcs.getStrikeInMoneyIfZero();
    }

    public double getCalcDevidend() {
        return optionsCalcs.getCalcDevidend();
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

    public void removeStrike( double strikeToRemove ) {

        for ( Strike strike: getStrikes()) {
            if ( strikeToRemove == strike.getStrike() ) {
                getStrikes().remove( strike );
            }
        }

        for ( Map.Entry< Integer, Option > entry : optionsMap.entrySet()) {
            Option option = entry.getValue();
            if ( strikeToRemove == option.getStrike() ) {
                optionsMap.remove( option );
            }
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

                    final double v = strike.getStrike( ) * ( Math.exp( ( -props.getInterestZero() - 0.002 + getCalcDevidend( ) ) * ( getDays( ) / 360.0 ) ) );
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

                    double v = ( strikePrice / ( Math.pow( props.getInterest(), ( getAbsolutDays( ) / 360.0 ) ) ) );

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

    public void setOpAvg( double opAvg ) {
        int size = opList.size();

        opList.clear();

        for ( int i = 0; i < size; i++ ) {
            opList.add( opAvg );
        }
    }

    @Override
    public MyJson getAsJson() {
        MyJson object = new MyJson( );
        object.put( JsonEnum.PROPS.toString(), getProps().getAsJson() );
        object.put( JsonEnum.TWS_CONTRACT.toString(), getTwsContract( ).getAsJson() );
        object.put( JsonEnum.DATA.toString(), getDataAsJson());
        return object;
    }

    @Override
    public MyJson getResetObject() {
        MyJson object = new MyJson( );
        object.put( JsonEnum.DATA.toString(), getResetDataAsJson());
        object.put( JsonEnum.TWS_CONTRACT.toString(), getTwsContract( ).getAsJson() );
        return object;
    }

    @Override
    public void loadFromJson( MyJson object ) {
        getProps().loadFromJson( object.getMyJson(JsonEnum.PROPS.toString()) );
        getTwsContract().loadFromJson( object.getMyJson( JsonEnum.TWS_CONTRACT.toString() ) );
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

    public JSONObject getDataAsJson() {

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
            callJson.put( JsonEnum.BID.toString(), call.getBid( ) );
            callJson.put( JsonEnum.ASK.toString(), call.getAsk( ) );
            callJson.put( JsonEnum.BID_ASK_COUNTER.toString(), call.getBidAskCounter( ) );

            Put put = strike.getPut( );
            putJson.put( JsonEnum.BID.toString(), put.getBid( ) );
            putJson.put( JsonEnum.ASK.toString(), put.getAsk( ) );
            putJson.put( JsonEnum.BID_ASK_COUNTER.toString(), put.getBidAskCounter( ) );

            strikeJson.put( JsonEnum.CALL.toString(), callJson );
            strikeJson.put( JsonEnum.PUT.toString(), putJson );

            optionsData.put( str( strike.getStrike( ) ), strikeJson );
        }

        mainJson.put( JsonEnum.BID_ASK_COUNTER.toString(), getContractBidAskCounter( ) );
        mainJson.put( JsonEnum.CONTRACT.toString(), getContract( ) );
        mainJson.put( JsonEnum.OP_AVG.toString(), L.floor( getOpAvg( ), 100 ) );
        mainJson.put( JsonEnum.DATA.toString(), optionsData );

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

            callJson.put( JsonEnum.BID.toString(), 0 );
            callJson.put( JsonEnum.ASK.toString(), 0 );
            callJson.put( JsonEnum.BID_ASK_COUNTER.toString(), 0 );

            putJson.put( JsonEnum.BID.toString(), 0 );
            putJson.put( JsonEnum.ASK.toString(), 0 );
            putJson.put( JsonEnum.BID_ASK_COUNTER.toString(), 0 );

            strikeJson.put( JsonEnum.CALL.toString(), callJson );
            strikeJson.put( JsonEnum.PUT.toString(), putJson );

            optionsData.put( str( strike.getStrike( ) ), strikeJson );
        }

        mainJson.put( JsonEnum.CONTRACT.toString(), 0 );
        mainJson.put( JsonEnum.OP_AVG.toString(), 0 );
        mainJson.put( JsonEnum.DATA.toString(), optionsData );

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

            } catch ( Exception e ) {}
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
            e.printStackTrace();
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

    public LocalDate getExpDate() {
        return expDate;
    }

    public void setExpDate( LocalDate expDate ) {
        this.expDate = expDate;
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

    private MyContract getTwsContract() {
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

    public void setTwsContract( MyContract twsContract ) {
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

    private double conAskForCheck = 0;
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

    public double getContractAsk() {
        return contractAsk;
    }

    private double conBidForCheck = 0;
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

    public Set<Integer> getDates() {
        return dates;
    }

    public void setDates(Set<Integer> dates) {
        this.dates = dates;
    }

    public void setContract( double contract ) {
        this.contract = contract;
    }

    public OptionsDDeCells getOptionsDDeCells() {
        return optionsDDeCells;
    }

    public void setOptionsDDeCells( OptionsDDeCells optionsDDeCells ) {
        this.optionsDDeCells = optionsDDeCells;
    }

    public HashMap< Integer, Option > getOptionsMap() {
        return optionsMap;
    }

    public OptionsProps getProps() {
        return props;
    }

    public void setProps( OptionsProps props ) {
        this.props = props;
    }

    public IOptionsCalcs getOptionsCalcs() {
        if ( optionsCalcs == null ) throw new NullPointerException( client.getName() + " " + getType() + " Options calc not set" );
        return optionsCalcs;
    }

    public void setOptionsCalcs( IOptionsCalcs optionsCalcs ) {
        this.optionsCalcs = optionsCalcs;
    }
}
