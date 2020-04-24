package serverObjects.stockObjects;

import DDE.DDECells;
import api.tws.requesters.NetflixRequester;
import dataBase.mySql.mySqlComps.TablesEnum;
import serverObjects.ApiEnum;

import java.time.LocalTime;

public class Netflix extends STOCK_OBJECT {


    public static void main(String[] args) {
        Apple netflix = Apple.getInstance();
        netflix.getTablesHandler().getTable(TablesEnum.TWS_CONTRACTS).load();
        netflix.getTablesHandler().getTable(TablesEnum.ARRAYS).load();
        netflix.getTablesHandler().getTable(TablesEnum.STATUS).load();

        netflix.getTablesHandler().getTable(TablesEnum.SUM).insert();

    }

    static Netflix client = null;

    // Constrtor
    public Netflix() {
        setName( "netflix" );
        setStrikeMargin( 5 );

        setDbId( 7 );
        initDDECells();
        setIndexStartTime(LocalTime.of(16, 30, 0));
        setIndexEndTime(LocalTime.of(23, 0, 0));
        setFutureEndTime(LocalTime.of(23, 15, 0));
        setiTwsRequester(new NetflixRequester());
    }

    // Get instance
    public static Netflix getInstance() {
        if ( client == null ) {
            client = new Netflix( );
        }
        return client;
    }

    @Override
    public double getTheoAvgMargin() {
        return 0.05;
    }

    @Override
    public void initDDECells() {
        DDECells ddeCells = new DDECells( ) {
            @Override
            public boolean isWorkWithDDE() {
                return false;
            }
        };
        setDdeCells( ddeCells );
    }

    @Override
    public ApiEnum getApi() {
        return ApiEnum.TWS;
    }

    @Override
    public void initBaseId() {
        setBaseId(70000);
    }
}
