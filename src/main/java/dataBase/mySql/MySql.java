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
        Connection conn = null;
        try {

            conn = getPool( ).getConnection( );
            stmt = conn.createStatement( );

            // Execute
            stmt.execute( query );
            
            System.out.println( query );

        } catch ( Exception e ) {
            e.printStackTrace( );
            Arik.getInstance( ).sendMessage( e.getMessage( ) + "\n" + e.getCause( ) );
        } finally {
            // Release connection
            if ( conn != null ) {
                getPool( ).releaseConnection( conn );
            }
        }
    }

    // Update
    public static void update( String query ) {
        Connection conn = null;
        try {
            conn = getPool( ).getConnection( );
            stmt = conn.createStatement( );

            // Execute
            stmt.executeUpdate( query );

        } catch ( Exception e ) {
            Arik.getInstance( ).sendMessage( e.getMessage( ) + "\n" + e.getCause( ) );
        } finally {
            // Release connection
            if ( conn != null ) {
                getPool( ).releaseConnection( conn );
            }
        }
    }

    // Update
    public static ResultSet select( String query ) {
        Statement st = null;
        ResultSet rs = null;

        Connection conn = null;

        try {
            conn = ConnectionPool.getConnectionsPoolInstance( ).getConnection( );

            // create the java statement
            st = conn.createStatement( );

            // execute the query, and get a java resultset
            rs = st.executeQuery( query );

        } catch ( Exception e ) {
            Arik.getInstance( ).sendErrorMessage( e );
        } finally {
            // Release connection
            if ( conn != null ) {
                getPool( ).releaseConnection( conn );
            }
        }
        return rs;
    }

    public static void trunticate( String tableName, String schema ) {

        String query = "TRUNCATE TABLE " + schema + "." + tableName;
        Statement st = null;
        Connection conn = null;
        try {

            conn = ConnectionPool.getConnectionsPoolInstance( ).getConnection( );
            // create the java statement
            st = conn.createStatement( );

            // execute the query, and get a java resultset
            st.executeUpdate( query );

        } catch ( Exception e ) {
            System.err.println( e.getMessage( ) );
            Arik.getInstance( ).sendErrorMessage( e );
        } finally {
            // Release connection
            if ( conn != null ) {
                getPool( ).releaseConnection( conn );
            }
        }
    }

    // Get connection pool
    public static ConnectionPool getPool() {
        if ( pool == null ) {
            pool = ConnectionPool.getConnectionsPoolInstance( );
        }
        return pool;
    }


}
