package exp;

import serverObjects.BASE_CLIENT_OBJECT;

public class E extends Exp {

    public E( BASE_CLIENT_OBJECT client, String expEnum ) {
        super( client, expEnum );
        initSeries( );
    }

}