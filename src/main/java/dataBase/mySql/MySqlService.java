package dataBase.mySql;

import api.Manifest;
import serverObjects.BASE_CLIENT_OBJECT;
import service.MyBaseService;
import tables.TablesHandler;

//MySql class
public class MySqlService extends MyBaseService {

    BASE_CLIENT_OBJECT client;
    TablesHandler tablesHandler;

    public MySqlService(BASE_CLIENT_OBJECT client, String name, int type, int sleep) {
        super(client, name, type, sleep);
        this.tablesHandler = client.getTablesHandler();
    }

    @Override
    public void go() {

//        // Updater
//        if (Manifest.DB_UPDATER) {
//            // Status
//            if (sleepCount % 1000 == 0) {
//                client.getTablesHandler().getStatusHandler().getHandler().updateData();
//            }
//        }
//
//        // DB runner
//        if (Manifest.DB_RUNNER) {
//
//            // Insert line
//            client.getTablesHandler().getDayHandler().getHandler().insertLine();
//
//            // Arrays
//            if (sleepCount % 30000 == 0) {
//                client.getTablesHandler().getArrayHandler().getHandler().updateData();
//            }
//
//            // Reset sleep count
//            if (sleepCount == 3000000) {
//                sleepCount = 0;
//            }
//        }
    }
}

