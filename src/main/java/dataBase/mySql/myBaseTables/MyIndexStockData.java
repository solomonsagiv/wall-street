package dataBase.mySql.myBaseTables;

import basketFinder.handlers.StocksHandler;
import dataBase.mySql.mySqlComps.MyColumnSql;
import dataBase.mySql.mySqlComps.MyLoadAbleColumn;
import dataBase.mySql.mySqlComps.MySqlColumnEnum;
import dataBase.mySql.mySqlComps.MySqlTable;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Spx;

import java.time.LocalDate;
import java.time.LocalTime;

public class MyIndexStockData extends MySqlTable {

    // Variables
    Spx spx;
    StocksHandler handler;

    // Constructor
    public MyIndexStockData(BASE_CLIENT_OBJECT client) {
        super(client);
        handler = spx.getStocksHandler();
    }
    
    @Override
    public void initColumns() {
//        addColumn(new MyColumnSql<Object>(this, MySqlColumnEnum.date) {
//            @Override
//            public Object getObject() {
//                return LocalDate.now().toString();
//            }
//        });
//        addColumn(new MyColumnSql<String>(this, MySqlColumnEnum.time) {
//            @Override
//            public String getObject() {
//                return LocalTime.now().toString();
//            }
//        });
//        addColumn( new MyLoadAbleColumn<String>( this, MySqlColumnEnum.data ) {
//            @Override
//            public Object getObject() {
//                return ;
//            }
//            @Override
//            public void setLoadedObject( String object ) {
//                spx.getStocksHandler().addStock( object );
//            }
//            @Override
//            public String getResetObject() {
//                return null;
//            }
//        } );
    }
}
