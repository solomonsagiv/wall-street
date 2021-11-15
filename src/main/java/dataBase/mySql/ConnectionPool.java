package dataBase.mySql;

import api.Manifest;
import arik.Arik;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool implements IConnectionPool {


    public static void main(String[] args) {
        ConnectionPool connectionPool = ConnectionPool.getConnectionsPoolInstance();
    }

    private static final int MAX_POOL_SIZE = 50;

    // Instance
    private static ConnectionPool connectionPool;
    private String url;
    private String user;
    private String password;
    private List<Connection> connections;
    private List<Connection> usedConnections = new ArrayList<>();


    static boolean connected = false;

    // Constructor
    private ConnectionPool(String url, String user, String password, List<Connection> connections) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.connections = connections;
    }

    public static ConnectionPool getConnectionsPoolInstance(int connection_count) {
        if (connectionPool == null) {
            try {
                MyDBConnections dbConnections = new MyDBConnections();
                DBConnectionType connectionType = dbConnections.getConnectionType(Manifest.DB_CONNECTION_TYPE);
                connectionPool = ConnectionPool.create(connectionType, connection_count);
            } catch (Exception e) {
                Arik.getInstance().sendMessage(e.getMessage() + "\n" + e.getCause());
            }
        }
        return connectionPool;
    }

    public static ConnectionPool getConnectionsPoolInstance() {
        if (connectionPool == null) {
            try {
                MyDBConnections dbConnections = new MyDBConnections();
                DBConnectionType connectionType = dbConnections.getConnectionType(Manifest.DB_CONNECTION_TYPE);
                connectionPool = ConnectionPool.create(connectionType, Manifest.POOL_SIZE);
            } catch (Exception e) {
                Arik.getInstance().sendMessage(e.getMessage() + "\n" + e.getCause());
            }
        }
        return connectionPool;
    }


    public static ConnectionPool create(DBConnectionType dbConnectionType, int connection_count) throws SQLException {
        List<Connection> pool = new ArrayList<>();
        try {
            for (int i = 0; i < connection_count; i++) {
                new Thread(() -> {
                    try {
                        System.out.println("Connection");
                        pool.add(createConnection(dbConnectionType.getUrl(), dbConnectionType.getUser(), dbConnectionType.getPassword()));
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }).start();
            }
        } finally {
            return new ConnectionPool(dbConnectionType.getUrl(), dbConnectionType.getUser(), dbConnectionType.getPassword(), pool);
        }
    }

    // standard constructors
    private static Connection createConnection(
            String url, String user, String password)
            throws SQLException {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(url, user, password);
    }

    public int getConnectionsCount() {
        return connections.size();
    }

    public int getUseConnectionsCount() {
        return usedConnections.size();
    }

    public void addConnection() throws SQLException, ClassNotFoundException {
        connections.add(createConnection(url, user, password));
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (connections.isEmpty()) {
            if (usedConnections.size() < MAX_POOL_SIZE) {
                connections.add(createConnection(url, user, password));
            } else {
                throw new RuntimeException(
                        "Maximum pool size reached, no available connections!");
            }
        }

        Connection connection = connections.remove(connections.size() - 1);
        usedConnections.add(connection);
        return connection;
    }

    public void shutdown() throws SQLException {
        usedConnections.forEach(this::releaseConnection);
        for (Connection c : connections) {
            c.close();
        }
        connections.clear();
    }

    @Override
    public boolean releaseConnection(Connection connection) {
        try {
            if (connection != null && !connection.isClosed() && !connections.contains(connection)) {
                connections.add(connection);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return usedConnections.remove(connection);
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
        return connections.size() + usedConnections.size();
    }
}