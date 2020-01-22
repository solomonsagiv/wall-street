package dataBase;

import serverObjects.BASE_CLIENT_OBJECT;

public class DB {

    // Mysql
    private MySqlRunner mySql;

    // Base variables
    private BASE_CLIENT_OBJECT client;

    public DB( BASE_CLIENT_OBJECT client ) {
        this.client = client;
        mySql = new MySqlRunner( client );
    }

    public void startAll() {

        getMySql( ).getHandler( ).start( );

    }

    public void closeAll() {

        getMySql( ).getHandler( ).close( );

    }

    public String str( Object o ) {
        return String.valueOf( o );
    }

    public MySqlRunner getMySql() {
        if ( mySql == null ) {
            mySql = new MySqlRunner( client );
        }
        return mySql;
    }

    public void setMySql( MySqlRunner mySql ) {
        this.mySql = mySql;
    }

}
