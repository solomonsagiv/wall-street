package serverObjects.indexObjects;

import api.Manifest;
import com.ib.client.Contract;
import logic.Logic;
import serverObjects.TwsData;
import tables.TableDayFather;
import tables.TableSumFather;
import tables.Tables;
import tables.daily.DaxTable;
import tables.status.IndexArraysTable;
import tables.status.IndexStatusTable;
import tables.status.TableStatusfather;
import tables.status.TablesArraysFather;
import tables.summery.Dax_daily;

import java.time.LocalTime;

public class DaxCLIENTObject extends INDEX_CLIENT_OBJECT {

    static DaxCLIENTObject client = null;

    // Private constructor
    private DaxCLIENTObject() {
        super( );
        setSpecificData( );
    }

    // Get instance
    public static DaxCLIENTObject getInstance() {
        if ( client == null ) {
            client = new DaxCLIENTObject( );
        }
        return client;
    }

    // Specification
    private void setSpecificData() {
        // Equal move
        setEqualMovePlag( 1 );
    }

    @Override
    public void initTwsData() {

        TwsData twsData = new TwsData( );

        twsData.setQuantity( 1 );

        Contract indexContract = new Contract( );
        indexContract.symbol( "DAX" );
        indexContract.secType( "IND" );
        indexContract.currency( "EUR" );
        indexContract.exchange( "DTB" );
        indexContract.multiplier( "25" );
        twsData.setIndexContract( indexContract );

        Contract futureContract = new Contract( );
        futureContract.symbol( "DAX" );
        futureContract.secType( "FUT" );
        futureContract.currency( "EUR" );
        futureContract.exchange( "DTB" );
        futureContract.multiplier( "5" );
        futureContract.lastTradeDateOrContractMonth( Manifest.EXPIRY );
        twsData.setFutureContract( futureContract );

        Contract indexOptionContract = new Contract( );
        indexOptionContract.secType( "OPT" );
        indexOptionContract.symbol( "ODAX" );
        indexOptionContract.tradingClass( "ODAX" );
        indexOptionContract.currency( "EUR" );
        indexOptionContract.exchange( "DTB" );
        indexOptionContract.multiplier( "5" );
        indexOptionContract.includeExpired( true );
        twsData.setOptionMonthContract( indexOptionContract );

        setTwsData( twsData );

    }


    @Override
    public void initTables() {
        setTables( new Tables( ) {

            @Override
            public TableSumFather getTableSum() {
                return new Dax_daily( );
            }

            @Override
            public TableStatusfather getTableStatus() {
                return new IndexStatusTable( );
            }

            @Override
            public TableDayFather getTableDay() {
                return new DaxTable( );
            }

            @Override
            public TablesArraysFather getTableArrays() {
                return new IndexArraysTable( );
            }
        } );
    }

    @Override
    public void initOptions() {
        getOptionsHandler( ).setMainOptions( getOptionsHandler( ).getOptionsMonth( ) );
    }

    @Override
    public void initMyLists() {

    }

    @Override
    public void initTablesHandlers() {

    }

    @Override
    public double getTheoAvgMargin() {
        return 0;
    }

    @Override
    public void initName() {
        setName( "dax" );
    }

    @Override
    public void initRacesMargin() {
        setRacesMargin( 1.5 );
    }

    @Override
    public void initStrikeMargin() {
        getOptionsHandler( ).getOptionsMonth( ).setStrikeMargin( 100 );
        getOptionsHandler( ).getOptionsDay( ).setStrikeMargin( 100 );
    }

    @Override
    public void initStartOfIndexTrading() {
        setStartOfIndexTrading( LocalTime.of( 10, 0, 0 ) );
    }

    @Override
    public void initEndOfIndexTrading() {
        setEndOfIndexTrading( LocalTime.of( 18, 30, 0 ) );
    }

    @Override
    public void initEndOfFutureTrading() {
        initEndOfIndexTrading( );
    }

    @Override
    public void initIds() {
        setBaseId( 5000 );
        getTwsData( ).setIndexId( getBaseId( ) + 1 );
        getTwsData( ).setFutureId( getBaseId( ) + 2 );
    }

    @Override
    public void initLogic() {
        setLogic( new Logic( getPanel( ), this ) );
    }

    @Override
    public void initDbId() {
        setDbId( 1 );
    }

    @Override
    public void initStrikeMarginForContract() {
        getOptionsHandler( ).getOptionsMonth( ).setStrikeMarginForContract( 100 );
        getOptionsHandler( ).getOptionsDay( ).setStrikeMarginForContract( 100 );
    }
}
