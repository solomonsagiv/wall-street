package serverObjects.indexObjects;

import api.Manifest;
import com.ib.client.Contract;
import logic.Logic;
import serverObjects.TwsData;
import tables.*;
import tables.daily.SpxTable;
import tables.status.IndexArraysTable;
import tables.status.IndexStatusTable;
import tables.status.TableStatusfather;
import tables.status.TablesArraysFather;
import tables.summery.SpxDaily;

import java.time.LocalTime;

public class SpxCLIENTObject extends INDEX_CLIENT_OBJECT {

	static SpxCLIENTObject client = null;

	// Constructor
	public SpxCLIENTObject() {
		super();
		setSpecificData();
	}

	// Get instance
	public static SpxCLIENTObject getInstance() {
		if ( client == null ) {
			client = new SpxCLIENTObject();
		}
		return client;
	}

	// Specification
	private void setSpecificData() {
		// Equal move
		setEqualMovePlag( .25 );
		setIndexBidAskMargin( .5 );
	}

	@Override
	public void initTwsData() {

		TwsData twsData = getTwsData();

		twsData.setQuantity( 3 );

		Contract indexContract = new Contract();
		indexContract.symbol( "SPX" );
		indexContract.secType( "IND" );
		indexContract.currency( "USD" );
		indexContract.exchange( "CBOE" );
		indexContract.multiplier( "50" );
		twsData.setIndexContract( indexContract );

		Contract futureContract = new Contract();
		futureContract.symbol( "ES" );
		futureContract.secType( "FUT" );
		futureContract.currency( "USD" );
		futureContract.lastTradeDateOrContractMonth( Manifest.EXPIRY );
		futureContract.exchange( "GLOBEX" );
		futureContract.multiplier( "50" );
		twsData.setFutureContract( futureContract );

		Contract optionsDayContract = new Contract();
		optionsDayContract.secType( "OPT" );
		optionsDayContract.currency( "USD" );
		optionsDayContract.exchange( "SMART" );
		optionsDayContract.tradingClass( "SPXW" );
		optionsDayContract.multiplier( "100" );
		optionsDayContract.symbol( "SPXW" );
		optionsDayContract.includeExpired( true );
		twsData.setOptionsDayContract( optionsDayContract );

		Contract optionsMonthContract = new Contract();
		optionsMonthContract.secType( "OPT" );
		optionsMonthContract.currency( "USD" );
		optionsMonthContract.exchange( "SMART" );
		optionsMonthContract.tradingClass( "SPX" );
		optionsMonthContract.multiplier( "100" );
		optionsMonthContract.symbol( "SPX" );
		optionsMonthContract.includeExpired( true );
		twsData.setOptionMonthContract( optionsMonthContract );

		Contract optionsQuarterContract = new Contract();
		optionsQuarterContract.secType( "OPT" );
		optionsQuarterContract.currency( "USD" );
		optionsQuarterContract.exchange( "SMART" );
		optionsQuarterContract.tradingClass( "SPX" );
		optionsQuarterContract.multiplier( "100" );
		optionsQuarterContract.symbol( "SPX" );
		optionsQuarterContract.includeExpired( true );
		twsData.setOptionsQuarterContract( optionsQuarterContract );

		Contract optionsQuarterFarContract = new Contract();
		optionsQuarterFarContract.secType( "OPT" );
		optionsQuarterFarContract.currency( "USD" );
		optionsQuarterFarContract.exchange( "SMART" );
		optionsQuarterFarContract.tradingClass( "SPX" );
		optionsQuarterFarContract.multiplier( "100" );
		optionsQuarterFarContract.symbol( "SPX" );
		optionsQuarterFarContract.includeExpired( true );
		twsData.setOptionsQuarterFarContract( optionsQuarterFarContract );

		Contract futureOptionContract = new Contract();
		futureOptionContract.secType( "FOP" );
		futureOptionContract.currency( "USD" );
		futureOptionContract.exchange( "GLOBEX" );
		futureOptionContract.multiplier( "50" );
		futureOptionContract.includeExpired( true );
		twsData.setFutureOptionContract( futureOptionContract );
	}

	@Override
	public void initName() {
		setName( "spx" );
	}

	@Override
	public void initRacesMargin() {
		setRacesMargin( .3 );
	}

	@Override
	public void initStrikeMargin() {
		getOptionsHandler().getOptionsMonth().setStrikeMargin( 5 );
		getOptionsHandler().getOptionsDay().setStrikeMargin( 5 );
		getOptionsHandler().getOptionsQuarter().setStrikeMargin( 5 );
	}

	@Override
	public void initStrikeMarginForContract() {
		getOptionsHandler().getOptionsMonth().setStrikeMarginForContract( 10 );
		getOptionsHandler().getOptionsDay().setStrikeMarginForContract( 10 );
		getOptionsHandler().getOptionsQuarter().setStrikeMarginForContract( 10 );
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

		setBaseId( 10000 );
		getTwsData().setIndexId( getBaseId() + 1 );
		getTwsData().setFutureId( getBaseId() + 2 );

	}

	@Override
	public void initLogic() {
		setLogic( new Logic( getPanel() , this ) );
	}

	@Override
	public void initDbId() {
		setDbId( 2 );
	}

	@Override
	public void initTables() {

		setTables( new Tables() {

			@Override
			public TableSumFather getTableSum() {
				return new SpxDaily();
			}

			@Override
			public TableStatusfather getTableStatus() {
				return new IndexStatusTable();
			}

			@Override
			public TableDayFather getTableDay() {
				return new SpxTable();
			}

			@Override
			public TablesArraysFather getTableArrays() {
				return new IndexArraysTable();
			}
		} );
	}

	@Override
	public void initOptions() {
		getOptionsHandler().setMainOptions( getOptionsHandler().getOptionsMonth() );
	}

	@Override
	public void initTablesHandlers() {
		setTablesHandler( new TablesHandler( new SpxTable.Handler( this ) , new IndexTableSum.Handler( this ) ,
				new IndexStatusTable.Handler( this ) , new IndexArraysTable.Handler( this ) ) );
	}

	@Override
	public double getTheoAvgMargin() {
		return 0.05;
	}


}
