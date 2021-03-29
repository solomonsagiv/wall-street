package dataBase.mySql;

import api.Manifest;
import dataBase.mySql.dataUpdaters.IDataBaseHandler;
import serverObjects.BASE_CLIENT_OBJECT;
import service.MyBaseService;
import service.ServiceEnum;

//MySql class
public class MySqlService extends MyBaseService {

    BASE_CLIENT_OBJECT client;
    IDataBaseHandler dataBaseHandler;

    public MySqlService(BASE_CLIENT_OBJECT client, IDataBaseHandler dataBaseHandler) {
        super(client);
        this.client = client;
        this.dataBaseHandler = dataBaseHandler;
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
            dataBaseHandler.insertData(getSleep());

            // Arrays

        }
    }

    public IDataBaseHandler getDataBaseHandler() {
        return dataBaseHandler;
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

