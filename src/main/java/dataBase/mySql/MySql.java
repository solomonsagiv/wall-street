package dataBase.mySql;

import arik.Arik;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

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
            e.printStackTrace( );
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

    // Update
    public static ResultSet select( String query ) {
        Statement st = null;
        ResultSet rs = null;
        try {
            Connection conn = ConnectionPool.getConnectionsPoolInstance( ).getConnection( );
            // create the java statement
            st = conn.createStatement( );

            // execute the query, and get a java resultset
            rs = st.executeQuery( query );
        } catch ( Exception e ) {
            System.err.println( e.getMessage( ) );
            Arik.getInstance( ).sendErrorMessage( e );
        }
        return rs;
    }


    // Get connection pool
    private static ConnectionPool getPool() {
        if ( pool == null ) {
            pool = ConnectionPool.getConnectionsPoolInstance( );
        }
        return pool;
    }


}
