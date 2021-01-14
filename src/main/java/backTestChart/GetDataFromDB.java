package backTestChart;

import dataBase.mySql.MySql;
import myJson.MyJson;
import options.JsonStrings;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GetDataFromDB {

    // Variables
    String query;

    // Constructor
    public GetDataFromDB( String query ) {
        this.query = query;
    }

    ArrayList< LocalTime > timeList;

    public ArrayList< LocalTime > getTimeList() {
        return timeList;
    }

    public Map< String, ArrayList< Double > > getDataFromDb( String[] cols ) {

        ResultSet rs = MySql.select( query );
        Map< String, ArrayList< Double > > map = new HashMap<>( );
        timeList = new ArrayList<>( );

        for ( int i = 0; i < cols.length; i++ ) {
            map.put( cols[ i ], new ArrayList<>( ) );
        }

        while ( true ) {
            try {
                if ( !rs.next( ) ) break;

                MyJson json = new MyJson( rs.getString( "data" ) );

                for ( Map.Entry< String, ArrayList< Double > > entry : map.entrySet( ) ) {

                    ArrayList< Double > list = entry.getValue( );

                    switch ( entry.getKey( ) ) {
                        case JsonStrings.e1Fut:
                            double fut = json.getMyJson( "exps" ).getMyJson( "e1" ).getDouble( "fut" );
                            list.add( fut );
                            break;
                        case JsonStrings.e1Delta:
                            double delta = json.getMyJson( "exps" ).getMyJson( "e1" ).getDouble( "delta" );
                            list.add( delta );
                            break;
                        case JsonStrings.indBidAskCounter:
                            double indBidAskCounter = json.getInt( "indBidAskCounter" );
                            list.add( indBidAskCounter );
                            break;
                        case JsonStrings.ind:
                            list.add( json.getDouble( "ind" ) );
                            break;
                        case JsonStrings.indBid:
                            list.add( json.getDouble( "indBid" ) );
                            break;
                        case JsonStrings.indAsk:
                            list.add( json.getDouble( "indAsk" ) );
                        default:
                            break;
                    }
                }

                // Time
                timeList.add( LocalTime.parse( rs.getString( "time" ) ) );

            } catch ( SQLException throwables ) {
                throwables.printStackTrace( );
            }
        }
        return map;
    }

}