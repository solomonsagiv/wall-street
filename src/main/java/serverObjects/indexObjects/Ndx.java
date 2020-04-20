package serverObjects.indexObjects;

import DDE.DDECells;
import serverObjects.ApiEnum;

import java.time.LocalTime;

public class Ndx extends INDEX_CLIENT_OBJECT {

    static Ndx client = null;

    // Private constructor
    public Ndx() {
        super( );
        setName( "ndx" );
        setIndexBidAskMargin( 1.25 );
        setDbId( 3 );
        setStrikeMargin( 40 );
        setBaseId( 20000 );
        initDDECells();
        setIndexStartTime(LocalTime.of(16, 30, 0));
        setIndexEndTime(LocalTime.of(23, 0, 0));
        setFutureEndTime(LocalTime.of(23, 15, 0));
    }

    // Get instance
    public static Ndx getInstance() {
        if ( client == null ) {
            client = new Ndx( );
        }
        return client;
    }

    @Override
    public void initDDECells() {

        DDECells ddeCells = new DDECells( ) {
            @Override
            public boolean isWorkWithDDE() {
                return true;
            }
        };

        // TODO init cells

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
        setBaseId(20000);
    }

    @Override
    public double getTheoAvgMargin() {
        return 0;
    }

}
