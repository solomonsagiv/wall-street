package dataBase.mySql.dataUpdaters;

import charts.myChart.MyTimeSeries;
import dataBase.mySql.MySql;
import exp.Exps;
import serverObjects.BASE_CLIENT_OBJECT;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class DataBaseHandler_Dax extends IDataBaseHandler {

    static final String SAGIV_SCHEME = "sagiv";
    static final String DATA_SCHEME = "data";

    ArrayList< MyTimeStampObject > index_timestamp = new ArrayList<>( );
    ArrayList< MyTimeStampObject > index_bid_timestamp = new ArrayList<>( );
    ArrayList< MyTimeStampObject > index_ask_timestamp = new ArrayList<>( );
    ArrayList< MyTimeStampObject > fut_day_timeStamp = new ArrayList<>( );
    ArrayList< MyTimeStampObject > fut_week_timeStamp = new ArrayList<>( );
    ArrayList< MyTimeStampObject > fut_month_timeStamp = new ArrayList<>( );
    ArrayList< MyTimeStampObject > fut_e1_timeStamp = new ArrayList<>( );
    ArrayList< MyTimeStampObject > fut_e2_timeStamp = new ArrayList<>( );
    ArrayList< MyTimeStampObject > ind_bid_ask_counter_timestamp = new ArrayList<>( );
    ArrayList< MyTimeStampObject > op_avg_fut_day_timestamp = new ArrayList<>( );
    ArrayList< MyTimeStampObject > op_avg_fut_day_15_timestamp = new ArrayList<>( );
    ArrayList< MyTimeStampObject > ind_counter_timestamp = new ArrayList<>( );

    double index_0 = 0;
    double index_bid_0 = 0;
    double index_ask_0 = 0;
    double fut_day_0 = 0;
    double fut_week_0 = 0;
    double fut_month_0 = 0;
    double fut_e1_0 = 0;
    double fut_e2_0 = 0;
    double index_bid_ask_counter_0 = 0;
    double ind_counter_0;

    Exps exps;

    public DataBaseHandler_Dax(BASE_CLIENT_OBJECT client ) {
        super( client );
        exps = client.getExps( );
    }

    int sleep_count = 100;

    @Override
    public void insertData( int sleep ) {
//
//        // Update lists retro
//        if ( sleep_count % 10000 == 0 ) {
//            updateListsRetro( );
//        }
//
//        // Index
//        if ( client.getIndex( ) != index_0 ) {
//            index_0 = client.getIndex( );
//            index_timestamp.add( new MyTimeStampObject( Instant.now( ), index_0 ) );
//        }
//
//        // Index bid
//        if ( client.getIndexBid( ) != index_bid_0 ) {
//            index_bid_0 = client.getIndexBid( );
//            index_bid_timestamp.add( new MyTimeStampObject( Instant.now( ), index_bid_0 ) );
//        }
//
//        // Index ask
//        if ( client.getIndexAsk( ) != index_ask_0 ) {
//            index_ask_0 = client.getIndexAsk( );
//            index_ask_timestamp.add( new MyTimeStampObject( Instant.now( ), index_ask_0 ) );
//        }
//
//        // Fut day
//        double fut_day = exps.getExp( ExpStrings.day ).getFuture( );
//
//        if ( fut_day != fut_day_0 ) {
//            fut_day_0 = fut_day;
//            fut_day_timeStamp.add( new MyTimeStampObject( Instant.now( ), fut_day_0 ) );
//        }
//
//        // Fut week
//        double fut_week = exps.getExp( ExpStrings.week ).getFuture( );
//
//        if ( fut_week != fut_week_0 ) {
//            fut_week_0 = fut_week;
//            fut_week_timeStamp.add( new MyTimeStampObject( Instant.now( ), fut_week_0 ) );
//        }
//
//        // Fut month
//        double fut_month = exps.getExp( ExpStrings.month ).getFuture( );
//
//        if ( fut_month != fut_month_0 ) {
//            fut_month_0 = fut_month;
//            fut_month_timeStamp.add( new MyTimeStampObject( Instant.now( ), fut_month_0 ) );
//        }
//
//        // Fut e1
//        double fut_e1 = exps.getExp( ExpStrings.e1 ).getFuture( );
//
//        if ( fut_e1 != fut_e1_0 ) {
//            fut_e1_0 = fut_e1;
//            fut_e1_timeStamp.add( new MyTimeStampObject( Instant.now( ), fut_e1_0 ) );
//        }
//
//        // Fut e2
//        double fut_e2 = exps.getExp( ExpStrings.e2 ).getFuture( );
//
//        if ( fut_e2 != fut_e2_0 ) {
//            fut_e2_0 = fut_e2;
//            fut_e2_timeStamp.add( new MyTimeStampObject( Instant.now( ), fut_e2_0 ) );
//        }
//
//        // Ind bid ask counter
//        int ind_bid_ask_counter = client.getIndexBidAskCounter( );
//
//        if ( ind_bid_ask_counter != index_bid_ask_counter_0 ) {
//
//            double last_count = ind_bid_ask_counter - index_bid_ask_counter_0;
//            index_bid_ask_counter_0 = ind_bid_ask_counter;
//            ind_bid_ask_counter_timestamp.add( new MyTimeStampObject( Instant.now( ), last_count ) );
//        }
//
//        // Races ind counter
//        if ( client.getIndexSum() != ind_counter_0 ) {
//            ind_counter_0 = client.getIndexSum();
//            ind_counter_timestamp.add( new MyTimeStampObject( Instant.now(), ind_counter_0 ) );
//        }
//
//        // Op avg day
//        if ( sleep_count % 1000 == 0 ) {
//            try {
//                double op_avg_15_day = exps.getExp( ExpStrings.day ).getOpAvgFut( 900 );
//                op_avg_fut_day_15_timestamp.add( new MyTimeStampObject( Instant.now( ), op_avg_15_day ) );
//
//                double op_avg_day = exps.getExp( ExpStrings.day ).getOpAvgFut( );
//                op_avg_fut_day_timestamp.add( new MyTimeStampObject( Instant.now( ), op_avg_day ) );
//
//            } catch ( Exception e ) {
//                e.printStackTrace( );
//            }
//        }
//
//        // Update count
//        sleep_count += sleep;
    }

    @Override
    public void loadData() {
//        loadSerieData( DATA_SCHEME, "snp500_index", client.getIndexSeries( ) );
//        loadSerieData( DATA_SCHEME, "snp500_index_bid", client.getIndexBidSeries( ) );
//        loadSerieData( DATA_SCHEME, "snp500_index_ask", client.getIndexAskSeries( ) );
//        loadSerieData( DATA_SCHEME, "snp500_index_bid_ask_counter", client.getIndexBidAskCounterSeries( ) );
//        loadSerieData( SAGIV_SCHEME, "snp500_op_avg_day", client.getExps( ).getExp( ExpStrings.day ).getOpAvgFutSeries( ) );
//        loadSerieData( SAGIV_SCHEME, "snp500_op_avg_15_day", client.getExps( ).getExp( ExpStrings.day ).getOpAvg15FutSeries( ) );
//        loadSerieData( SAGIV_SCHEME, "snp500_index_counter", client.getIndCounterSeries() );
    }

    private void loadSerieData( String scheme, String table, MyTimeSeries timeSeries ) {
        String query = String.format( "SELECT * FROM %s.%s WHERE time::date = now()::date;", scheme, table );
        ResultSet rs = MySql.select( query );
        while ( true ) {
            try {
                if ( !rs.next( ) ) break;
                Timestamp timestamp = rs.getTimestamp( 1 );
                double value = rs.getDouble( 2 );
                timeSeries.add( timestamp.toLocalDateTime( ), value );
            } catch ( SQLException throwables ) {
                throwables.printStackTrace( );
            }
        }
    }
    
    private void insertListRetro( ArrayList< MyTimeStampObject > list, String scheme, String tableName ) {
        if ( list.size( ) > 0 ) {

            // Create the query
            StringBuilder queryBuiler = new StringBuilder( "INSERT INTO %s.%s (time, value) VALUES " );
            int last_item_id = list.get( list.size( ) - 1 ).hashCode( );
            for ( MyTimeStampObject row : list ) {
                queryBuiler.append( String.format( "(cast('%s' as timestamp with time zone), %s)", row.getInstant( ), row.getValue( ) ) );
                if ( row.hashCode( ) != last_item_id ) {
                    queryBuiler.append( "," );
                }
            }
            queryBuiler.append( ";" );

            String q = String.format( queryBuiler.toString( ), scheme, tableName );

            // Insert
            MySql.insert( q, true );

            // Clear the list
            list.clear( );
        }
    }

    private void updateListsRetro() {
        insertListRetro( index_timestamp, DATA_SCHEME, "snp500_index" );
        insertListRetro( index_bid_timestamp, DATA_SCHEME, "snp500_index_bid" );
        insertListRetro( index_ask_timestamp, DATA_SCHEME, "snp500_index_ask" );
        insertListRetro( fut_day_timeStamp, DATA_SCHEME, "snp500_fut_day" );
        insertListRetro( fut_week_timeStamp, DATA_SCHEME, "snp500_fut_week" );
        insertListRetro( fut_month_timeStamp, DATA_SCHEME, "snp500_fut_month" );
        insertListRetro( fut_e1_timeStamp, DATA_SCHEME, "snp500_fut_e1" );
        insertListRetro( fut_e2_timeStamp, DATA_SCHEME, "snp500_fut_e2" );
        insertListRetro( ind_bid_ask_counter_timestamp, DATA_SCHEME, "snp500_index_bid_ask_counter" );
        insertListRetro( op_avg_fut_day_timestamp, SAGIV_SCHEME, "snp500_op_avg_day" );
        insertListRetro( op_avg_fut_day_15_timestamp, SAGIV_SCHEME, "snp500_op_avg_15_day" );
        insertListRetro( ind_counter_timestamp, SAGIV_SCHEME, "snp500_index_counter" );
    }
}