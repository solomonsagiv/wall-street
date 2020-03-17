package dataBase.mySql;

import api.Manifest;
import dataBase.mySql.myTables.TablesEnum;
import serverObjects.BASE_CLIENT_OBJECT;
import service.MyBaseService;

//MySql class
public class MySqlService extends MyBaseService {

    BASE_CLIENT_OBJECT client;

    public MySqlService(BASE_CLIENT_OBJECT client) {
        super( client );
        this.client = client;
    }

    @Override
    public void go() {

        // Updater
        if (Manifest.DB_UPDATER) {
            // Status
            if (sleepCount % 1000 == 0) {
                client.getTablesHandler().getTable( TablesEnum.STATUS ).update();
            }
        }

        // DB runner
        if (Manifest.DB_RUNNER) {
            
            // Insert line
            client.getTablesHandler().getTable(TablesEnum.DAY).insert();

            // Arrays
            if (sleepCount % 30000 == 0) {
                client.getTablesHandler().getTable(TablesEnum.ARRAYS).update();
            }

            // Reset sleep count
            if (sleepCount == 3000000) {
                sleepCount = 0;
            }
        }
    }

    @Override
    public String getName() {
        return "mysql";
    }

    @Override
    public int getSleep() {
        return 500;
    }

    @Override
    public int getType() {
        return MyBaseService.MYSQL_RUNNER;
    }
}

