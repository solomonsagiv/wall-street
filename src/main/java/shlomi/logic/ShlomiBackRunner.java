package shlomi.logic;

import locals.Themes;
import threads.MyThread;

public class ShlomiBackRunner extends MyThread implements Runnable {

    TraderWindow window;
    ShlomiTrader trader;

    // Constructor
    public ShlomiBackRunner( ShlomiTrader trader, TraderWindow window ) {
        super( trader.getStock( ) );
        setName( "Shlomi runner" );
        this.trader = trader;
        this.window = window;

    }

    @Override
    public void initRunnable() {
        setRunnable( this );
    }

    @Override
    public void run() {

        getClient( ).getTablesHandler( ).getStatusHandler( ).getHandler( ).loadData( );
        getClient( ).getTablesHandler( ).getArrayHandler( ).getHandler( ).loadData( );

        while ( isRun( ) ) {
            try {

                // Load db data
                loadDbData( );

                // Update the window
                updateData( );

            } catch ( Exception e ) {
                e.printStackTrace( );
                getHandler( ).close( );
            }
        }
    }

    // Load status data
    private void loadDbData() {

        getClient( ).getTablesHandler( ).getStatusHandler( ).getHandler( ).loadData( );

    }

    // Update the window
    private void updateData() {

        // Status
        if ( trader.getShlomiRunner( ).isRun( ) ) {
            window.statusLabel.setForeground( Themes.GREEN );
        } else {
            window.statusLabel.setForeground( Themes.RED );
        }

        window.statusLabel.setText( str( trader.getShlomiRunner( ).isRun( ) ) );

        // Log
//		window.log.append("\n" + str(getClient().getOpAvg15FromDb() + "\n" + str(getClient().getIndexSum())));

    }

    public String str( Object o ) {
        return String.valueOf( o );
    }

}
