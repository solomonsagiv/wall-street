package tables;

public interface ITablesHandler {

    void insertLine();

    Object getTableObject();

    void loadData();

    void resetData();

    void updateData();

    void updateObject();
}
