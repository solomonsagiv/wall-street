package dataBase.mySql.mySqlComps;

import serverObjects.BASE_CLIENT_OBJECT;

import java.rmi.UnknownHostException;
import java.text.ParseException;

public abstract class MyColumnSql<T> {

    public static final int STRING = 0;
    public static final int DOUBLE = 1;
    public static final int INT = 2;

    // Variables
    protected MySqlTable myTableSql;
    protected MySqlColumnEnum type;
    BASE_CLIENT_OBJECT client;

    // Constructor
    public MyColumnSql(MySqlTable myTableSql, MySqlColumnEnum type) {
        this.myTableSql = myTableSql;
        this.client = myTableSql.getClient();
        this.type = type;
    }

    // Abstracts functions
    public abstract T getObject() throws UnknownHostException, ParseException;

    // Getters and setters
    public MySqlColumnEnum getType() {
        return type;
    }
}

