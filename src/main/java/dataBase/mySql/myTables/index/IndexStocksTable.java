package dataBase.mySql.myTables.index;

import dataBase.mySql.mySqlComps.MyLoadAbleColumn;
import dataBase.mySql.mySqlComps.MySqlColumnEnum;
import dataBase.mySql.mySqlComps.MySqlTable;
import serverObjects.indexObjects.INDEX_CLIENT_OBJECT;

public class IndexStocksTable extends MySqlTable {

    public IndexStocksTable(INDEX_CLIENT_OBJECT client) {
        super(client, "indexStocks");
    }

    @Override
    public void initColumns() {

        addColumn( new MyLoadAbleColumn<String>( this, MySqlColumnEnum.data ) {

            @Override
            public Object getObject() {
                return null;
            }

            @Override
            public void setLoadedObject( String object ) {
                System.out.println("Object " + object);
                System.out.println(object);
            }

            @Override
            public String getResetObject() {
                return null;
            }
        } );
    }



}
