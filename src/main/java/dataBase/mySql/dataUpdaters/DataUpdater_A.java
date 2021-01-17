package dataBase.mySql.dataUpdaters;

import dataBase.mySql.MySql;
import exp.Exps;
import serverObjects.BASE_CLIENT_OBJECT;

import java.time.Instant;
import java.util.ArrayList;

public class DataUpdater_A extends IDataUpdater {

    public static void main( String[] args ) {
        System.out.println( Instant.now() );

        String query = "INSERT INTO %s.%s (time, value) VALUES (cast('%s' as timestamp with time zone), %s)";

        String q = String.format( query, "spx", "index", Instant.now().toString(), 656 );


        MySql.insert( q, true );

    }


    ArrayList<MyTimeStampObject> index_timestamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> index_bid_timestamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> index_ask_timestamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> fut_day_timeStamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> fut_week_timeStamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> fut_month_timeStamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> fut_e1_timeStamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> fut_e2_timeStamp = new ArrayList<>();


    double index_0 = 0;
    double index_bid_0 = 0;
    double index_ask_0 = 0;
    double fut_day_0 = 0;
    double fut_week_0 = 0;
    double fut_month_0 = 0;
    double fut_e1_0 = 0;
    double fut_e2_0 = 0;
    double index_bid_ask_counter_0 = 0;
    Exps exps;

    String query = "INSERT INTO %s.%s (time, value) VALUES (cast('%s' as timestamp with time zone), %s)";
    String localQuery = "";

    public DataUpdater_A( BASE_CLIENT_OBJECT client ) {
        super( client );
        exps = client.getExps( );
    }



    @Override
    public void insertData() {



        // Index
        if ( client.getIndex( ) != index_0 ) {
            index_0 = client.getIndex( );
            index_timestamp.add( new MyTimeStampObject( Instant.now(), index_0 ) );


            localQuery = String.format( query, client.getName( ), "index", Instant.now(), index_0 );
            MySql.insert( localQuery, true );
        }
//        // Index bid
////        if ( client.getIndexBid() != index_bid_0 ) {
//        index_bid_0 = client.getIndex( );
//        localQuery = String.format( query, client.getName( ), "index_bid", index_bid_0 );
//        MySql.insert( localQuery, true );
////        }
//
//        // Index ask
////        if ( client.getIndexAsk() != index_ask_0 ) {
//        index_0 = client.getIndexAsk( );
//        localQuery = String.format( query, client.getName( ), "index_ask", index_ask_0 );
//        MySql.insert( localQuery, true );
////        }
//
//        // Fut day
//        double fut_day = exps.getExp( ExpStrings.day ).getFuture( );
//
////        if ( fut_day != fut_day_0 ) {
//        fut_day_0 = fut_day;
//        localQuery = String.format( query, client.getName( ), "fut_day", fut_day_0 );
//        MySql.insert( localQuery, true );
////        }
//
//        // Fut week
//        double fut_week = exps.getExp( ExpStrings.week ).getFuture( );
//
////        if ( fut_week != fut_week_0 ) {
//        fut_week_0 = fut_week;
//        localQuery = String.format( query, client.getName( ), "fut_week", fut_week_0 );
//        MySql.insert( localQuery, true );
////        }
//
//        // Fut month
//        double fut_month = exps.getExp( ExpStrings.month ).getFuture( );
//
////        if ( fut_month != fut_month_0 ) {
//        fut_month_0 = fut_month;
//        localQuery = String.format( query, client.getName( ), "fut_month", fut_month_0 );
//        MySql.insert( localQuery, true );
////        }
//
//        // Fut e1
//        double fut_e1 = exps.getExp( ExpStrings.e1 ).getFuture( );
//
////        if ( fut_e1 != fut_e1_0 ) {
//        fut_e1_0 = fut_e1;
//        localQuery = String.format( query, client.getName( ), "fut_e1", fut_e1_0 );
//        MySql.insert( localQuery, true );
////        }
//
//        // Fut e2
//        double fut_e2 = exps.getExp( ExpStrings.e2 ).getFuture( );
//
////        if ( fut_e2 != fut_e2_0 ) {
//        fut_e2_0 = fut_e2;
//        localQuery = String.format( query, client.getName( ), "fut_e2", fut_e2_0 );
//        MySql.insert( localQuery, true );
////        }

    }
}
