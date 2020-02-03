package dataBase.mySql.tables;

public interface IMYsqlTable {

    void initColumns();
    void insert();
    void load();
    void update();
    void reset();
    Object getTableObject();

}
