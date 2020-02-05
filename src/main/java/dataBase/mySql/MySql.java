package dataBase.mySql;

import arik.Arik;

import java.sql.Connection;
import java.sql.Statement;

public class MySql {

    private static ConnectionPool pool;
    private static Statement stmt;

    // Insert
    public static void insert(String query) {
        try {

            Connection conn = getPool().getConnection();
            stmt = conn.createStatement();

            // Execute
            stmt.execute(query);

            // Return connection
            getPool().releaseConnection(conn);

        } catch (Exception e) {
            e.printStackTrace();
            Arik.getInstance().sendMessage(e.getMessage() + "\n" + e.getCause());
        }
    }

    // Update
    public static void update(String query) {
        try {
            Connection conn = getPool().getConnection();
            stmt = conn.createStatement();

            // Execute
            stmt.executeUpdate(query);

            // Return connection
            getPool().releaseConnection(conn);
        } catch (Exception e) {
            Arik.getInstance().sendMessage(e.getMessage() + "\n" + e.getCause());
        }
    }

    // Update
    public static void select( String query ) {
            try
            {

                Connection conn = ConnectionPool.getConnectionsPoolInstance().getConnection();
                // create the java statement
                Statement st = conn.createStatement();

                // execute the query, and get a java resultset
                st.executeQuery(query);
            }
            catch (Exception e)
            {
                System.err.println("Got an exception! ");
                System.err.println(e.getMessage());
                Arik.getInstance().sendMessage(e.getMessage() + "\n" + e.getCause());
            } finally {
            }

    }


    // Get connection pool
    private static ConnectionPool getPool() {
        if (pool == null) {
            pool = ConnectionPool.getConnectionsPoolInstance();
        }
        return pool;
    }


}
