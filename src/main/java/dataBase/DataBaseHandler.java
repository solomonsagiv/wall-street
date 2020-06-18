package dataBase;

import dataBase.mySql.TablesHandler;
import dataBase.mySql.mySqlComps.TablesEnum;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Spx;

public class DataBaseHandler {

    BASE_CLIENT_OBJECT client;

    public DataBaseHandler( BASE_CLIENT_OBJECT client ) {
        this.client = client;
    }

    public void load() {
        TablesHandler th = client.getTablesHandler();
        th.getTable(TablesEnum.TWS_CONTRACTS).load();
        th.getTable(TablesEnum.STATUS).load();
        th.getTable(TablesEnum.ARRAYS).load();

        if ( client instanceof Spx ) {
            client.getTablesHandler().getTable(TablesEnum.INDEX_STOCKS).loadAll();
        }

    }

}
