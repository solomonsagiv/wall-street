package backGround;

import api.Manifest;
import arik.Arik;
import exp.Exp;
import options.Options;
import serverObjects.BASE_CLIENT_OBJECT;
import threads.MyThread;

import java.time.LocalTime;
import java.util.HashMap;

public class BackGroundHandler {

    public static BackGroundHandler instance;

    private BackGroundHandler() {
    }

    public static BackGroundHandler getInstance() {
        if ( instance == null ) {
            instance = new BackGroundHandler( );
        }
        return instance;
    }

    HashMap< String, BackRunner > backRunners = new HashMap<>( );

    public BackRunner createNewRunner( BASE_CLIENT_OBJECT client ) {
        if ( !backRunners.containsKey( client.getName( ) ) ) {

            // Start new runner
            BackRunner backRunner = new BackRunner( client );
            backRunner.getHandler( ).start( );

            // Append to map
            backRunners.put( client.getName( ), backRunner );

            return backRunner;
        } else {
            return backRunners.get( client.getName( ) );
        }
    }

    public void removeRunner( BASE_CLIENT_OBJECT client ) {
        backRunners.remove( client.getName( ) );
    }

    // Clients
    private class BackRunner extends MyThread implements Runnable {

        public BackRunner( BASE_CLIENT_OBJECT client ) {
            super( client );
        }

        boolean run = true;
        double last_0 = client.getIndex( );
        boolean runnersClosed = false;
        LocalTime now;

        @Override
        public void run() {

            checkAllOptionsData( );

            while ( isRun( ) ) {
                try {

                    // Sleep
                    Thread.sleep( 2000 );

                    now = LocalTime.now( );

                    double last = client.getIndex( );

                    // Index start time
                    if ( now.isAfter( client.getIndexStartTime( ) ) && !client.isStarted( ) && last_0 != last ) {
                        if ( client.getOpen( ) == 0 ) {
                            client.setOpen( last );
                        }
                        client.startAll( );
                    }

                    //  Index end time ( Close runners )
                    if ( now.isAfter( client.getIndexEndTime( ) ) && !runnersClosed ) {

                        if ( Manifest.DB ) {
                            // Arik
                            Arik.getInstance( ).sendMessageToEveryOne( client.getArikSumLine( ) );
                        }

                        client.getMyServiceHandler( ).removeService( client.getMySqlService( ) );
                        client.getMyServiceHandler( ).removeService( client.getListsService( ) );

                        runnersClosed = true;
                    }

                    // Future end time ( Export )
                    if ( now.isAfter( client.getFutureEndTime( ) ) ) {
                        client.closeAll( );
                        client.fullExport( );
                        break;
                    }
                } catch ( InterruptedException e ) {
                } catch ( Exception e ) {
                    Arik.getInstance( ).sendMessage( e.getMessage( ) + "\n" + e.getCause( ) );
                }
            }
        }

        public void checkAllOptionsData() {
            for ( Exp exp : client.getExps( ).getExpList( ) ) {
                Options options = exp.getOptions( );
                options.checkOptionData( );
            }
        }
        @Override
        public void initRunnable() {
            setRunnable( this );
        }

        public boolean isRun() {
            return run;
        }

        public void setRun( boolean run ) {
            this.run = run;
        }

    }

}