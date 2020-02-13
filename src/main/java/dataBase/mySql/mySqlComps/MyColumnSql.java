package dataBase.mySql.mySqlComps;

import serverObjects.BASE_CLIENT_OBJECT;

public abstract class MyColumnSql<T> {

    // Variables
    protected MyTableSql myTableSql;
    protected String name;
    BASE_CLIENT_OBJECT client;
    public Class<T> myClass;

    // Constructor
    public MyColumnSql( MyTableSql myTableSql, String name ) {
        this.myTableSql = myTableSql;
        this.name = name;
        this.client = myTableSql.client;
        myTableSql.addColumn( this );
    }

    // Abstracts functions
    public abstract T getObject();

    public abstract void setLoadedObject(T object );

    public abstract Class getClassType();



}

