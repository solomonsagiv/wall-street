package lists;

import locals.MyObjects;
import serverObjects.BASE_CLIENT_OBJECT;

import java.util.ArrayList;

public class MyIntList extends MyList {

    // Variables
    MyObjects.MyInteger object;
    String name;

    // Constructor
    public MyIntList( BASE_CLIENT_OBJECT client, MyObjects.MyInteger object, String name ) {
        super( client, name );
        this.object = object;
        this.name = name;
    }

    public MyIntList( BASE_CLIENT_OBJECT client, MyObjects.MyInteger object, int optionalMaxSize, String name ) {
        super( client, optionalMaxSize, name );
        this.object = object;
    }

    @Override
    public void addMyVal() {
        list.add( object.getVal( ) );
    }

    @Override
    public void initList() {
        list = new ArrayList< MyObjects.MyInteger >( );
    }

    @Override
    public void fillList( Object object, int size ) {
        int v = ( int ) object;
        for ( int i = 0; i < size; i++ ) {
            list.add( v );
        }
    }

}