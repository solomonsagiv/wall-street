package serverObjects.indexObjects;

import api.Manifest;
import com.ib.client.Contract;
import logic.Logic;
import serverObjects.TwsData;
import tables.*;
import tables.daily.NdxTable;
import tables.status.IndexArraysTable;
import tables.status.IndexStatusTable;
import tables.status.TableStatusfather;
import tables.status.TablesArraysFather;
import tables.summery.Ndx_daily;

import java.time.LocalTime;

public class NdxCLIENTObject extends INDEX_CLIENT_OBJECT {

	static NdxCLIENTObject client = null;

	// Private constructor
	public NdxCLIENTObject () {
		super();
		setSpecificData();
	}

	// Get instance
	public static NdxCLIENTObject getInstance() {
		if ( client == null ) {
			client = new NdxCLIENTObject ();
		}
		return client;
	}

	// Specification
	private void setSpecificData() {
		// Equal move
		setEqualMovePlag( .6 );
		setIndexBidAskMargin( 1.25 );
	}

	@Override
	public void initTwsData() {

		TwsData twsData = getTwsData ();

		Contract indexContract = new Contract();
		indexContract.symbol( "NDX" );
		indexContract.secType( "IND" );
		indexContract.currency( "USD" );
		indexContract.exchange( "NASDAQ" );
		indexContract.multiplier( "20" );
		twsData.setIndexContract( indexContract );

		Contract futureContract = new Contract();
		futureContract.symbol( "NQ" );
		futureContract.secType( "FUT" );
		futureContract.currency( "USD" );
		futureContract.exchange( "GLOBEX" );
		futureContract.lastTradeDateOrContractMonth( Manifest.EXPIRY );
		futureContract.multiplier( "20" );
		twsData.setFutureContract( futureContract );

		Contract optionsDayContract = new Contract();
		optionsDayContract.secType( "OPT" );
		optionsDayContract.currency( "USD" );
		optionsDayContract.exchange( "SMART" );
		optionsDayContract.tradingClass( "NDXP" );
		optionsDayContract.symbol( "NDXP" );
		optionsDayContract.includeExpired( true );
		twsData.setOptionsDayContract ( optionsDayContract );

		Contract optoinsMonthContract = new Contract();
		optoinsMonthContract.secType( "OPT" );
		optoinsMonthContract.currency( "USD" );
		optoinsMonthContract.exchange( "SMART" );
		optoinsMonthContract.tradingClass( "NDX" );
		optoinsMonthContract.symbol( "NDX" );
		optoinsMonthContract.includeExpired( true );
		twsData.setOptionMonthContract ( optoinsMonthContract );

		Contract optoinsQuarterContract = new Contract();
		optoinsQuarterContract.secType( "OPT" );
		optoinsQuarterContract.currency( "USD" );
		optoinsQuarterContract.exchange( "SMART" );
		optoinsQuarterContract.symbol( "NDX" );
		optoinsQuarterContract.multiplier ( "100" );
		optoinsQuarterContract.tradingClass ( "NDX" );
		optoinsQuarterContract.includeExpired( true );
		twsData.setOptionsQuarterContract ( optoinsQuarterContract );

		Contract optoinsQuarterFarContract = new Contract();
		optoinsQuarterFarContract.secType( "OPT" );
		optoinsQuarterFarContract.currency( "USD" );
		optoinsQuarterFarContract.exchange( "SMART" );
		optoinsQuarterFarContract.tradingClass( "NDX" );
		optoinsQuarterFarContract.symbol( "NDX" );
		optoinsQuarterFarContract.includeExpired( true );
		twsData.setOptionsQuarterFarContract ( optoinsQuarterFarContract );

	}

	@Override
	public void initStartOfIndexTrading() {
		setStartOfIndexTrading( LocalTime.of( 16 , 30 , 0 ) );
	}

	@Override
	public void initEndOfIndexTrading() {
		setEndOfIndexTrading( LocalTime.of( 23 , 0 , 0 ) );
	}

	@Override
	public void initEndOfFutureTrading() {
		setEndFutureTrading( LocalTime.of( 23 , 15 , 0 ) );
	}

	@Override
	public void initIds() {

		setBaseId ( 20000 );
		getTwsData ().setIndexId ( getBaseId () + 1 );
		getTwsData ().setFutureId ( getBaseId () + 2 );

	}

	@Override
	public void initLogic() {
		setLogic( new Logic( getPanel() , this ) );
	}

	@Override
	public void initDbId() {
		setDbId( 3 );
	}

	@Override
	public void initTables() {


		setTables( new Tables() {

			@Override
			public TableSumFather getTableSum() {
				return new Ndx_daily();
			}

			@Override
			public TableStatusfather getTableStatus() {
				return new IndexStatusTable();
			}

			@Override
			public TableDayFather getTableDay() {
				return new NdxTable();
			}

			@Override
			public TablesArraysFather getTableArrays() {
				return new IndexArraysTable();
			}
		} );
	}

	@Override
	public void initName() {
		setName( "ndx" );
	}

	@Override
	public void initRacesMargin() {
		setRacesMargin( .7 );
	}

	@Override
	public void initStrikeMargin() {
		getOptionsHandler().getOptionsMonth().setStrikeMargin( 40 );
		getOptionsHandler().getOptionsDay().setStrikeMargin( 40 );
		getOptionsHandler().getOptionsQuarter ().setStrikeMargin( 40 );
	}
	@Override
	public void initStrikeMarginForContract() {
		getOptionsHandler().getOptionsMonth().setStrikeMarginForContract( 40 );
		getOptionsHandler().getOptionsDay().setStrikeMarginForContract( 40 );
		getOptionsHandler().getOptionsQuarter ().setStrikeMarginForContract( 40 );
	}

	@Override
	public void initOptions() {
		getOptionsHandler().setMainOptions ( getOptionsHandler().getOptionsMonth () );
	}

	@Override
	public void initTablesHandlers() {
		setTablesHandler( new TablesHandler( new NdxTable.Handler( this ) , new IndexTableSum.Handler( this ) ,
				new IndexStatusTable.Handler( this ) , new IndexArraysTable.Handler( this ) ) );
	}

	@Override
	public double getTheoAvgMargin() {
		return 0;
	}

}
