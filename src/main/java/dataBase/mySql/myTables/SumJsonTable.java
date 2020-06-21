package dataBase.mySql.myTables;

import api.Manifest;
import dataBase.mySql.myBaseTables.MySumTable;
import dataBase.mySql.mySqlComps.MyColumnSql;
import dataBase.mySql.mySqlComps.MySqlColumnEnum;
import roll.RollEnum;
import serverObjects.BASE_CLIENT_OBJECT;

import java.time.LocalDate;

public class SumJsonTable extends MySumTable {

    // Constructor
    public SumJsonTable( BASE_CLIENT_OBJECT client, String tableName) {
        super(client, tableName);
    }

    @Override
    public void initColumns() {
        addColumn(new MyColumnSql<>(this, MySqlColumnEnum.date) {
            @Override
            public String getObject() {
                return LocalDate.now().toString();
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
