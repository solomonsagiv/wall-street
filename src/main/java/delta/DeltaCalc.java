package delta;

import exp.E;
import exp.ExpStrings;
import serverObjects.indexObjects.Spx;

import java.util.Scanner;

public class DeltaCalc {

    public static double calc( int quantity, double last, double preBid, double preAsk ) {

        double delta = 0;

        if ( last > 0 && preBid > 0 && preAsk > 0 ) {

            // Buy ( Last == pre ask )
            if ( last >= preAsk ) {
                delta = quantity;
            }

            // Sell ( Last == pre bid )
            if ( last <= preBid ) {
                delta = quantity * -1;
            }

//            System.out.println( );
//            System.out.println( "Q " + quantity );
//            System.out.println( "pre Bid: " + preBid );
//            System.out.println( "Last " + last );
//            System.out.println( "pre ask: " + preAsk );
//            System.out.println( "Delta: " + delta );

        }
        return delta;
    }

}
