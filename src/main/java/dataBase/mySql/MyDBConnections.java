package dataBase.mySql;

import java.util.HashMap;

public class MyDBConnections {

    // Static variables
    public static final int JIBE_POSTGRES = 2;
    public static final int SLO_POSTGRES = 3;

    // Variables
    HashMap<Integer, DBConnectionType> map = new HashMap<>();
    
    // Constructor
    public MyDBConnections() {
        map.put(SLO_POSTGRES, new DBConnectionType("jdbc:postgresql://52.73.213.15:5432/jibe", "jibe_admin", "160633a0cd2ab5a9b82f088a77240cb68f9232a8"));
        map.put(JIBE_POSTGRES, new DBConnectionType("jdbc:postgresql://52.4.58.207:5432/jibe", "sagiv", "f19add32-1141-4af5-9abd-4744487f3b51"));
    }

    public DBConnectionType getConnectionType(int type) {
        return map.get(type);
    }

}

// DB connection type class
class DBConnectionType {

    // Variables
    private String url;
    private String user;
    private String password;

    // Constructor
    public DBConnectionType(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    // Getters and Setters
    public String getUser() {
        return user;
    }

    public String getUrl() {
        return url;
    }

    public String getPassword() {
        return password;
    }
}
