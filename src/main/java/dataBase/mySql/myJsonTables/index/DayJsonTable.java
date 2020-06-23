package dataBase.mySql.myJsonTables.index;

import api.Manifest;
import dataBase.mySql.myBaseTables.MyDayTable;
import dataBase.mySql.mySqlComps.MyColumnSql;
import dataBase.mySql.mySqlComps.MySqlColumnEnum;
import serverObjects.BASE_CLIENT_OBJECT;

import java.time.LocalDate;
import java.time.LocalTime;

public class DayJsonTable extends MyDayTable {

    // Constructor
    public DayJsonTable(BASE_CLIENT_OBJECT client) {
        super(client);
    }

    @Override
    public String getName() {
        return client.getName() + "JsonDay";
    }

    @Override
    public void initColumns() {
        addColumn(new MyColumnSql<String>(this, MySqlColumnEnum.date) {
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
        addColumn(new MyColumnSql<String>(this, MySqlColumnEnum.data) {
            @Override
            public String getObject() {
                return client.getAsJson().toString();
            }
        });

    }
}
