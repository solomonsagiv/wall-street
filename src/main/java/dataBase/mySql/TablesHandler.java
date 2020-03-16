package dataBase.mySql;

import dataBase.mySql.mySqlComps.MyTableSql;
import dataBase.mySql.myTables.TablesEnum;
import serverObjects.BASE_CLIENT_OBJECT;

import java.util.*;

public class TablesHandler {

    Map tables = new HashMap< TablesEnum, MyTableSql>();

    public MyTableSql getTable( TablesEnum tablesEnum ) {
        return ( MyTableSql ) tables.get( tablesEnum );
    }

    public void addTable( TablesEnum tablesEnum, MyTableSql myTableSql ) {
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

    public String getArraysName() {
        return "arrays";
    }

    public Map getTables() {
        return tables;
    }
}
