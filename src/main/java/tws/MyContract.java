package tws;

import charts.myChart.MyProps;
import com.ib.client.Contract;
import locals.IJsonDataBase;
import org.json.JSONObject;

public class MyContract extends Contract implements IJsonDataBase {

    private int myId;
    private boolean requested;
    private TwsContractsEnum type;
    private MyProps props;

    public MyContract( int myId, TwsContractsEnum type ) {
        this.myId = myId;
        this.type = type;
    }

    @Override
    public JSONObject getAsJson() {
        JSONObject object = new JSONObject( );
        object.put( MyContractEnum.ID.toString( ), getMyId() );
        object.put( MyContractEnum.SEC_TYPE.toString( ), secType( ) );
        object.put( MyContractEnum.CURRENCY.toString( ), currency( ) );
        object.put( MyContractEnum.EXCHANGE.toString( ), exchange( ) );
        object.put( MyContractEnum.TRADING_CLASS.toString( ), tradingClass( ) );
        object.put( MyContractEnum.MULTIPLIER.toString( ), multiplier( ) );
        object.put( MyContractEnum.SYMBOL.toString( ), symbol( ) );
        object.put( MyContractEnum.INCLUDE_EXPIRED.toString( ), includeExpired( ) );

        return object;
    }

    @Override
    public void loadFromJson( JSONObject object ) {
        setMyId( object.getInt( MyContractEnum.ID.toString() ) );
        secType( object.getString( MyContractEnum.SEC_TYPE.toString() ) );
        currency( object.getString( MyContractEnum.CURRENCY.toString() ) );
        exchange( object.getString( MyContractEnum.EXCHANGE.toString() ) );
        tradingClass( object.getString( MyContractEnum.TRADING_CLASS.toString() ) );
        multiplier( object.getString( MyContractEnum.MULTIPLIER.toString() ) );
        symbol( object.getString( MyContractEnum.SYMBOL.toString() ) );
        includeExpired( object.getBoolean( MyContractEnum.INCLUDE_EXPIRED.toString() ) );
    }

    @Override
    public JSONObject getResetObject() {
        return getAsJson();
    }

    public MyContract() {
    }

    public MyContract( TwsContractsEnum type ) {
        this.type = type;
    }

    public int getMyId() {
        return myId;
    }

    public void setMyId( int myId ) {
        this.myId = myId;
    }

    public boolean isRequested() {
        return requested;
    }

    public void setRequested( boolean requested ) {
        this.requested = requested;
    }

    public TwsContractsEnum getType() {
        return type;
    }
}

enum MyContractEnum {
    ID,
    SEC_TYPE,
    CURRENCY,
    EXCHANGE,
    TRADING_CLASS,
    MULTIPLIER,
    SYMBOL,
    INCLUDE_EXPIRED
}