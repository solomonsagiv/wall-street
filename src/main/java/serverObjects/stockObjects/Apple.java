package serverObjects.stockObjects;

import DDE.DDECells;
import api.tws.requesters.AppleRequester;
import serverObjects.ApiEnum;

import java.time.LocalTime;

public class Apple extends STOCK_OBJECT {

    static Apple client = null;

    // Constructor
    public Apple() {
        super( );
        setName( "apple" );
        setRacesMargin( 0.1 );
        setStrikeMargin( 5 );

        setDbId( 4 );
        initDDECells();
        setiTwsRequester( new AppleRequester() );
        setIndexStartTime(LocalTime.of(16, 30, 0));
        setIndexEndTime(LocalTime.of(23, 0, 0));
        setFutureEndTime(LocalTime.of(23, 15, 0));
        setiTwsRequester(new AppleRequester());

//        rollHandler = new RollHandler( this );
//        Roll quarter_quarterFar = new Roll( getOptionsHandler().getOptions( OptionsEnum.QUARTER ), getOptionsHandler().getOptions( OptionsEnum.QUARTER_FAR ) );
//        rollHandler.addRoll( RollEnum.QUARTER_QUARTER_FAR, quarter_quarterFar );

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
    public void requestApi() {

    }

    @Override
    public void initBaseId() {
        setBaseId(30000);
    }
}
