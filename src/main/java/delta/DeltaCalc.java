package delta;

import exp.E;
import exp.ExpEnum;
import options.Options;
import serverObjects.indexObjects.INDEX_CLIENT_OBJECT;
import serverObjects.indexObjects.Spx;

import java.util.Scanner;

public class DeltaCalc {


    public static void main( String[] args ) {
        Spx client = Spx.getInstance();

        E e = ( E ) client.getExps().getExp( ExpEnum.E1 );

        Scanner scanner = new Scanner(System.in);


        int q = 1;

        while ( true ) {

            System.out.println( );
            System.out.println( "Enter last" );

            double last = scanner.nextDouble();

            e.setVolumeFutForDelta( q );
            e.setFutForDelta( last );
            e.setFutBidForDelta( last - 0.5 );
            e.setFutAskForDelta( last + 0.5 );




            q += 1;
        }

    }

    public static double calc( int quantity, double last, double preBid, double preAsk ) {

        double delta = 0;

        // Buy ( Last == pre ask )
        if ( last == preAsk ) {
            delta = quantity;
        }

        // Buy ( Last == pre bid )
        if ( last == preBid ) {
            delta = quantity * -1;
        }

        System.out.println( delta + "$");

        return delta * 50;
    }

}
