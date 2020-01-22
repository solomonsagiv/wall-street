package serverApiObjects.stockObjects;

import com.ib.client.Contract;
import logic.Logic;
import serverObjects.TwsData;
import tables.TableDayFather;
import tables.TableSumFather;
import tables.Tables;
import tables.daily.NetflixTable;
import tables.status.StocksArraysTable;
import tables.status.StocksStatusTable;
import tables.status.TableStatusfather;
import tables.status.TablesArraysFather;
import tables.stocks.sum.NetflixSum;

public class NetflixCLIENTObject extends STOCK_CLIENT_OBJECT {

    static NetflixCLIENTObject client = null;
    double futureBid = 0;
    double futureAsk = 0;

    // Private constructor
    private NetflixCLIENTObject() {
        super( );
    }

    // Get instance
    public static NetflixCLIENTObject getInstance() {
        if ( client == null ) {
            client = new NetflixCLIENTObject( );
        }
        return client;
    }

    public double getFutureBid() {
        return futureBid;
    }

    public void setFutureBid( double futureBid ) {
        this.futureBid = futureBid;
    }

    public double getFutureAsk() {
        return futureAsk;
    }

    public void setFutureAsk( double futureAsk ) {
        this.futureAsk = futureAsk;
    }

    @Override
    public double getTheoAvgMargin() {
        return 0;
    }

    @Override
    public void initTwsData() {

        TwsData twsData = new TwsData( );

        Contract indexContract = new Contract( );
        indexContract.symbol( "NFLX" );
        indexContract.secType( "STK" );
        indexContract.currency( "USD" );
        indexContract.exchange( "SMART" );
        indexContract.primaryExch( "NASDAQ" );
        indexContract.multiplier( null );
        indexContract.lastTradeDateOrContractMonth( null );
        twsData.setIndexContract( indexContract );

        Contract indexOptionContract = new Contract( );
        indexOptionContract.secType( "OPT" );
        indexOptionContract.currency( "USD" );
        indexOptionContract.exchange( "SMART" );
        indexOptionContract.multiplier( "100" );
        indexOptionContract.symbol( "NFLX" );
        indexOptionContract.tradingClass( "NFLX" );
        indexOptionContract.includeExpired( true );
        twsData.setOptionMonthContract( indexOptionContract );

        setTwsData( twsData );

    }


    @Override
    public void initTables() {
        setTables( new Tables( ) {

            @Override
            public TableSumFather getTableSum() {
                return new NetflixSum( );
            }

            @Override
            public TableStatusfather getTableStatus() {
                return new StocksStatusTable( );
            }

            @Override
            public TableDayFather getTableDay() {
                return new NetflixTable( );
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
    public void initMyLists() {

    }

    @Override
    public void initTablesHandlers() {

    }


    @Override
    public void initName() {
        setName( "netflix" );
    }

    @Override
    public void initStrikeMargin() {
//		setStrikeMargin ( 5 );
    }

    @Override
    public void initIds() {
        getIds( ).put( "index", 1799 );
        getIds( ).put( "call_start", 1800 );
        getIds( ).put( "call_end", 1813 );
        getIds( ).put( "put_start", 1814 );
        getIds( ).put( "put_end", 1827 );
    }

    @Override
    public void initLogic() {
        setLogic( new Logic( getPanelLine( ), this ) );
    }

    @Override
    public void initDbId() {
        setDbId( 4 );
    }

    @Override
    public void initStrikeMarginForContract() {
//		setStrikeMarginForContract ( 5 );
    }

}
