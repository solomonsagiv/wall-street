package dataBase.mySql;

import dataBase.mySql.mySqlComps.MySqlTable;
import dataBase.mySql.mySqlComps.TablesEnum;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Spx;

import java.util.*;

public class TablesHandler {

    public static void main( String[] args ) {
        Spx spx = Spx.getInstance();
        spx.getTablesHandler().getTable( TablesEnum.SUM ).insert();
        System.out.println( "Done" );
    }


    Map tables = new HashMap< TablesEnum, MySqlTable>();

    public MySqlTable getTable( TablesEnum tablesEnum ) {
        return ( MySqlTable ) tables.get( tablesEnum );
    }

    public void addTable( TablesEnum tablesEnum, MySqlTable myTableSql ) {
        tables.put( tablesEnum,  myTableSql );
    }

    public String getDayName( BASE_CLIENT_OBJECT client ) {
        return client.getName() + "JsonDay";
    }

    public String getSumName( BASE_CLIENT_OBJECT client ) {
        return client.getName() + "JsonSum";
    }

    public String getStatusName() {
        return "jsonStatus";
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
