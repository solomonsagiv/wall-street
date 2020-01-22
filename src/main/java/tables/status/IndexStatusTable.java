package tables.status;

import arik.Arik;
import dataBase.HB;
import options.Options;
import org.json.JSONObject;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.SpxCLIENTObject;
import tables.ITablesHandler;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalTime;

@Entity
@Table( name = "status" )
public class IndexStatusTable extends TableStatusfather {


	// Handler
	public static class Handler implements ITablesHandler {

		public static Object object;

		BASE_CLIENT_OBJECT client;

		public Handler( BASE_CLIENT_OBJECT client ) {
			this.client = client;
			object = getTableObject ();
		}

		@Override
		public void insertLine() {

		}

		@Override
		public Object getTableObject() {

			IndexStatusTable table = new IndexStatusTable();
			table.setId( client.getDbId() );
			table.setName( client.getName() );
			table.setTime( LocalTime.now().toString() );
			table.setInd( client.getIndex() );
			table.setIndUp( client.getIndexUp() );
			table.setIndDown( client.getIndexDown() );
			table.setConUp( client.getConUp() );
			table.setConDown( client.getConDown() );
			table.setBase( client.getBase() );
			table.setOpen( client.getOpen() );
			table.setHigh( client.getHigh() );
			table.setLow( client.getLow() );
			table.setFutBdCounter( client.getFutureBidAskCounter() );
			table.setOptions( client.getOptionsHandler().getAllOptionsAsJson().toString() );

			return table;
		}

		@Override
		public void loadData() {

			try {
				IndexStatusTable status = ( IndexStatusTable ) HB.get_line_by_id( IndexStatusTable.class ,
						client.getDbId() , client.getSessionfactory() );
				client.setOpen ( status.getOpen () );
				client.setConUp( status.getConUp() );
				client.setConDown( status.getConDown() );
				client.setIndexUp( status.getIndUp() );
				client.setIndexDown( status.getIndDown() );
				client.setOptimiMoveFromOutSide( status.getOptimiMove() );
				client.setPesimiMoveFromOutSide( status.getPesimiMove() );

				JSONObject optionsData = new JSONObject( status.getOptions() );

				for ( Options options : client.getOptionsHandler().getOptionsList() ) {
					options.setDataFromJson( optionsData.getJSONObject( options.getName() ) );
				}

				client.setLoadStatusFromHB ( true );

			} catch ( Exception e ) {
				Arik.getInstance().sendMessage( Arik.sagivID , client.getName() + " MYSQL exception \n" + e.getCause() ,
						null );
			}

		}

		@Override
		public void resetData() {

			try {
				IndexStatusTable table = new IndexStatusTable ();
				table.setId ( client.getDbId () );
				table.setName ( client.getName () );

				table.setOptions( client.getOptionsHandler().getAllOptionsEmptyJson().toString() );
				HB.update( client.getSessionfactory() , table );

			} catch ( Exception e ) {
				Arik.getInstance().sendMessage( Arik.sagivID , client.getName() + " MYSQL exception \n" + e.getCause() ,
						null );
			}

		}

		@Override
		public void updateData() {

			try {


				IndexStatusTable table = ( IndexStatusTable ) getTableObject();

				long startTime = System.currentTimeMillis();


				HB.update( client.getSessionfactory() , table );

				long endTime = System.currentTimeMillis();

				double duration = ( endTime - startTime );  //divide by 1000000 to get milliseconds
				System.out.println( duration / 1000 );



			} catch ( Exception e ) {
				Arik.getInstance().sendMessage( Arik.sagivID , client.getName() + " MYSQL exception \n" + e.getCause() ,
						null );
			}

		}

		@Override
		public void updateObject () {

			new Thread ( () ->{

				object = getTableObject ();

			} ).start ();

		}
	}


	public IndexStatusTable() {
		super();
	}

}


