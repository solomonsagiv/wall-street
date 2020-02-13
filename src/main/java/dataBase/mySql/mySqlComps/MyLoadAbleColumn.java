package dataBase.mySql.mySqlComps;

public abstract class MyLoadAbleColumn<T> extends MyColumnSql {

    public MyLoadAbleColumn( MyTableSql myTableSql, String name, int type ) {
        super( myTableSql, name, type );
    }

    public abstract void setLoadedObject( T object );

    public abstract T getResetObject();
}
