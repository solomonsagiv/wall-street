package dataBase.mySql;

public class MySqlTablesHandler {

    // Variables
    ConnectionPool connections;

    // Tables


    // Constructor
    public MySqlTablesHandler() {

        connections = ConnectionPool.getConnectionsPoolInstance( );

    }


}
