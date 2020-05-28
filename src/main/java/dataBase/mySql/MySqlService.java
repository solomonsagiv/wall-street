package dataBase.mySql;

import api.Manifest;
import dataBase.mySql.mySqlComps.TablesEnum;
import serverObjects.BASE_CLIENT_OBJECT;
import service.MyBaseService;
import service.ServiceEnum;

//MySql class
public class MySqlService extends MyBaseService {

    BASE_CLIENT_OBJECT client;

    public MySqlService( BASE_CLIENT_OBJECT client ) {
        super( client );
        this.client = client;
    }

    @Override
    public void go() {
        // Updater
        if ( Manifest.DB_UPDATER ) {
            // Status
            client.getTablesHandler( ).getTable( TablesEnum.STATUS ).update( );
        }

        // DB runner
        if ( Manifest.DB_RUNNER ) {

            // Insert line
            client.getTablesHandler( ).getTable( TablesEnum.DAY ).insert( );

            // Arrays
            if ( sleepCount % 4000 == 0 ) {
                client.getTablesHandler( ).getTable( TablesEnum.ARRAYS ).insert( );
            }
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

