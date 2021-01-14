package dataBase.mySql;

import java.util.HashMap;

public class MyDBConnections {

    public static void main( String[] args ) {
        MySql.insert( "INSERT INTO spx.index (time, value) VALUES ('now()', 5699)" );
    }

    // Static variables
    public static final int SLO_POSTGRES_LOCAL = 0;
    public static final int AMAZON_PARIS_SAGIV = 1;
    public static final int JIBE_POSTGRES = 2;

    // Variables
    HashMap< Integer, DBConnectionType > map = new HashMap<>( );

    // Constructor
    public MyDBConnections() {
        map.put( AMAZON_PARIS_SAGIV, new DBConnectionType( "jdbc:mysql://parisdb.chuxlqcvlex2.eu-west-3.rds.amazonaws.com:3306/", "sagivMasterUser", "Solomonsagivawsmaster12" ) );
        map.put( SLO_POSTGRES_LOCAL, new DBConnectionType( "jdbc:postgresql://localhost:5432/postgres", "postgres", "Solomonpostgres12" ) );
        map.put( JIBE_POSTGRES, new DBConnectionType( "jdbc:postgresql://52.4.58.207:5432/jibe", "jibe_admin", "160633a0cd2ab5a9b82f088a77240cb68f9232a8" ) );
    }
    
    public DBConnectionType getConnectionType( int type ) {
        return map.get( type );
    }

}

// DB connection type class
class DBConnectionType {

    // Variables
    private String url;
    private String user;
    private String password;

    // Constructor
    public DBConnectionType( String url, String user, String password ) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    // Getters and Setters
    public String getUser() {
        return user;
    }

    public String getUrl() {
        return url;
    }

    public String getPassword() {
        return password;
    }
}
