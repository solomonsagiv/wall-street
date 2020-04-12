package dataBase.mySql;

import dataBase.mySql.mySqlComps.MySqlTable;
import dataBase.mySql.mySqlComps.TablesEnum;
import serverObjects.BASE_CLIENT_OBJECT;

import java.util.*;

public class TablesHandler {

    Map tables = new HashMap< TablesEnum, MySqlTable>();

    public MySqlTable getTable( TablesEnum tablesEnum ) {
        return ( MySqlTable ) tables.get( tablesEnum );
    }

    public void addTable( TablesEnum tablesEnum, MySqlTable myTableSql ) {
        tables.put( tablesEnum,  myTableSql );
    }

    public String getDayName( BASE_CLIENT_OBJECT client ) {
        return client.getName();
    }

    public String getSumName( BASE_CLIENT_OBJECT client ) {
        return client.getName() + "_sum";
    }

    public String getStatusName() {
        return "status";
    }

    public String getSettingName() {
        return "settings";
    }

    public String getArraysName() {
        return "arrays";
    }

    public Map getTables() {
        return tables;
    }
}
