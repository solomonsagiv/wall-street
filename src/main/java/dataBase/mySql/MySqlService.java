package dataBase.mySql;

import api.Manifest;
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
                client.getMyTableHandler().getMyStatusTable().update();
            }
        }

        // DB runner
        if (Manifest.DB_RUNNER) {

            // Insert line
            client.getMyTableHandler().getMyDayTable().insert();

            // Arrays
            if (sleepCount % 30000 == 0) {
                client.getMyTableHandler().getMyArraysTable().update();
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

