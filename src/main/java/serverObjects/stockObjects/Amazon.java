package serverObjects.stockObjects;

import DDE.DDECells;
import api.tws.requesters.AmazonRequester;
import api.tws.requesters.AppleRequester;
import serverObjects.ApiEnum;

import java.time.LocalTime;

public class Amazon extends STOCK_OBJECT {

    static Amazon client = null;

    // Constrtor
    public Amazon() {
        setName( "amazon" );
        setRacesMargin( 0.1 );
        setStrikeMargin( 10 );
        setDbId( 5 );
        initDDECells();
        setiTwsRequester(new AmazonRequester());
    }

    // Get instance
    public static Amazon getInstance() {
        if ( client == null ) {
            client = new Amazon( );
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
    public void requestApi() {

    }

    @Override
    public void initBaseId() {
        setBaseId(50000);
    }
}
