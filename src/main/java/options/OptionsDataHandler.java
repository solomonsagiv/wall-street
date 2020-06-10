package options;

import blackScholes.MyBlackScholes;
import serverObjects.BASE_CLIENT_OBJECT;
import threads.MyThread;

public class OptionsDataHandler {

    public Runner runner;
    String[] state = { "", "" };
    BASE_CLIENT_OBJECT client;

    Options options;
    private int sleep = 300;
    private int sleepCount = 0;
    private double avgTheoMargin = .05;

    // Constructor
    public OptionsDataHandler( BASE_CLIENT_OBJECT client ) {
        this.client = client;
        this.options = client.getExps( ).getMainExp( ).getOptions();
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

    public double getAvgTheoMargin() {
        return avgTheoMargin;
    }

    public void setAvgTheoMargin( double avgTheoMargin ) {
        this.avgTheoMargin = avgTheoMargin;
    }

    public Options getOptionsFather() {
        return options;
    }

    public void setOptions( Options options ) {

        if ( options.getStrikes( ).size( ) != 0 ) {
            this.options = options;
        }
    }

    // ---------- Runner ---------- //
    public class Runner extends MyThread implements Runnable {

        Option call;
        Option put;
        // Vega, delta
        double[] greeks;
        double stDev;
        double contract;
        double daysLeft;
        double interest;

        public Runner( BASE_CLIENT_OBJECT client ) {
            super( client );
            setName( "OptionsData" );
        }

        @Override
        public void initRunnable() {
            setRunnable( this );
        }

        @Override
        public void run() {

            while ( isRun() ) {
                try {

                    // Calculate Standard deviation
                    handleStDev( );

                    // Sleep
                    Thread.sleep( sleep );

                    sleepCount += sleep;

                } catch ( InterruptedException e ) {
                    break;
                } catch ( Exception e ) {
                    e.printStackTrace();
                    break;
                }
            }
        }

        private void handleStDev() {

            contract = options.calcContractAbsolute( );
            daysLeft = options.getAbsolutDays( );
            interest = options.getProps().getInterestZero( );

            for ( Strike strike : options.getStrikes( ) ) {
                try {

                    call = strike.getCall( );
                    put = strike.getPut( );

                    if ( strike.getStDev( ) == 0 ) {

                        if ( call.gotBidAsk( ) && put.gotBidAsk( ) ) {

                            stDev = MyBlackScholes.findStDev( strike, contract, daysLeft, interest );
                            strike.setStDev( stDev );

                        }
                    } else {
                        if ( sleepCount % ( sleep * 20 ) == 0 ) {

                            // Update stDev
                            stDev = MyBlackScholes.updateStDev( contract, strike, daysLeft, interest );
                            strike.setStDev( stDev );

                            // ----- Call ----- //
                            // Delta greeks
                            greeks = MyBlackScholes.greek( call, contract, daysLeft / 360.0, stDev, interest );

                            call.setDelta( greeks[ 1 ] );
                            call.setVega( greeks[ 3 ] );

                            // ----- Put ----- //
                            // Delta greeks
                            greeks = MyBlackScholes.greek( put, contract, daysLeft / 360.0, stDev, interest );
                            put.setDelta( greeks[ 1 ] );
                            put.setVega( greeks[ 3 ] );

                        }

                        // Update theoretic prices
                        double[] theos = MyBlackScholes.findTheos( strike, contract, daysLeft, interest );
                        call.setTheoreticPrice( theos[ 0 ] );
                        put.setTheoreticPrice( theos[ 1 ] );

                    }

                } catch ( Exception e ) {
                    e.printStackTrace( );
                }
            }
        }
    }

}
