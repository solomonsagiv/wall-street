package lists;

import locals.MyObjects;
import serverObjects.BASE_CLIENT_OBJECT;

import java.util.ArrayList;

public class MyDoubleList extends MyList {

    // Variables
    MyObjects.MyDouble object;

    // Constructor
    public MyDoubleList( BASE_CLIENT_OBJECT client, MyObjects.MyDouble object, String name ) {
        super(client, name);
        this.object = object;
    }

    public MyDoubleList( BASE_CLIENT_OBJECT client, MyObjects.MyDouble object, int optionalMaxSize, String name ) {
        super( client, optionalMaxSize, name );
        this.object = object;
    }

    @Override
    public void addMyVal() {
        list.add( object.getVal() );
    }

    @Override
    public void initList() {
        list = new ArrayList< MyObjects.MyDouble >();
    }

    @Override
    public void fillList( Object object, int size ) {
        double d = ( double ) object;
        for ( int i = 0; i < size; i++ ) {
            list.add( d );
        }
    }

}