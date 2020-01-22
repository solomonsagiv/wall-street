package arik.alerts;

import arik.Arik;
import com.pengrad.telegrambot.model.Update;
import serverObjects.indexObjects.INDEX_CLIENT_OBJECT;

public class AlertsThread extends Thread {

    double future;
    Arik arik = Arik.getInstance( );
    AlertsHandler alertsHandler = AlertsHandler.getInstance( );
    private boolean run = true;
    private double target_price;
    private boolean up;
    private Update update;
    private INDEX_CLIENT_OBJECT client;
    private Alert alert;

    // Constructor
    public AlertsThread( Alert alert, Update update ) {
        this.alert = alert;
        this.target_price = alert.getTarget( );
        this.update = update;
        this.client = alert.getStockObject( );
    }

    @Override
    public void run() {
        future = client.getIndex( );

        // Check id the target price is greater or lower than current price
        up = target_price > future;

        while ( run ) {
            try {
                future = client.getIndex( );
                System.out.println( "Running for: " + target_price + ", Future: " + future + ", Boolean: " + up );

                if ( up ) {
                    if ( future >= target_price ) {
                        arik.sendMessage( update, " Alert reached the target price " + target_price, null );
                        alertsHandler.getAlerts( ).remove( alert );
                        break;
                    }
                } else {
                    if ( future <= target_price ) {
                        arik.sendMessage( update, " Alert reached the target price " + target_price, null );
                        alertsHandler.getAlerts( ).remove( alert );
                        break;
                    }
                }
                Thread.sleep( 1000 );
            } catch ( InterruptedException e ) {
                e.printStackTrace( );
            }
        }
    }


    public void close() {
        run = false;
    }

    /**
     * Getters and setters
     */
    public double getTarget_price() {
        return target_price;
    }

    public void setTarget_price( double target_price ) {
        this.target_price = target_price;
    }

    public boolean isUp_down() {
        return up;
    }

    public void setUp_down( boolean up_down ) {
        this.up = up_down;
    }

    public boolean isRun() {
        return run;
    }

    public void setRun( boolean run ) {
        this.run = run;
    }

    public Alert getAlert() {
        return alert;
    }

    public void setAlert( Alert alert ) {
        this.alert = alert;
    }

}
