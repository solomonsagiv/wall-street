package dataBase;

import dataBase.mySql.ConnectionPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class testMySql {

    private static Connection conn;
    private static Statement stmt;

    public static void main( String[] args ) throws SQLException, ClassNotFoundException {

        conn = ConnectionPool.getConnectionsPoolInstance( ).getConnection( );
        stmt = conn.createStatement( );

        System.out.println( "Connected" );
        String sql = "INSERT INTO `stocks`.`spx_daily` ( `date`, `exp_name`, `open`, `high`, `low`, `close`, `con_up`, `con_down`, `index_up`, `index_down`, `op_avg`, `base`, `options`) VALUES ( '20', 'sd', '3434', '546', '7676', '878', '9', '78', '78', '97', '0.3', '4554', 'gmfg');";

        for ( int i = 0; i < 300; i++ ) {
            stmt.execute( sql );
        }

        System.out.println( "Done" );

    }

    public static Connection awsConn() throws ClassNotFoundException, SQLException {

        String url = "jdbc:mysql://sagivwork.test.us-east-1.rds.amazonaws.com:3306/";
        String userName = "sagivMasterUser";
        String password = "Solomonsagivawsmaster12";
        String dbName = "stocks";
        String driver = "com.mysql.jdbc.Driver";

        return DriverManager.getConnection( url + dbName, userName, password );

    }


    // Connect to DB
    public static Connection connectToDB() {
        Connection conn = null;
        try {
            Class.forName( "com.mysql.jdbc.Driver" );
            conn = DriverManager
                    .getConnection( "jdbc:mysql://localhost/stocks?user=hb&password=hb&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC" );
        } catch ( ClassNotFoundException | SQLException e ) {
            e.printStackTrace( );
        }
        return conn;
    }
}
