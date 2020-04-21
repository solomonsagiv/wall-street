package serverObjects.stockObjects;

import DDE.DDECells;
import api.tws.requesters.UltaRequester;
import dataBase.mySql.mySqlComps.TablesEnum;
import serverObjects.ApiEnum;

public class Ulta extends STOCK_OBJECT {

    public static void main( String[] args ) {
        Ulta ulta = Ulta.getInstance();
        ulta.getTablesHandler().getTable( TablesEnum.DAY ).insert();
    }

    static Ulta client = null;

    // Constrtor
    public Ulta() {
        setName( "ulta" );
        setStrikeMargin( 2.5 );
        setDbId( 6 );
        initDDECells();
        setiTwsRequester(new UltaRequester());
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
    public void initBaseId() {
        setBaseId(60000);
    }
}
