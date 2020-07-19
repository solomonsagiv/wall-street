package dataBase;

import dataBase.mySql.TablesHandler;
import dataBase.mySql.mySqlComps.TablesEnum;
import serverObjects.BASE_CLIENT_OBJECT;

public class DataBaseHandler {

    BASE_CLIENT_OBJECT client;

    public DataBaseHandler(BASE_CLIENT_OBJECT client) {
        this.client = client;
    }

    public void load() {
        TablesHandler th = client.getTablesHandler();
//        System.out.println(client.getName() + "load 0" );

        th.getTable(TablesEnum.TWS_CONTRACTS).load();
//        System.out.println(client.getName() + "load 1" );

        th.getTable(TablesEnum.STATUS).load();
//        System.out.println(client.getName() + "load 2" );

        th.getTable(TablesEnum.ARRAYS).load();
//        System.out.println(client.getName() + "load 3" );

    }

}
