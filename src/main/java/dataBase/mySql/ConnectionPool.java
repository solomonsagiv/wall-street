package dataBase.mySql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool implements IConnectionPool {

    private static final int MAX_POOL_SIZE = 30;
    // Instance
    private static ConnectionPool connectionPool;
    private static int INITIAL_POOL_SIZE = 5;
    private String url;
    private String user;
    private String password;
    private List< Connection > connections;
    private List< Connection > usedConnections = new ArrayList<>( );

    private ConnectionPool( String url, String user, String password, List< Connection > connections ) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.connections = connections;
    }

    public static ConnectionPool getConnectionsPoolInstance() {
        if ( connectionPool == null ) {
            try {
                String url = "jdbc:mysql://parisdb.chuxlqcvlex2.eu-west-3.rds.amazonaws.com:3306/";
                String user = "sagivMasterUser";
                String password = "Solomonsagivawsmaster12";

                connectionPool = ConnectionPool.create( url, user, password );
            } catch ( Exception e ) {
//                Arik.getInstance( ).sendMessage( e.getMessage( ) + "\n" + e.getCause( ) );
            }
        }
        return connectionPool;
    }

    public static ConnectionPool create( String url, String user, String password ) throws SQLException {

        List< Connection > pool = new ArrayList<>( INITIAL_POOL_SIZE );
        try {
            for ( int i = 0; i < INITIAL_POOL_SIZE; i++ ) {
                new Thread( () -> {
                    try {
                        pool.add( createConnection( url, user, password ) );
                    } catch ( SQLException throwables ) {
                        throwables.printStackTrace( );
                    }
                } ).start( );

            }
        } finally {
            return new ConnectionPool( url, user, password, pool );
        }

    }

    // standard constructors

    private static Connection createConnection(
            String url, String user, String password )
            throws SQLException {
        return DriverManager.getConnection( url, user, password );
    }

    @Override
    public Connection getConnection() throws SQLException {
        if ( connections.isEmpty( ) ) {
            if ( usedConnections.size( ) < MAX_POOL_SIZE ) {
                connections.add( createConnection( url, user, password ) );
            } else {
                throw new RuntimeException(
                        "Maximum pool size reached, no available connections!" );
            }
        }

        Connection connection = connections.remove( connections.size( ) - 1 );
        usedConnections.add( connection );
        return connection;
    }

    public void shutdown() throws SQLException {
        usedConnections.forEach( this::releaseConnection );
        for ( Connection c : connections ) {
            c.close( );
        }
        connections.clear( );
    }

    @Override
    public boolean releaseConnection( Connection connection ) {
        connections.add( connection );
        return usedConnections.remove( connection );
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getUser() {
        return user;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public int getSize() {
        return connections.size( ) + usedConnections.size( );
    }


    // standard getters
}
