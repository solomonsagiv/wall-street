package api;

import dataBase.mySql.MyDBConnections;

public class Manifest {

    // REAL ACCOUNT
    public static int REAL_PORT = 4343;
    public static int CLIENT_ID = 999;
    public static String REAL_ACCOUNT = "U2177653";

    // SNUPPI ACCOUNT
    public static int SNUPPI_PORT = 7496;
    public static String SNUPPI_ACCOUNT = "U3450352";

    // TEST ACCOUNT
    public static String TEST_ACCOUNT = "DU1293791";
    public static int TEST_PORT = 3333;

    // GATEWAY
    public static int GATEWAY_PORT = 4001;

    // SELECTED ACCOUNT
    public static boolean DB = true;
    public static boolean DB_RUNNER = true;
    public static boolean DB_UPDATER = true;
    public static boolean OPEN_CHARTS = true;
    public static final int DB_CONNECTION_TYPE = MyDBConnections.JIBE_POSTGRES;
    public static String ACCOUNT = SNUPPI_ACCOUNT;
    public static int PORT = GATEWAY_PORT;
    public static int SCREEN = 0;
    /*
     * On expiration change
     * 1. EXPIRY date
     * 2. EXP name
     * */
}