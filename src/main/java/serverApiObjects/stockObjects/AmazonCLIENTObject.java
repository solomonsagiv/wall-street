package serverApiObjects.stockObjects;

import com.ib.client.Contract;
import logic.Logic;
import serverObjects.TwsData;
import tables.TableDayFather;
import tables.TableSumFather;
import tables.Tables;
import tables.daily.AmazonTable;
import tables.status.StocksArraysTable;
import tables.status.StocksStatusTable;
import tables.status.TableStatusfather;
import tables.status.TablesArraysFather;
import tables.stocks.sum.AmazonSum;

public class AmazonCLIENTObject extends STOCK_CLIENT_OBJECT {

    static AmazonCLIENTObject client = null;

    // Private constructor
    private AmazonCLIENTObject() {
        super( );
    }

    // Get instance
    public static AmazonCLIENTObject getInstance() {
        if ( client == null ) {
            client = new AmazonCLIENTObject( );
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
        indexContract.symbol( "AMZN" );
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
        indexOptionContract.symbol( "AMZN" );
        indexOptionContract.tradingClass( "AMZN" );
        indexOptionContract.includeExpired( true );
        twsData.setOptionMonthContract( indexOptionContract );

        setTwsData( twsData );
    }

    @Override
    public void initTables() {
        setTables( new Tables( ) {

            @Override
            public TableSumFather getTableSum() {
                return new AmazonSum( );
            }

            @Override
            public TableStatusfather getTableStatus() {
                return new StocksStatusTable( );
            }

            @Override
            public TableDayFather getTableDay() {
                return new AmazonTable( );
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
        setName( "amazon" );
    }

    @Override
    public void initStrikeMargin() {
//		setStrikeMargin ( 20 );
    }

    @Override
    public void initIds() {
        getIds( ).put( "index", 799 );
        getIds( ).put( "call_start", 800 );
        getIds( ).put( "call_end", 819 );
        getIds( ).put( "put_start", 820 );
        getIds( ).put( "put_end", 839 );
    }

    @Override
    public void initLogic() {
        setLogic( new Logic( getPanelLine( ), this ) );
    }

    @Override
    public void initDbId() {
        setDbId( 1 );
    }

    @Override
    public void initStrikeMarginForContract() {
//		setStrikeMarginForContract ( 20 );
    }

}
