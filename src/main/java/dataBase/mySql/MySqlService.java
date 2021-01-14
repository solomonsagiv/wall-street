package dataBase.mySql;

import api.Manifest;
import dataBase.mySql.mySqlComps.TablesEnum;
import serverObjects.BASE_CLIENT_OBJECT;
import service.MyBaseService;
import service.ServiceEnum;

//MySql class
public class MySqlService extends MyBaseService {

    BASE_CLIENT_OBJECT client;

    public MySqlService(BASE_CLIENT_OBJECT client) {
        super(client);
        this.client = client;
    }

    @Override
    public void go() {
        // Updater
        if (Manifest.DB_UPDATER) {
            // TODO
            /
        }

        // DB runner
        if (Manifest.DB_RUNNER) {

            // Insert line
            // TODO

            // Arrays
            // TODO
        }
    }

    @Override
    public String getName() {
        return "mysql";
    }

    @Override
    public int getSleep() {
        return 2000;
    }

    @Override
    public ServiceEnum getType() {
        return ServiceEnum.MYSQL_RUNNER;
    }
}

