package lists;

import serverObjects.BASE_CLIENT_OBJECT;

import java.util.ArrayList;
import java.util.List;

public class MyList {

    // Variables
    BASE_CLIENT_OBJECT client;
    List list;
    int targetObject;
    int optionalMaxSize = 0;

    public static final int  INDEX = 0;
    public static final int  CONTRACT = 1;
    public static final int  INDEX_BID = 2;
    public static final int  INDEX_ASK = 3;
    public static final int  CONTRACT_BID = 4;
    public static final int  CONTRACT_ASK = 5;
    public static final int  INDEX_RACES = 6;
    public static final int  OP = 7;
    public static final int  OP_QUARTER = 8;

    // Constructors
    public MyList( BASE_CLIENT_OBJECT client, int targetObject ) {

        list = new ArrayList< Double >( );
        this.client = client;
        this.targetObject = targetObject;

    }

    public MyList( BASE_CLIENT_OBJECT client, int targetObject, int optionalMaxSize ) {

        list = new ArrayList< Double >( );
        this.client = client;
        this.optionalMaxSize = optionalMaxSize;
        this.targetObject = targetObject;

    }

    // Functions
    public void addVal() {

        // Remove index 0 if size > optionalMaxSize && size > 0
        if ( optionalMaxSize > 0 && list.size( ) > optionalMaxSize ) {
            list.remove( 0 );
        }
        list.add( getTargeObject( ) );
    }

    public void clear() {
        list.clear( );
    }

    public void setValues( double value ) {
        int size = getList( ).size( );

        clear( );

        for ( int i = 0; i < size; i++ ) {

            getList( ).add( value );

        }

    }

    public Object getTargeObject() {

        switch ( targetObject ) {
            case INDEX:
                return client.getIndex( );
            case CONTRACT:
                return client.getOptionsHandler( ).getMainOptions( ).getContract( );
            case OP:
                return client.getOptionsHandler( ).getMainOptions( ).getOp( );
            case INDEX_BID:
                return client.getIndexBid( );
            case INDEX_ASK:
                return client.getIndexAsk( );
            case CONTRACT_BID:
                return client.getOptionsHandler( ).getMainOptions( ).getContractBid( );
            case CONTRACT_ASK:
                return client.getOptionsHandler( ).getMainOptions( ).getContractAsk( );
            case OP_QUARTER:
                return client.getOptionsHandler( ).getOptionsQuarter( ).getContract( ) - client.getIndex( );
            case INDEX_RACES:
                return (double) client.getIndexSum();
            default:
                return null;
        }
    }

    public Object getLastItem() {
        return getList( ).get( getList( ).size( ) - 1 );
    }

    // Getters and Setters
    public List getList() {
        return list;
    }

    public void setList( List list ) {
        this.list = list;
    }

    public ArrayList< Double > getAsDoubleList() {
        return ( ArrayList< Double > ) list;
    }

    public BASE_CLIENT_OBJECT getClient() {
        return client;
    }

    public void setClient( BASE_CLIENT_OBJECT client ) {
        this.client = client;
    }

}
