package serverObjects.stockObjects;

import DDE.DDECells;
import api.tws.requesters.AmdRequester;
import serverObjects.ApiEnum;

public class Amd extends STOCK_OBJECT {

    static Amd client = null;

    // Constrtor
    public Amd() {
        setName( "amd" );
        setStrikeMargin( 1 );

        setDbId( 8 );
        initDDECells();
        setiTwsRequester(new AmdRequester());
    }

    // Get instance
    public static Amd getInstance() {
        if ( client == null ) {
            client = new Amd( );
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
        setBaseId(80000);
    }
}
