package dataBase.mySql;

import dataBase.mySql.mySqlComps.MySqlTable;
import dataBase.mySql.mySqlComps.TablesEnum;
import serverObjects.indexObjects.Spx;

import java.util.HashMap;
import java.util.Map;

public class TablesHandler {

    Map<TablesEnum, MySqlTable> tables = new HashMap<>();

    public static void main(String[] args) {
        Spx spx = Spx.getInstance();
        System.out.println("Done");
    }

    public MySqlTable getTable(TablesEnum tablesEnum) {
        return (MySqlTable) tables.get(tablesEnum);
    }

    public void addTable(TablesEnum tablesEnum, MySqlTable myTableSql) {
        tables.put(tablesEnum, myTableSql);
    }

    public Map<TablesEnum, MySqlTable> getTables() {
        return tables;
    }

}