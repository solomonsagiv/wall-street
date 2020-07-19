package dataBase.mySql.myBaseTables;

import dataBase.mySql.mySqlComps.MySqlTable;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Spx;

public class MyIndexStockData extends MySqlTable {

    // Variables
    Spx spx;

    // Constructor
    public MyIndexStockData(BASE_CLIENT_OBJECT client) {
        super(client);
    }

    @Override
    public void initColumns() {
//        addColumn( new MyColumnSql( this, "date", MySqlColumnEnum.DATE ) {
//            @Override
//            public Object getObject() {
//                return LocalDate.now();
//            }
//        } );
//        addColumn(new MyColumnSql<String>(this, "time", MySqlColumnEnum.TIME) {
//            @Override
//            public String getObject() {
//                return LocalTime.now().toString();
//            }
//        });
//        addColumn(new MyColumnSql<String>(this, "time", MySqlColumnEnum.TIME) {
//            @Override
//            public String getObject() {
//                return LocalTime.now().toString();
//            }
//        });
//        addColumn( new MyLoadAbleColumn<String>( this, client.getName(), MySqlColumnEnum.SPX_STOCKS ) {
//            @Override
//            public Object getObject() {
//                return null;
//            }
//            @Override
//            public void setLoadedObject( String object ) {.getStocksHandler().addStock( object );
//            }
//            @Override
//            public String getResetObject() {
//                return null;
//            }
//        } );
    }
}
