package backGround;

import arik.Arik;
import options.Options;
import serverObjects.BASE_CLIENT_OBJECT;

import java.time.LocalTime;

public class BackRunner {

    LocalTime now;
    private BASE_CLIENT_OBJECT client;
    private Runner runner;

    public BackRunner( BASE_CLIENT_OBJECT client ) {
        this.client = client;
    }

    public void startRunner() {
        try {
            if ( !getRunner( ).isAlive( ) ) {
                getRunner( ).start( );
            }
        } catch ( IllegalThreadStateException e ) {
            setRunner( null );
            getRunner( ).start( );
        }
    }

    public void closeRunner() {
        getRunner( ).close( );
    }

    public Runner getRunner() {
        if ( runner == null ) {
            runner = new Runner( );
        }
        return runner;
    }

    public void setRunner( Runner runner ) {
        this.runner = runner;
    }

    public class Runner extends Thread {

        boolean run = true;
        double last_0 = client.getIndex( );

        @Override
        public void run() {

            checkAllOptionsData( );

            while ( isRun( ) ) {
                try {

                    // Sleep
                    sleep( 2000 );

                    now = LocalTime.now( );

                    double last = client.getIndex( );

                    // Start
                    if ( now.isAfter( client.getStartOfIndexTrading( ) ) && !client.isStarted( ) && last_0 != last ) {

                        if ( client.getOpen( ) == 0 ) {
                            client.setOpen( last );
                        }

                        client.startAll( );
                    }

                    // Close runners
                    if ( now.isAfter( client.getEndOfIndexTrading( ) ) && client.isDbRunning( ) ) {
                        client.getDb( ).closeAll( );
                        client.getRegularListUpdater( ).getHandler( ).close( );

                        // Arik
                        Arik.getInstance().sendMessageToEveryOne( client.getArikSumLine() );
                    }

                    // Export
                    if ( now.isAfter( client.getEndFutureTrading( ) ) ) {
                        client.fullExport( );
                        client.closeAll( );
                        break;
                    }


                } catch ( InterruptedException e ) {
                    close( );
                }
            }
        }

        public void checkAllOptionsData() {

            for ( Options options : client.getOptionsHandler( ).getOptionsList( ) ) {

                options.checkOptionData( );

            }

        }

        public boolean isRun() {
            return run;
        }

        public void setRun( boolean run ) {
            this.run = run;
        }

        public void close() {
            run = false;
        }
    }
}
