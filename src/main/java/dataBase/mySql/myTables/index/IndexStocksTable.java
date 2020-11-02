package dataBase.mySql.myTables.index;

import dataBase.mySql.MySql;
import dataBase.mySql.mySqlComps.MyLoadAbleColumn;
import dataBase.mySql.mySqlComps.MySqlColumnEnum;
import dataBase.mySql.mySqlComps.MySqlTable;
import myJson.MyJson;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.INDEX_CLIENT_OBJECT;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IndexStocksTable extends MySqlTable {



    public IndexStocksTable(INDEX_CLIENT_OBJECT client) {
        super(client, "indexStocks");
    }

    @Override
    public INDEX_CLIENT_OBJECT getClient() {
        return ( INDEX_CLIENT_OBJECT ) super.getClient( );
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
                System.out.println(object);
            }

            @Override
            public String getResetObject() {
                return null;
            }
        } );
    }

    @Override
    public void load() {
        String query = String.format( "SELECT * FROM jsonTables.indexStocks WHERE name ='%s';", client.getName() );
        ResultSet rs = MySql.select(query);

        while ( true ) {
            try {
                if ( !rs.next() ) break;

                MyJson json = new MyJson( rs.getString( "data" ));
                getClient().getStocksHandler().loadStocksFromJson( json );

            } catch ( SQLException throwables ) {
                throwables.printStackTrace( );
            }

        }

    }
}
