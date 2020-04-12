package dataBase.mySql.mySqlComps;

public abstract class MyLoadAbleColumn<T> extends MyColumnSql {

    public MyLoadAbleColumn(MySqlTable myTableSql, String name, MySqlColumnEnum columnType) {
        super(myTableSql, name, columnType);
    }

    public abstract void setLoadedObject(T object);

    public abstract T getResetObject();
}
