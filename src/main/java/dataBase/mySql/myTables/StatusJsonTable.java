package dataBase.mySql.myTables;

import dataBase.mySql.myBaseTables.MyStatusTable;
import dataBase.mySql.mySqlComps.MyColumnSql;
import dataBase.mySql.mySqlComps.MySqlColumnEnum;
import serverObjects.BASE_CLIENT_OBJECT;

import java.time.LocalDate;
import java.time.LocalTime;

public class StatusJsonTable extends MyStatusTable {

    // Constructor
    public StatusJsonTable( BASE_CLIENT_OBJECT client, String tableName ) {
        super(client, tableName);
    }

    @Override
    public void initColumns() {
        addColumn(new MyColumnSql<>(this, MySqlColumnEnum.NAME) {
            @Override
            public String getObject() {
                return client.getName();
            }
        });
        addColumn(new MyColumnSql<>(this, MySqlColumnEnum.date) {
            @Override
            public String getObject() {
                return LocalDate.now().toString();
            }
        });
        addColumn(new MyColumnSql<>(this, MySqlColumnEnum.time) {
            @Override
            public String getObject() {
                return LocalTime.now().toString();
            }
        });
        addColumn(new MyColumnSql<>(this, MySqlColumnEnum.data) {
            @Override
            public String getObject() {
                return client.getAsJson().toString();
            }
        });
    }
}
