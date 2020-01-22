package arik.alerts;

import arik.locals.Emojis;
import com.pengrad.telegrambot.model.Update;
import serverObjects.indexObjects.INDEX_CLIENT_OBJECT;

import java.util.ArrayList;

public class AlertsHandler {

    // The alert handler instance
    private static AlertsHandler alertsHandler;
    private int id = 0;
    // Variables
    private ArrayList< Alert > alerts = new ArrayList<>( );

    // Private constructor
    private AlertsHandler() {
    }

    // Get instance
    public static AlertsHandler getInstance() {
        if ( alertsHandler == null ) {
            alertsHandler = new AlertsHandler( );
        }
        return alertsHandler;
    }

    // Kill all alerts
    public void killAllAlerts() {
        for ( Alert alert : alerts ) {
            alert.getAlertsThread( ).close( );
        }
        alerts.clear( );
    }

    // Show all alerts
    public String show_alerts() {
        String s = "";
        if ( alerts.size( ) > 0 ) {
            for ( Alert alert : alerts ) {
                s += "Alert - " + alert.getId( ) + " Target - " + alert.getTarget( ) + " \n";
            }
        } else {
            s = "There are no alerts open " + Emojis.no_mouth;
        }
        return s;
    }


    // Kill thread
    public String kill( int id ) {
        String s = "There are no alerts with this name " + Emojis.open_mouth;
        // Iterate over set to find yours

        for ( Alert alert : alerts ) {
            if ( alert.getId( ) == id ) {
                s = "Alert - " + alert.getId( ) + " is dead " + Emojis.check_mark;
                alert.getAlertsThread( ).setRun( false );
                alerts.remove( alert );
                return s;
            }
        }
        return s;
    }

    // Create new alert
    public Alert newAlert( INDEX_CLIENT_OBJECT stockObject, double target, Update update ) {
        Alert alert = new Alert( id, stockObject, target, update );
        alert.startAlertRunner( );
        id++;
        return alert;
    }

    // ---------- Getters and Setters ---------- //
    public ArrayList< Alert > getAlerts() {
        return alerts;
    }

    public void setAlerts( ArrayList< Alert > alerts ) {
        this.alerts = alerts;
    }

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

}
