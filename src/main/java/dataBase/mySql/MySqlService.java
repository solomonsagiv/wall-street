package dataBase.mySql;

import api.Manifest;
import dataBase.mySql.dataUpdaters.IDataUpdater;
import serverObjects.BASE_CLIENT_OBJECT;
import service.MyBaseService;
import service.ServiceEnum;

//MySql class
public class MySqlService extends MyBaseService {

    BASE_CLIENT_OBJECT client;
    IDataUpdater dataUpdater;

    public MySqlService(BASE_CLIENT_OBJECT client, IDataUpdater dataUpdater ) {
        super(client);
        this.client = client;
        this.dataUpdater = dataUpdater;
    }

    @Override
    public void go() {
        // Updater
        if (Manifest.DB_UPDATER) {
            // TODO
        }

        // DB runner
        if (Manifest.DB_RUNNER) {

            // Insert line
            dataUpdater.insertData( getSleep() );

            // Arrays


        }
    }

    @Override
    public String getName() {
        return "mysql";
    }

    @Override
    public int getSleep() {
        return 100;
    }

    @Override
    public ServiceEnum getType() {
        return ServiceEnum.MYSQL_RUNNER;
    }
}

