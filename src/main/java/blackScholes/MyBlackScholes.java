package blackScholes;

import options.Option;
import options.Strike;

public class MyBlackScholes {

    public static double[] findTheos( Strike strike, double future, double daysLeft, double interest ) {

        // TEST DATA -- trade 106005
        double targetPrice = future;
        double timeToExp = daysLeft / 360.0;

        Option call = strike.getCall( );
        Option put = strike.getPut( );

        double callCalc = BlackScholesFormula.calculate( call.isCallOrPut( ), targetPrice, call.getStrike( ), interest,
                timeToExp, call.getStDev( ) );

        double putCalc = BlackScholesFormula.calculate( put.isCallOrPut( ), targetPrice, put.getStrike( ), interest,
                timeToExp, put.getStDev( ) );

        double[] theos = { callCalc, putCalc };
        return theos;

    }

    public static double findStDev( Strike strike, double future, double daysLeft, double interest ) {

        // TEST DATA -- trade 106005
        double targetPrice = future;
        double timeToExp = daysLeft / 360.0;
        double stDev = 0.000;
        double stIncrement = 0.0005;

        boolean callEntered = false;
        boolean putEntered = false;

        Option call = strike.getCall( );
        Option put = strike.getPut( );

        // Calc by call
        if ( strike.getStrike( ) > future ) {

            while ( true ) {

                double callCalc = BlackScholesFormula.calculate( call.isCallOrPut( ), targetPrice, call.getStrike( ),
                        interest, timeToExp, stDev );


                if ( !callEntered ) {

                    if ( callCalc < call.getBidAskAvg( ) ) {
                        stDev += stIncrement;
                    } else if ( callCalc > call.getBidAskAvg( ) ) {
                        return stDev;
                    }

                }

                // Return stDev > 0.7
                if ( stDev > 0.7 ) {
                    return 1;
                }

            }
        }
        // Calc by put
        else {

            while ( true ) {

                double putCalc = BlackScholesFormula.calculate( put.isCallOrPut( ), targetPrice, put.getStrike( ),
                        interest, timeToExp, stDev );

                if ( !putEntered ) {

                    if ( putCalc < put.getBidAskAvg( ) ) {
                        stDev += stIncrement;
                    } else if ( putCalc > put.getBidAskAvg( ) ) {
                        return stDev;
                    }

                }

                // Return stDev > 0.7
                if ( stDev > 0.7 ) {
                    return 1;
                }
            }
        }

    }

    public static double[] greek( Option option, double targetPrice, double timeToExp, double stDev, double interest ) {

        OptionDetails optionDetails = new OptionDetails( option.isCallOrPut( ), targetPrice, option.getStrike( ), interest,
                timeToExp, stDev );

        optionDetails = BlackScholesFormula.calculateWithGreeks( optionDetails );
        double[] greeks = optionDetails.getGreeaks( );

        return greeks;

    }

    public static double updateStDev( double future, Strike strike, double daysLeft, double interest ) {

        // TEST DATA -- trade 106005
        double strk = strike.getStrike( );
        double targetPrice = future;
        double timeToExp = daysLeft / 360.0;
        double stDev = strike.getStDev( );
        Option call = strike.getCall( );
        Option put = strike.getPut( );
        double stIncrement = 0.0005;

        double callCalc = BlackScholesFormula.calculate( call.isCallOrPut( ), targetPrice, call.getStrike( ), interest,
                timeToExp, stDev );

        double putCalc = BlackScholesFormula.calculate( put.isCallOrPut( ), targetPrice, put.getStrike( ), interest,
                timeToExp, stDev );

        // ----- Call ----- //
        if ( strk > future ) {

            if ( callCalc > call.getBidAskAvg( ) ) {
                stDev -= stIncrement;
            } else {
                stDev += stIncrement;
            }

        } else {

            if ( putCalc > put.getBidAskAvg( ) ) {
                stDev -= stIncrement;
            } else {
                stDev += stIncrement;
            }
        }

        return stDev;

    }

    public static double getTheoreticPrice( boolean callOption, double targetPrice, double strike, double interest,
                                            double timeToExp, double stDev ) {

        timeToExp = timeToExp / 360.0;

        OptionDetails optionDetails = new OptionDetails( callOption, targetPrice, strike, interest, timeToExp, stDev );

        optionDetails = BlackScholesFormula.calculateWithGreeks( optionDetails );
        double[] greeks = optionDetails.getGreeaks( );

        return greeks[ 0 ];

    }

    // Greeaks
    @SuppressWarnings( "static-access" )
    public static double[] getGreeaks( double strike, boolean CallPut, double price, double interest, double timeToExp,
                                       double stDev ) {
        OptionDetails optionDetails = new OptionDetails( CallPut, price, strike, interest, timeToExp, stDev );

        optionDetails = BlackScholesFormula.calculateWithGreeks( optionDetails );
        double[] d = optionDetails.getGreeaks( );

        return d;
    }

}
