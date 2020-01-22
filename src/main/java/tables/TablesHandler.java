package tables;

import arik.Arik;
import dataBase.HB;
import dataBase.HBsession;
import serverObjects.BASE_CLIENT_OBJECT;
import tables.bounds.BoundsTable;

import java.util.ArrayList;

public class TablesHandler {

	Tables tables;
	BASE_CLIENT_OBJECT client;

	// Handlers
	DayHandler dayHandler;
	SumHandler sumHandler;
	ArrayHandler arrayHandler;
	StatusHandler statusHandler;
	BoundsHandler boundsHandler;

	public TablesHandler( ITablesHandler dayHandler, ITablesHandler sumHandler,
	                      ITablesHandler statusHandler, ITablesHandler arrayHandler ) {

		this.dayHandler = new DayHandler( dayHandler );
		this.sumHandler = new SumHandler( sumHandler );
		this.statusHandler = new StatusHandler( statusHandler );
		this.arrayHandler = new ArrayHandler( arrayHandler );
		boundsHandler = new BoundsHandler ();

	}

	public class DayHandler {

		ITablesHandler handler;

		public DayHandler( ITablesHandler handler ) {
			this.handler = handler;
		}

		public ITablesHandler getHandler() {
			return handler;
		}

	}

	public class ArrayHandler {

		ITablesHandler handler;

		public ArrayHandler( ITablesHandler handler ) {
			this.handler = handler;
		}

		public ITablesHandler getHandler() {
			return handler;
		}

	}

	public class SumHandler {

		ITablesHandler handler;

		public SumHandler( ITablesHandler handler ) {
			this.handler = handler;
		}

		public ITablesHandler getHandler() {
			return handler;
		}

	}

	public class StatusHandler {

		ITablesHandler handler;

		public StatusHandler( ITablesHandler handler ) {
			this.handler = handler;
		}

		public ITablesHandler getHandler() {
			return handler;
		}

	}

	public class BoundsHandler {

		public BoundsTable getBound( String stockName , String name ) {

			try {

				String query = String.format( "from %s where %s = '%s' and %s = '%s'" , "BoundsTable" , "stockName" , stockName , "name" , name );

				BoundsTable boundsTable = ( BoundsTable ) HB.getLineByQuery( query , HBsession.getBoundsFactory() );

				return boundsTable;

			} catch ( Exception e ) {
				e.printStackTrace();
				Arik.getInstance().sendMessage( Arik.sagivID,
						client.getName() + " Get bound HB faild \n" + e.getCause() , null );
			}


			return null;
		}


		private void updateBound( String stockName , String name , int x , int y , int width , int height ) {

			try {
				// Get the current bound
				BoundsTable boundsTable = getBound( stockName , name );
				boundsTable.setX( x );
				boundsTable.setY( y );
				boundsTable.setWidth( width );
				boundsTable.setHeight( height );

				// Update the new bound
				HB.update( HBsession.getBoundsFactory() , boundsTable );
			} catch ( Exception e ) {
				e.printStackTrace();
				Arik.getInstance().sendMessage( Arik.sagivID ,
						client.getName() + " Update bound HB faild \n" + e.getCause() , null );
			}

		}

		public void updateBoundOrCreateNewOne( String stockName , String name , int x , int y , int width , int height ) {

			try {
				boolean exist = false;

				ArrayList < BoundsTable > bounds = ( ArrayList < BoundsTable > ) HB.getTableList( BoundsTable.class , HBsession.getBoundsFactory() );

				// For each check if exist
				for ( BoundsTable bound : bounds ) {
					if ( stockName.equals( bound.getStockName() ) && name.equals( bound.getName() ) ) {
						exist = true;
						break;
					}
				}

				// If not exist -> create new one
				if ( !exist ) {
					BoundsTable boundsTable = new BoundsTable( stockName , name , x , y , width , height );
					HB.save( boundsTable , "BoundsTable" , HBsession.getBoundsFactory() );
				} else {
					updateBound( stockName , name , x , y , width , height );
				}
			} catch ( Exception e ) {
				Arik.getInstance().sendMessage( Arik.sagivID ,
						client.getName() + " Create bound HB faild \n" + e.getCause() , null );
			}

		}

	}

	public DayHandler getDayHandler() {
		return dayHandler;
	}

	public SumHandler getSumHandler() {
		return sumHandler;
	}

	public StatusHandler getStatusHandler() {
		return statusHandler;
	}

	public BoundsHandler getBoundsHandler() {
		return boundsHandler;
	}

	public ArrayHandler getArrayHandler() {
		return arrayHandler;
	}

}
