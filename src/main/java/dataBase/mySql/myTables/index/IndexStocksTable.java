package dataBase.mySql.myTables.index;

import dataBase.mySql.mySqlComps.MyLoadAbleColumn;
import dataBase.mySql.mySqlComps.MySqlColumnEnum;
import dataBase.mySql.mySqlComps.MySqlTable;
import serverObjects.indexObjects.Spx;

public class IndexStocksTable extends MySqlTable {

    Spx spx;

    public IndexStocksTable( Spx client ) {
        super( client, "indexStocks" );
        spx = client;
    }

    @Override
    public void initColumns() {
        addColumn( new MyLoadAbleColumn<String>( this, client.getName(), MySqlColumnEnum.SPX_STOCKS ) {
            @Override
            public Object getObject() {
                return null;
            }
            @Override
            public void setLoadedObject( String object ) {
                spx.getStocksHandler().addStock( object );
            }
            @Override
            public String getResetObject() {
                return null;
            }
        } );
    }
}
