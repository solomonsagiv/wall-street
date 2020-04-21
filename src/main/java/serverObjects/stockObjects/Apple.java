package serverObjects.stockObjects;

import DDE.DDECells;
import api.tws.requesters.AppleRequester;
import serverObjects.ApiEnum;

import java.time.LocalTime;

public class Apple extends STOCK_OBJECT {

    static Apple client = null;

    // Constrtor
    public Apple() {
        setName( "apple" );
        setStrikeMargin( 5 );

        setDbId( 4 );
        initDDECells();
        setIndexStartTime(LocalTime.of(16, 30, 0));
        setIndexEndTime(LocalTime.of(23, 0, 0));
        setFutureEndTime(LocalTime.of(23, 15, 0));
        setiTwsRequester(new AppleRequester());
    }

    // Get instance
    public static Apple getInstance() {
        if ( client == null ) {
            client = new Apple( );
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
        setBaseId(30000);
    }
}
