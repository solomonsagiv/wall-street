package dataBase;

import api.Manifest;
import arik.Arik;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.SpxCLIENTObject;
import tables.TableDayFather;
import tables.TablesHandler;
import tables.daily.SpxTable;
import threads.MyThread;

import java.util.ArrayList;

//MySql class
public class MySqlRunner extends MyThread implements Runnable {

    BASE_CLIENT_OBJECT client;
    long sleepCount = 0;
    long sleep = 500;
    TablesHandler tablesHandler;


    public MySqlRunner( BASE_CLIENT_OBJECT client ) {
        super( client );
        setName( "MYSQL" );
        this.client = client;
        tablesHandler = client.getTablesHandler( );

    }

    public static void main( String[] args ) {
        TableDayFather father = new SpxTable( );

        SpxTable spx = new SpxTable( );
        spx.setOpAvg15( 89 );

        father = spx;

        ArrayList< TableDayFather > lines = new ArrayList<>( );

//		HB.save( father , SpxTable.class.getName() , HBsession.getParisFactory () );

        for ( int i = 0; i < 100; i++ ) {
            lines.add( new SpxTable( ) );
        }

        SpxCLIENTObject.getInstance( ).getTablesHandler( ).getSumHandler( ).getHandler( ).insertLine( );

        long startTime = System.currentTimeMillis( );

        SpxCLIENTObject.getInstance( ).getTablesHandler( ).getSumHandler( ).getHandler( ).insertLine( );

        long endTime = System.currentTimeMillis( );

        double duration = ( endTime - startTime );  //divide by 1000000 to get milliseconds
        System.out.println( duration / 1000 );

    }

    @Override
    public void initRunnable() {
        setRunnable( this );
    }

    @Override
    public void run() {

        setRun( true );

        while ( isRun( ) ) {
            try {

                Thread.sleep( sleep );

                // Updater
                if ( Manifest.DB_UPDATER ) {
                    // Status
                    if ( sleepCount % 1000 == 0 ) {
                        client.getTablesHandler( ).getStatusHandler( ).getHandler( ).updateData( );
                    }
                }

                // DB runner
                if ( Manifest.DB_RUNNER ) {

                    // Insert line
                    client.getTablesHandler( ).getDayHandler( ).getHandler( ).insertLine( );

                    // Arrays
                    if ( sleepCount % 30000 == 0 ) {
                        client.getTablesHandler( ).getArrayHandler( ).getHandler( ).updateData( );
                    }

                    // Reset sleep count
                    if ( sleepCount == 3000000 ) {
                        sleepCount = 0;
                    }

                    client.setDbRunning( true );
                    setRun( true );

                    // Sleep
                    sleepCount += sleep;
                }

            } catch ( InterruptedException e ) {
                setRun( false );
                getHandler( ).close( );
            } catch ( NumberFormatException e ) {
                e.printStackTrace( );
                continue;
            } catch ( Exception e ) {
//                Arik.getInstance( ).sendMessage( Arik.sagivID, client.getName( ) + " MYSQL exception \n" + e.getCause( ),
//                        null );
            }
        }

        setRun( false );
        client.setDbRunning( false );
//        Arik.getInstance( ).sendMessage( Arik.sagivID, client.getName( ) + " MYSQL kiiled !!!", null );

    }


}
