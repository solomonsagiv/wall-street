package dataBase.mySql.myBaseTables;

import dataBase.mySql.mySqlComps.MyColumnSql;
import dataBase.mySql.mySqlComps.MySqlColumnEnum;
import dataBase.mySql.mySqlComps.MySqlTable;
import serverObjects.BASE_CLIENT_OBJECT;

import java.time.LocalDate;
import java.time.LocalTime;

public class MyIndexStockData extends MySqlTable {

    // Variables

    // Constructor
    public MyIndexStockData( BASE_CLIENT_OBJECT client ) {
        super( client );
    }

    @Override
    public void initColumns() {
        addColumn( new MyColumnSql( this, "date", MySqlColumnEnum.DATE ) {
            @Override
            public Object getObject() {
                return LocalDate.now();
            }
        } );
        addColumn(new MyColumnSql<String>(this, "time", MySqlColumnEnum.TIME) {
            @Override
            public String getObject() {
                return LocalTime.now().toString();
            }
        });
        addColumn(new MyColumnSql<String>(this, "time", MySqlColumnEnum.TIME) {
            @Override
            public String getObject() {
                return LocalTime.now().toString();
            }
        });
    }
}
