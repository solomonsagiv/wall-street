package dataBase.mySql.myTables;

import dataBase.mySql.myBaseTables.MyStatusTable;
import dataBase.mySql.mySqlComps.MyColumnSql;
import dataBase.mySql.mySqlComps.MyLoadAbleColumn;
import dataBase.mySql.mySqlComps.MySqlColumnEnum;
import myJson.MyJson;
import options.Options;
import roll.RollEnum;
import serverObjects.BASE_CLIENT_OBJECT;

import java.time.LocalDate;
import java.time.LocalTime;

public class DayJsonTable extends MyStatusTable {

    // Constructor
    public DayJsonTable(BASE_CLIENT_OBJECT client) {
        super(client, "status");
    }

    @Override
    public void initColumns() {
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

