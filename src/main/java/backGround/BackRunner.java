package backGround;

import api.Manifest;
import arik.Arik;
import options.Options;
import serverObjects.BASE_CLIENT_OBJECT;
import threads.MyThread;

import java.time.LocalTime;

public class BackRunner {

    LocalTime now;
    private BASE_CLIENT_OBJECT client;
    private Runner runner;

    public BackRunner( BASE_CLIENT_OBJECT client ) {
        this.client = client;
    }

    public void startRunner() {
        getRunner( ).getHandler( ).start( );
    }

    public void closeRunner() {
        getRunner( ).getHandler().close();
    }

    public Runner getRunner() {
        if ( runner == null ) {
            runner = new Runner( client );
        }
        return runner;
    }

    public void setRunner( Runner runner ) {
        this.runner = runner;
    }

    public class Runner extends MyThread implements Runnable {

        public Runner( BASE_CLIENT_OBJECT client ) {
            super( client );
        }

        boolean run = true;
        double last_0 = client.getIndex( );

        @Override
        public void run() {

            checkAllOptionsData( );

            while ( isRun( ) ) {


                try {

                    // sleep
                    Thread.sleep( 2000 );

                    now = LocalTime.now( );

                    double last = client.getIndex( );

                    // Start
                    if ( now.isAfter( client.getIndexStartTime( ) ) && !client.isStarted( ) && last_0 != last ) {

                        if ( client.getOpen( ) == 0 ) {
                            client.setOpen( last );
                        }

                        client.startAll( );
                    }

                    // Close runners
                    if ( now.isAfter( client.getIndexEndTime( ) ) && client.isDbRunning( ) ) {

                        if ( Manifest.DB ) {
                            // Arik
                            Arik.getInstance( ).sendMessageToEveryOne( client.getArikSumLine( ) );
                        }

                        client.getMyServiceHandler( ).removeService( client.getMySqlService( ) );
                        client.getMyServiceHandler( ).removeService( client.getListsService( ) );
                    }

                    // Export
                    if ( now.isAfter( client.getFutureEndTime( ) ) ) {
                        client.fullExport( );
                        client.closeAll( );
                        break;
                    }

                } catch ( InterruptedException e ) {
                } catch ( Exception e ) {
                    Arik.getInstance( ).sendMessage( e.getMessage( ) + "\n" + e.getCause( ) );
                }
            }
        }

        public void checkAllOptionsData() {
            for ( Options options : client.getOptionsHandler( ).getOptionsList( ) ) {
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
