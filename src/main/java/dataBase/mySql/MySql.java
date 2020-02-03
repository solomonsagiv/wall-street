package dataBase.mySql;

import arik.Arik;

import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Calendar;

public class MySql {

    private static ConnectionPool pool;
    private static Statement stmt;

    // Insert
    public static void insert( String query ) {
        try {

            Connection conn = getPool( ).getConnection( );
            stmt = conn.createStatement( );

            // Execute
            stmt.execute( query );

            // Return connection
            getPool( ).releaseConnection( conn );

        } catch ( Exception e ) {
            e.printStackTrace();
            Arik.getInstance( ).sendMessage( e.getMessage( ) + "\n" + e.getCause( ) );
        }
    }

    // Update
    public static void update( String query ) {
        try {
            Connection conn = getPool( ).getConnection( );
            stmt = conn.createStatement( );

            // Execute
            stmt.executeUpdate( query );

            // Return connection
            getPool( ).releaseConnection( conn );
        } catch ( Exception e ) {
            Arik.getInstance( ).sendMessage( e.getMessage( ) + "\n" + e.getCause( ) );
        }
    }

    // Get connection pool
    private static ConnectionPool getPool() {
        if ( pool == null ) {
            pool = ConnectionPool.getConnectionsPoolInstance( );
        }
        return pool;
    }


}
