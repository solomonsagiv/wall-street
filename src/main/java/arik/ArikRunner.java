package arik;

import arik.locals.Emojis;
import arik.locals.KeyBoards;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import serverObjects.indexObjects.INDEX_CLIENT_OBJECT;

import java.util.List;

public class ArikRunner extends Thread {

    boolean run = true;

    int[] allowed = { 365117561, 948009529, 513323078 };

    int sagiv_id = 365117561;

    Arik arik;
    boolean openOrders = false;

    int alert_lvl = 0;
    int kill_alert_lvl = 0;

    String current_url;

    INDEX_CLIENT_OBJECT stockObject;

    // Constructor
    public ArikRunner( Arik arik ) {
        this.arik = arik;
    }

    public static void main( String[] args ) {
        // Arik the bot
//        Arik bot = Arik.getInstance( );
//        ArikRunner arik = new ArikRunner( bot );
//        arik.start( );
    }

    // Run
    public void run() {
        try {
            loop( );
        } catch ( InterruptedException e ) {
            e.printStackTrace( );
        }
    }

    public void close() {
        run = false;
    }

    // Loop
    private void loop() throws InterruptedException {
        long date = 0;
        int alert_count = 1;

        try {
            while ( run ) {
                // BackGround
                // Get updates
                getUpdates( date, alert_count );
                sleep( 1000 );
            }
        } catch ( Exception e ) {
            e.printStackTrace( );
            sleep( 1000 );
            loop( );
        }
    }

    // Get upadtes
    private void getUpdates( long date, int alert_count ) {
        GetUpdatesResponse updatesResponse = arik.getBot( )
                .execute( new GetUpdates( ).limit( 1000 ).offset( arik.getUpdateId( ) ).timeout( 5 ) );
        List< Update > updates = updatesResponse.updates( );
        if ( updates.size( ) > 0 ) {
            for ( Update update : updates ) {
                if ( update.message( ).date( ) > date ) {
                    date = update.message( ).date( );
                    // Validate user
                    if ( is_allowed( allowed, update.message( ).from( ).id( ) ) ) {
                        try {

                            String user_text = update.message( ).text( );
                            System.out.println( user_text );

                            arik.sendMessage( update, update.message( ).from( ).firstName( ) + " \n" + update.message( ).from( ).id( ), KeyBoards.main( ) );

                        } catch ( Exception e ) {
                            arik.sendMessage( update, e.getMessage( ), null );
                        }
                    } else {
                        arik.sendMessage( update, " Sorry can't talk with you ", null );

                        // Notice me
                        arik.sendMessage(
                                "Someone try to talk with me " + "\n He said: " + update.message( ).text( )
                                        + "\n His name is: " + update.message( ).from( ).firstName( ) + "\n" + "id: "
                                        + update.message( ).from( ).id( )
                        );

                        break;
                    }
                }
            }
        }
    }

    // Is allowed
    public boolean is_allowed( int[] allowed, int id ) {
        for ( int i : allowed ) {
            if ( id == i ) {
                return true;
            }
        }
        return false;
    }

    // Kill all alerts
    public String kill_all_alerts() {
        return " Killed all the alerts " + Emojis.check_mark;
    }

    // Floor
    public double floor( double d, int zeros ) {
        return Math.floor( d * zeros ) / zeros;
    }

    // Str
    private String str( Object o ) {
        return String.valueOf( o );
    }

}
