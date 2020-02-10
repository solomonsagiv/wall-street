package serverApiObjects.stockObjects;

import com.ib.client.Contract;
import logic.Logic;
import serverObjects.TwsData;
import tables.TableDayFather;
import tables.TableSumFather;
import tables.Tables;
import tables.daily.AppleTable;
import tables.status.StocksArraysTable;
import tables.status.StocksStatusTable;
import tables.status.TableStatusfather;
import tables.status.TablesArraysFather;
import tables.stocks.sum.AppleSum;

public class AppleCLIENTObject extends STOCK_CLIENT_OBJECT {

    static AppleCLIENTObject client = null;

    // Private constructor
    private AppleCLIENTObject() {
        super( );
    }

    // Get instance
    public static AppleCLIENTObject getInstance() {
        if ( client == null ) {
            client = new AppleCLIENTObject( );
        }
        return client;
    }

    @Override
    public double getTheoAvgMargin() {
        return 0;
    }

    @Override
    public void initTwsData() {

        TwsData twsData = new TwsData( );

        Contract indexContract = new Contract( );
        indexContract.symbol( "AAPL" );
        indexContract.secType( "STK" );
        indexContract.currency( "USD" );
        indexContract.exchange( "SMART" );
        indexContract.multiplier( null );
        indexContract.lastTradeDateOrContractMonth( null );
        twsData.setIndexContract( indexContract );

        Contract indexOptionContract = new Contract( );
        indexOptionContract.secType( "OPT" );
        indexOptionContract.currency( "USD" );
        indexOptionContract.exchange( "SMART" );
        indexOptionContract.multiplier( "100" );
        indexOptionContract.tradingClass( "AAPL" );
        indexOptionContract.symbol( "AAPL" );
        indexOptionContract.includeExpired( true );
        twsData.setOptionMonthContract( indexOptionContract );

        setTwsData( twsData );

    }


    @Override
    public void initTables() {
        setTables( new Tables( ) {

            @Override
            public TableSumFather getTableSum() {
                return new AppleSum( );
            }

            @Override
            public TableStatusfather getTableStatus() {
                return new StocksStatusTable( );
            }

            @Override
            public TableDayFather getTableDay() {
                return new AppleTable( );
            }

            @Override
            public TablesArraysFather getTableArrays() {
                return new StocksArraysTable( );
            }
        } );
    }

    @Override
    public void initOptions() {
        getOptionsHandler( ).setMainOptions( getOptionsHandler( ).getOptionsDay( ) );
    }


    @Override
    public void initTablesHandlers() {

    }


    @Override
    public void initName() {
        setName( "apple" );
    }

    @Override
    public void initStrikeMargin() {
//		setStrikeMargin ( 2.5 );
    }

    @Override
    public void initIds() {
        getIds( ).put( "index", 699 );
        getIds( ).put( "call_start", 700 );
        getIds( ).put( "call_end", 717 );
        getIds( ).put( "put_start", 718 );
        getIds( ).put( "put_end", 735 );
    }

    @Override
    public void initLogic() {
        setLogic( new Logic( getPanelLine( ), this ) );
    }

    @Override
    public void initDbId() {
        setDbId( 3 );
    }

    @Override
    public void initStrikeMarginForContract() {
//		setStrikeMarginForContract ( 2.5 );
    }

}
