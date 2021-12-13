package dataBase;

import dataBase.mySql.ConnectionPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class testMySql {

    private static Connection conn;
    private static Statement stmt;

    public static Connection awsConn() throws ClassNotFoundException, SQLException {

        String url = "jdbc:mysql://sagivwork.test.us-east-1.rds.amazonaws.com:3306/";
        String userName = "sagivMasterUser";
        String password = "Solomonsagivawsmaster12";
        String dbName = "stocks";
        String driver = "com.mysql.jdbc.Driver";

        return DriverManager.getConnection(url + dbName, userName, password);

    }

    // Connect to DB
    public static Connection connectToDB() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager
                    .getConnection("jdbc:mysql://localhost/stocks?user=hb&password=hb&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
