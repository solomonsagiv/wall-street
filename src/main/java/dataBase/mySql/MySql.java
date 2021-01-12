package dataBase.mySql;

import arik.Arik;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MySql {

    private static ConnectionPool pool;

    // Insert
    public static void insert( String query ) {
        Connection conn = null;
        try {

            conn = getPool( ).getConnection( );
            Statement stmt = conn.createStatement( );

            // Execute
            stmt.execute( query );

//            System.out.println( query );

        } catch ( Exception e ) {
            e.printStackTrace( );
            Arik.getInstance( ).sendMessage( e.getMessage( ) + "\n" + e.getCause( ) + " \n" + "Insert" );
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
            Statement stmt = conn.createStatement( );

            // Execute
            stmt.executeUpdate( query );
        } catch ( Exception e ) {
            Arik.getInstance( ).sendMessage( e.getMessage( ) + "\n" + e.getCause( ) + " \n" + "Update" );
        } finally {
            // Release connection
            if ( conn != null ) {
                getPool( ).releaseConnection( conn );
            }
        }
    }

    // Update
    public static ResultSet select( String query ) {
        ResultSet rs = null;
        Connection conn = null;

        try {
            conn = getPool( ).getConnection( );

            // create the java statement
            Statement st = conn.createStatement( );

            // execute the query, and get a java resultset
            rs = st.executeQuery( query );

//            System.out.println( query );

        } catch ( Exception e ) {
            Arik.getInstance( ).sendMessage( e.getMessage( ) + "\n" + e.getCause( ) + " \n" + "Select" );
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
        Connection conn = null;
        try {

            conn = getPool( ).getConnection( );
            // create the java statement
            Statement st = conn.createStatement( );

            // execute the query, and get a java resultset
            st.executeUpdate( query );

        } catch ( Exception e ) {
            Arik.getInstance( ).sendMessage( e.getMessage( ) + "\n" + e.getCause( ) + " \n" + "Trunticate" );
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