package dataBase;

import dataBase.mySql.TablesHandler;
import dataBase.mySql.mySqlComps.MySqlTable;
import dataBase.mySql.mySqlComps.TablesEnum;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Spx;

import java.util.Map;

public class DataBaseHandler {

    BASE_CLIENT_OBJECT client;

    public DataBaseHandler( BASE_CLIENT_OBJECT client ) {
        this.client = client;
    }

    public void load() {
        TablesHandler th = client.getTablesHandler( );
        for ( Map.Entry< TablesEnum, MySqlTable > entry : th.getTables( ).entrySet( ) ) {
            try {
                MySqlTable table = entry.getValue();
                table.load();
            } catch ( Exception e ) {
                e.printStackTrace();
            }

        }
    }

}
