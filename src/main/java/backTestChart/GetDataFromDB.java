package backTestChart;

import dataBase.mySql.MySql;
import myJson.MyJson;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GetDataFromDB {

    public static void main( String[] args ) {

        String query = "select * from jsonTables.spxJsonDay where date = '2020-10-26';";

        GetDataFromDB getDataFromDB = new GetDataFromDB( query );
        getDataFromDB.getDataFromDb( );

    }

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

    public Map< String, ArrayList< Double > > getDataFromDb() {

        ResultSet rs = MySql.select( query );
        Map< String, ArrayList< Double > > map = new HashMap<>( );

        timeList = new ArrayList<>( );

        ArrayList< Double > indList = new ArrayList<>( );
        ArrayList< Double > indBidList = new ArrayList<>( );
        ArrayList< Double > indAskList = new ArrayList<>( );
        ArrayList< Double > futList = new ArrayList<>( );

        map.put( "index", indList );
        map.put( "bid", indBidList );
        map.put( "ask", indAskList );
        map.put( "fut", futList );

        while ( true ) {
            try {
                if ( !rs.next( ) ) break;

                MyJson json = new MyJson( rs.getString( "data" ) );

                double fut = json.getMyJson( "exps" ).getMyJson( "e1" ).getDouble( "fut" );
                indList.add( json.getDouble( "ind" ) );
                indBidList.add( json.getDouble( "indBid" ) );
                indAskList.add( json.getDouble( "indAsk" ) );
                futList.add( fut );
                timeList.add( LocalTime.parse( rs.getString( "time" ) ) );

            } catch ( SQLException throwables ) {
                throwables.printStackTrace( );
            }
        }
        return map;
    }

}
