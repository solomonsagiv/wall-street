package dataBase.mySql.myTables;

import dataBase.mySql.myBaseTables.MySumTable;
import dataBase.mySql.mySqlComps.MyColumnSql;
import dataBase.mySql.mySqlComps.MySqlColumnEnum;
import myJson.MyJson;
import serverObjects.BASE_CLIENT_OBJECT;

import java.time.LocalDate;

public class SumJsonTable extends MySumTable {

    // Constructor
    public SumJsonTable(BASE_CLIENT_OBJECT client) {
        super(client);
    }

    @Override
    public String getName() {
        return client.getName() + "JsonSum";
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
                MyJson json = client.getAsJson();
//                json.put( JsonStrings.tomorrowFut,  )
                return client.getAsJson().toString();
            }
        });
    }
}