package dataBase.mySql.myBaseTables;

import arik.Arik;
import dataBase.mySql.MySql;
import dataBase.mySql.mySqlComps.MyLoadAbleColumn;
import dataBase.mySql.mySqlComps.MySqlColumnEnum;
import dataBase.mySql.mySqlComps.MySqlTable;
import org.json.JSONArray;
import serverObjects.BASE_CLIENT_OBJECT;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public abstract class MyArraysTable extends MySqlTable {

    public MyArraysTable( BASE_CLIENT_OBJECT client ) {
        super( client );
    }

    @Override
    public String getName() {
        return client.getName() + "_arrays";
    }

    @Override
    public void insert() {
        super.insert();
    }

    @Override
    public void load() {
        try {
            String query = String.format( "SELECT * FROM stocks.%s WHERE id ='%S'", getName(), client.getDbId( ) );

            System.out.println( query );
            ResultSet rs = MySql.select( query );

            while ( rs.next( ) ) {
                for (Map.Entry<MySqlColumnEnum, MyLoadAbleColumn> entry : loadAbleColumns.entrySet()) {
                    MyLoadAbleColumn column = entry.getValue();
                    String list = rs.getString( column.name );
                    column.setLoadedObject( list );
                }
            }

        } catch ( SQLException e ) {
            e.printStackTrace( );
            Arik.getInstance( ).sendErrorMessage( e );
        }
    }

    @Override
    public void update() {
//        super.update();
    }



//    @Override
//    public void reset() {
//        StringBuilder query = new StringBuilder( String.format( "UPDATE `stocks`.`%s` SET ", getName() ) );
//
//        int i = 0;
//
//        for (Map.Entry<MySqlColumnEnum, MyLoadAbleColumn> entry : loadAbleColumns.entrySet()) {
//            MyLoadAbleColumn column = entry.getValue();
//            if ( i < loadAbleColumns.size( ) - 1 ) {
//                query.append( "`" + column.name + "`='" + column.getResetObject( ) + "'," );
//            } else {
//                query.append( "`" + column.name + "`='" + column.getResetObject( ) + "'" );
//            }
//            i++;
//        }
//
//        String endQuery = String.format( "WHERE `id`='%s';", client.getDbId( ) );
//
//        query.append( endQuery );
//
//        MySql.update( query.toString( ) );
//    }


    @Override
    public void reset() {
//
//        MySql.
//
//        super.reset();
    }

    // Convert json array to arrayList<Double>
    public void convertJsonArrayToDoubleArray( JSONArray jsonArray, ArrayList< Double > list ) {

        for ( int i = 0; i < jsonArray.length( ); i++ ) {

            list.add( jsonArray.getDouble( i ) );

        }

    }

}
