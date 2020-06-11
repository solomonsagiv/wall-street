package dataBase.mySql.myTables.index;

import dataBase.mySql.mySqlComps.MyLoadAbleColumn;
import dataBase.mySql.mySqlComps.MySqlColumnEnum;
import dataBase.mySql.mySqlComps.MySqlTable;
import serverObjects.indexObjects.Dax;
import serverObjects.indexObjects.INDEX_CLIENT_OBJECT;

public class IndexStocksTable extends MySqlTable {


    public IndexStocksTable( INDEX_CLIENT_OBJECT client ) {
        super( client, "indexStocks" );
    }

    @Override
    public void initColumns() {
//        addColumn( new MyLoadAbleColumn<String>( this, client.getName(), MySqlColumnEnum.DAX_STOCKS ) {
//            @Override
//            public Object getObject() {
//                return null;
//            }
//            @Override
//            public void setLoadedObject( String object ) {
//                if (object != null) {
//                    if (client instanceof Dax) {
//                        System.out.println(object);
//                    }
//                    ((INDEX_CLIENT_OBJECT) client).getStocksHandler().addStock( object );
//                }
//
//            }
//            @Override
//            public String getResetObject() {
//                return null;
//            }
//        } );
    }
}
