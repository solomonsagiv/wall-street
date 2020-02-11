package options.fullOptions;

import locals.L;
import locals.Themes;
import options.Option;
import options.Options;
import options.Strike;
import serverObjects.BASE_CLIENT_OBJECT;
import threads.MyThread;
import javax.swing.*;
import java.awt.*;

public class FullOptionsUpdater extends MyThread implements Runnable {

    // Variables
    int sleep = 300;
    JTable table;
    Options options;

    // Constructor
    public FullOptionsUpdater( BASE_CLIENT_OBJECT client, Options options, JTable table ) {
        super( client );
        setName( "Full options updater" );
        this.table = table;
        this.options = options;
    }

    @Override
    public void initRunnable() {
        setRunnable( this );
    }

    @Override
    public void run() {

        while ( isRun( ) ) {
            try {

                // Updater text
                updateText( );

                // Sleep
                Thread.sleep( sleep );
            } catch ( InterruptedException e ) {
                break;
            } catch ( Exception e ) {
                try {
                    Thread.sleep( 1000 );
                } catch ( InterruptedException e1 ) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace( );
                }
                e.printStackTrace( );
            }
        }

    }

    private void updateText() throws Exception {

        int row = 0;

        // Each strike
        for ( Strike strike : options.getStrikes( ) ) {

            Option call = strike.getCall( );

            // Call
            table.setValueAt( L.format10( call.getVega( ) ), row, 0 );
            table.setValueAt( L.format10( call.getDelta( ) ), row, 1 );
            table.setValueAt( L.format100( call.getBid( ) ), row, 2 );
            table.setValueAt( L.format100( call.getTheoreticPrice( ) ), row, 3 );
            table.setValueAt( L.format100( call.getAsk( ) ), row, 4 );
            table.setValueAt( ( int ) call.getStrike( ), row, 5 );
            table.setValueAt( L.format10( call.getStDev( ) * 100 ), row, 6 );

            // Put
            Option put = strike.getPut( );
            table.setValueAt( L.format100( put.getBid( ) ), row, 7 );
            table.setValueAt( L.format100( put.getTheoreticPrice( ) ), row, 8 );
            table.setValueAt( L.format100( put.getAsk( ) ), row, 9 );
            table.setValueAt( L.format10( put.getDelta( ) ), row, 10 );
            table.setValueAt( L.format10( put.getVega( ) ), row, 11 );

            row++;
        }

        FullOptionsWindow.indexLabel.setText( str( getClient( ).getIndex( ) ) );

        double indexPre = floor( ( ( getClient( ).getIndex( ) - getClient( ).getBase( ) ) / getClient( ).getBase( ) ) * 100 );
        colorBackPresent( FullOptionsWindow.indexPresentLabel, indexPre );

        // Delta
        int delta = ( int ) getClient().getOptionsHandler().getPositionCalculator().getTotalData( PositionCalculator.DELTA );
        FullOptionsWindow.deltaLbl.colorForge( delta );

        // Vega
        int vega = ( int ) getClient().getOptionsHandler().getPositionCalculator().getTotalData( PositionCalculator.VEGA );
        FullOptionsWindow.vegaLbl.colorForge( vega );

        // Pnl
        int pnl = ( int ) getClient().getOptionsHandler().getPositionCalculator().getTotalData( PositionCalculator.PNL );
        FullOptionsWindow.pnlLbl.colorForge( pnl, Color.BLACK );

    }



    public double floor( double d ) {
        return Math.floor( d * 100 ) / 100;
    }

    public String str( Object o ) {
        return String.valueOf( o );
    }


    public Options getOptions() {
        return options;
    }

    public void setOptions( Options options ) {
        this.options = options;
    }


    // Present
    public void colorBackPresent( JLabel field, double val ) {

        if ( val >= 0 ) {
            field.setText( "(+" + str( val ) + "%)" );
            field.setForeground( Themes.GREEN );
        } else {
            field.setText( "(" + str( val ) + "%)" );
            field.setForeground( Themes.RED );
        }

    }

}
