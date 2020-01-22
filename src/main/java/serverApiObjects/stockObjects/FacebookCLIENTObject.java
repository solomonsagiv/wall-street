package serverApiObjects.stockObjects;

import com.ib.client.Contract;
import logic.Logic;
import serverObjects.TwsData;
import tables.TableDayFather;
import tables.TableSumFather;
import tables.Tables;
import tables.daily.FacebookTable;
import tables.status.StocksArraysTable;
import tables.status.StocksStatusTable;
import tables.status.TableStatusfather;
import tables.status.TablesArraysFather;
import tables.stocks.sum.FacebookSum;

public class FacebookCLIENTObject extends STOCK_CLIENT_OBJECT {

	static FacebookCLIENTObject client = null;
	double futureBid = 0;
	double futureAsk = 0;

	// Private constructor
	private FacebookCLIENTObject () {
		super();
	}

	// Get instance
	public static FacebookCLIENTObject getInstance() {
		if ( client == null ) {
			client = new FacebookCLIENTObject ();
			System.out.println( "Created new facebook" );
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

		TwsData twsData = new TwsData();

		Contract indexContract = new Contract();
		indexContract.symbol( "FB" );
		indexContract.secType( "STK" );
		indexContract.currency( "USD" );
		indexContract.exchange( "SMART" );
		indexContract.multiplier( null );
		indexContract.lastTradeDateOrContractMonth( null );
		twsData.setIndexContract( indexContract );

		Contract indexOptionContract = new Contract();
		indexOptionContract.secType( "OPT" );
		indexOptionContract.currency( "USD" );
		indexOptionContract.exchange( "SMART" );
		indexOptionContract.multiplier( "100" );
		indexOptionContract.symbol( "FB" );
		indexOptionContract.tradingClass( "FB" );
		indexOptionContract.includeExpired( true );
		twsData.setOptionMonthContract ( indexOptionContract );


		setTwsData( twsData );

	}


	@Override
	public void initTables() {
		setTables( new Tables() {

			@Override
			public TableSumFather getTableSum() {
				return new FacebookSum();
			}

			@Override
			public TableStatusfather getTableStatus() {
				return new StocksStatusTable();
			}

			@Override
			public TableDayFather getTableDay() {
				return new FacebookTable();
			}

			@Override
			public TablesArraysFather getTableArrays() {
				return new StocksArraysTable();
			}
		} );
	}

	@Override
	public void initOptions() {
		getOptionsHandler().setMainOptions ( getOptionsHandler().getOptionsDay () );
	}

	@Override
	public void initMyLists() {

	}

	@Override
	public void initTablesHandlers() {

	}


	@Override
	public void initName() {
		setName( "facebook" );
	}

	@Override
	public void initStrikeMargin() {
//		setStrikeMargin ( 2.5 );
	}

	@Override
	public void initIds() {
		getIds().put( "index" , 599 );
		getIds().put( "call_start" , 600 );
		getIds().put( "call_end" , 617 );
		getIds().put( "put_start" , 618 );
		getIds().put( "put_end" , 635 );
	}

	@Override
	public void initLogic() {
		setLogic( new Logic( getPanelLine() , this ) );
	}

	@Override
	public void initDbId() {
		setDbId( 2 );
	}

	@Override
	public void initStrikeMarginForContract() {
//		setStrikeMarginForContract ( 2.5 );
	}


}
