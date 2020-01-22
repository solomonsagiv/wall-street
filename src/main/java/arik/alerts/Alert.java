package arik.alerts;

import com.pengrad.telegrambot.model.Update;
import serverObjects.indexObjects.INDEX_CLIENT_OBJECT;

public class Alert {

    // Veriables
    private int id;
    private INDEX_CLIENT_OBJECT stockObject;
    private double target;
    private AlertsThread alertsThread;
    private Update update;

    // Constructor
    public Alert( int id, INDEX_CLIENT_OBJECT stockObject, double target, Update update ) {
        this.id = id;
        this.stockObject = stockObject;
        this.target = target;
        this.update = update;

        // Add to alert list
        AlertsHandler.getInstance( ).getAlerts( ).add( this );
    }


    // Start the alert runner
    public void startAlertRunner() {
        alertsThread = new AlertsThread( this, update );
        alertsThread.start( );
    }

    // ---------- Getters and Setters ---------- //
    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public INDEX_CLIENT_OBJECT getStockObject() {
        return stockObject;
    }

    public void setStockObject( INDEX_CLIENT_OBJECT stockObject ) {
        this.stockObject = stockObject;
    }

    public double getTarget() {
        return target;
    }

    public void setTarget( double target ) {
        this.target = target;
    }

    public AlertsThread getAlertsThread() {
        return alertsThread;
    }

    public void setAlertsThread( AlertsThread alertsThread ) {
        this.alertsThread = alertsThread;
    }

    @Override
    public String toString() {
        return "Alert [id=" + id + ", stockObject=" + stockObject + ", target=" + target + ", alertsThread="
                + alertsThread + "]";
    }


}
