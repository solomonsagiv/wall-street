package tables.status;

import arik.Arik;
import dataBase.HB;
import org.json.JSONArray;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.INDEX_CLIENT_OBJECT;
import tables.ITablesHandler;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;

@Entity
@Table( name = "arrays" )
public class IndexArraysTable extends TablesArraysFather {


	@Column( name = "optimiMoveList" )
	private String optimiMoveList = new JSONArray ( ).toString ( );
	@Column( name = "pesimiMoveList" )
	private String pesimiMoveList = new JSONArray ( ).toString ( );
	@Column( name = "conBdCounterList" )
	private String conBdCounterList = new JSONArray ( ).toString ( );
	@Column( name = "indexlist" )
	private String indexlist = new JSONArray ( ).toString ( );
	@Column( name = "opList" )
	private String opList = new JSONArray ( ).toString ( );

	public IndexArraysTable ( int id, String name ) {

		super ( id, name );

	}

	public IndexArraysTable () {
		super ( );
	}

	public IndexArraysTable ( int id, String name, String optimiMoveList, String pesimiMoveList,
	                          String conBdCounterList, String indexlist, String opList ) {

		super ( id, name );

		this.optimiMoveList = optimiMoveList;
		this.pesimiMoveList = pesimiMoveList;
		this.conBdCounterList = conBdCounterList;
		this.indexlist = indexlist;
		this.opList = opList;
	}

	public String getIndexlist () {
		return indexlist;
	}

	public String getOpList () {
		return opList;
	}

	@Override
	public String toString () {
		return "IndexArraysTable{" +
				"optimiMoveList='" + optimiMoveList + '\'' +
				", pesimiMoveList='" + pesimiMoveList + '\'' +
				", conBdCounterList='" + conBdCounterList + '\'' +
				", indexlist='" + indexlist + '\'' +
				", opList='" + opList + '\'' +
				'}';
	}

	public static class Handler implements ITablesHandler {

		BASE_CLIENT_OBJECT client;

		public Handler ( BASE_CLIENT_OBJECT client ) {
			this.client = client;
		}

		@Override
		public void insertLine () {

		}

		@Override
		public Object getTableObject () {
			return null;
		}

		@Override
		public void loadData () {

			try {

				Class entityClass = client.getTables ( ).getTableArrays ( ).getClass ( );
				int id = client.getDbId ();

				IndexArraysTable table = ( IndexArraysTable ) HB.get_line_by_id ( entityClass, id, client.getSessionfactory () );

				convertJsonArrayToDoubleArray ( new JSONArray ( table.getOpList ( ) ), client.getOptionsHandler ( ).getMainOptions ( ).getOpList ( ) );
				convertJsonArrayToDoubleArray ( new JSONArray ( table.getIndexlist ( ) ), ( ArrayList < Double > ) client.getListMap ( ).get ( "index" ).getList ( ) );

				client.setLoadArraysFromHB ( true );

			} catch ( Exception e ) {
				e.printStackTrace ( );
				Arik.getInstance ( ).sendMessage ( Arik.sagivID,
						client.getName ( ) + " Load arrays HB faild \n" + e.getCause ( ), null );
			}
		}

		@Override
		public void resetData () {

			try {

				IndexArraysTable arraysTable = new IndexArraysTable ( client.getDbId ( ), client.getName ( ) );
				HB.update ( client.getSessionfactory ( ), arraysTable );

			} catch ( Exception e ) {
				e.printStackTrace ( );
				Arik.getInstance ( ).sendMessage ( Arik.sagivID,
						client.getName ( ) + " Reset arrays HB faild \n" + e.getCause ( ), null );
			}

		}

		@Override
		public void updateData () {

			try {

				if ( client instanceof INDEX_CLIENT_OBJECT ) {
					IndexArraysTable arraysTable = new IndexArraysTable ( client.getDbId ( ), client.getName ( ),
							new JSONArray ( ).toString ( ),
							new JSONArray ( ).toString ( ),
							new JSONArray ( ).toString ( ),
							new JSONArray ( client.getListMap ( ).get ( "index" ).getAsDoubleList ( ) ).toString ( ),
							new JSONArray ( client.getOptionsHandler ( ).getMainOptions ( ).getOpList ( ).toString ( ) ).toString ( ) );

					HB.update ( client.getSessionfactory ( ), arraysTable );
				}

			} catch ( Exception e ) {
				e.printStackTrace ( );
				Arik.getInstance ( ).sendMessage ( Arik.sagivID,
						client.getName ( ) + " Update arrays HB faild \n" + e.getCause ( ), null );
			}

		}

		@Override
		public void updateObject () {

		}


		// Convert json array to arrayList<Double>
		public void convertJsonArrayToDoubleArray ( JSONArray jsonArray, ArrayList < Double > list ) {

			for ( int i = 0; i < jsonArray.length ( ); i++ ) {

				list.add ( jsonArray.getDouble ( i ) );

			}

		}
	}


}
