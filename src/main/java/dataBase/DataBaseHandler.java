package dataBase;

import basketFinder.MiniStock;
import dataBase.mySql.TablesHandler;
import dataBase.mySql.mySqlComps.TablesEnum;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.INDEX_CLIENT_OBJECT;

import java.util.Map;

public class DataBaseHandler {

    BASE_CLIENT_OBJECT client;

    public DataBaseHandler( BASE_CLIENT_OBJECT client ) {
        this.client = client;
    }

    public void load() {
        TablesHandler th = client.getTablesHandler( );
        th.getTable( TablesEnum.TWS_CONTRACTS ).load( );
        th.getTable( TablesEnum.STATUS ).load( );
        th.getTable( TablesEnum.ARRAYS ).load( );

        // Index stocks weight
        if ( client instanceof INDEX_CLIENT_OBJECT ) {
            th.getTable( TablesEnum.INDEX_STOCKS ).load();

            for ( Map.Entry< Integer, MiniStock > entry : ( ( INDEX_CLIENT_OBJECT ) client ).getStocksHandler().getStocksMap().entrySet()) {
                System.out.println( entry.getValue().getWeight() );
            }
        }
    }
    
}
