package dataBase.mySql;

import dataBase.mySql.mySqlComps.MySqlTable;
import dataBase.mySql.mySqlComps.TablesEnum;
import serverObjects.indexObjects.Spx;
import serverObjects.stockObjects.Apple;

import java.util.HashMap;
import java.util.Map;

public class TablesHandler {

    public static void main( String[] args ) {
        Spx spx = Spx.getInstance( );
        spx.getTablesHandler( ).getTable( TablesEnum.STATUS ).update( );
        System.out.println( "Done" );
    }

    Map tables = new HashMap< TablesEnum, MySqlTable >( );

    public MySqlTable getTable( TablesEnum tablesEnum ) {
        return ( MySqlTable ) tables.get( tablesEnum );
    }

    public void addTable( TablesEnum tablesEnum, MySqlTable myTableSql ) {
        tables.put( tablesEnum, myTableSql );
    }

    public Map getTables() {
        return tables;
    }

}