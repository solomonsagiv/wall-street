package serverObjects.stockObjects;

import DDE.DDECells;
import api.tws.requesters.AmazonRequester;
import serverObjects.ApiEnum;

public class Ulta extends STOCK_OBJECT {

    static Ulta client = null;

    // Constrtor
    public Ulta() {
        setName( "ulta" );
        setRacesMargin( 0.1 );
        setStrikeMargin( 10 );
        setDbId( 6 );
        initDDECells();
        setiTwsRequester(new AmazonRequester());
    }

    // Get instance
    public static Ulta getInstance() {
        if ( client == null ) {
            client = new Ulta( );
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
        setBaseId(60000);
    }
}
