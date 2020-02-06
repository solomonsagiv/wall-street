package lists;

import locals.MyObjects;
import serverObjects.BASE_CLIENT_OBJECT;

import java.util.ArrayList;
import java.util.List;

public abstract class MyList implements IMyList {

    // Variables
    BASE_CLIENT_OBJECT client;
    List list;
    int optionalMaxSize = 0;
    String name;

    // Constructors
    public MyList( BASE_CLIENT_OBJECT client, String name ) {
        this.client = client;
        this.name = name;
        this.client.getLists( ).add( this );
        initList( );
    }

    public MyList( BASE_CLIENT_OBJECT client, int optionalMaxSize, String name ) {
        this( client, name );
        this.optionalMaxSize = optionalMaxSize;
    }

    // Functions
    public void addVal() {

        // Remove index 0 if size > optionalMaxSize && size > 0
        if ( optionalMaxSize > 0 && list.size( ) > optionalMaxSize ) {
            list.remove( 0 );
        }
        addMyVal( );
    }

    public void clear() {
        list.clear( );
    }

    public int size() {
        return list.size( );
    }

    public void setValues( MyObjects.MyBaseObject object ) {

        int size = list.size( );

        clear( );
        fillList( object, size );
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

    public String getName() {
        return name;
    }
}
