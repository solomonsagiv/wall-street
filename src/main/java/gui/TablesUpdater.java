package gui;

import options.Option;
import serverObjects.indexObjects.Dax;
import serverObjects.indexObjects.Ndx;
import serverObjects.indexObjects.Spx;

import javax.swing.*;
import java.awt.*;

public class TablesUpdater {

    static int spxStartTableStrike = 0;
    static int spxEndTableStrike = 0;
    WallStreetWindow window;
    Dax dax;
    Spx spx;
    Ndx ndx;
    private Runner runner;

    public TablesUpdater() {
        this.window = WallStreetWindow.window;

        dax = Dax.getInstance( );
        spx = Spx.getInstance( );
        ndx = Ndx.getInstance( );

        try {
            Thread.sleep( 3000 );
        } catch ( InterruptedException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace( );
        }
        spx.setFuture( spx.getFuture( ) );
        setSpxTableStrikes( ( ( int ) ( spx.getFuture( ) / 10 ) ) * 10 );

    }

    public static void setSpxTableStrikes( int price ) {
        spxStartTableStrike = price - 30;
        spxEndTableStrike = price + 40;
    }

    public void startRunner() {
        if ( runner == null ) {
            runner = new Runner( );
        }

        if ( !runner.isAlive( ) ) {
            runner.start( );
        }
    }

    public void closeRunner() {
        if ( runner != null ) {
            runner.close( );
            runner = null;
        }
    }

    private class Runner extends Thread {

        Color green = new Color( 0, 128, 0 );
        Color red = new Color( 229, 19, 0 );
        private boolean run = true;

        public Runner() {
        }

        @Override
        public void run() {

            while ( run ) {
                try {
                    updateDataToTables( );
                    // Sleep
                    sleep( 1000 );
                } catch ( InterruptedException e ) {
                    close( );
                }
            }
        }

        private void updateDataToTables() {

            JTable spxTable = window.spxOptionsTable;
            setStrikes( spxTable );

            for ( int row = 0; row < spxTable.getRowCount( ); row++ ) {
                double strike;
                try {
                    strike = ( int ) spxTable.getValueAt( row, 1 );
                    Option call = spx.getOptionsHandler( ).getOptionsDay( ).getOption( "c" + strike );
                    spxTable.setValueAt( call.getBidAskCounter( ), row, 0 );

                    Option put = spx.getOptionsHandler( ).getOptionsDay( ).getOption( "p" + strike );
                    spxTable.setValueAt( put.getBidAskCounter( ), row, 2 );

                } catch ( NullPointerException e ) {
                    continue;
                }
            }
        }

        private void setStrikes( JTable table ) {

            int increment = 0;

            for ( int row = 0; row < table.getRowCount( ); row++ ) {
                table.setValueAt( spxStartTableStrike + increment, row, 1 );
                increment += 10;
            }
        }

        public void close() {
            run = false;
        }

        // Present
        public void colorForfInt( JTextField field, double val ) {

            if ( val > 0 ) {
                field.setText( str( ( int ) val ) );
                field.setForeground( green );
            } else {
                field.setText( str( ( int ) val ) );
                field.setForeground( red );
            }
        }


        // To string
        public String str( Object o ) {
            return String.valueOf( o );
        }
    }
}
