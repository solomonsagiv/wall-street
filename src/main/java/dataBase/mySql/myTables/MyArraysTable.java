package dataBase.mySql.myTables;

import arik.Arik;
import dataBase.mySql.MySql;
import dataBase.mySql.mySqlComps.MyLoadAbleColumn;
import dataBase.mySql.mySqlComps.MyTableSql;
import org.json.JSONArray;
import serverObjects.BASE_CLIENT_OBJECT;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class MyArraysTable extends MyTableSql {

    public MyArraysTable( BASE_CLIENT_OBJECT client, String tableName ) {
        super( client, tableName );
    }

    @Override
    public void initColumns() {

    }

    @Override
    public void insert() {
    }

    @Override
    public void load() {
        try {
            String query = String.format( "SELECT * FROM stocks.%s WHERE id ='%S'", tableName, client.getDbId( ) );

            System.out.println( query );
            ResultSet rs = MySql.select( query );

            while ( rs.next( ) ) {
                for ( MyLoadAbleColumn column : loadAbleColumns ) {
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
        super.updateFromSuper( );
    }

    @Override
    public void reset() {
        StringBuilder query = new StringBuilder( String.format( "UPDATE `stocks`.`%s` SET ", tableName ) );

        int i = 0;

        for ( MyLoadAbleColumn column : loadAbleColumns ) {
            if ( i < loadAbleColumns.size( ) - 1 ) {
                query.append( "`" + column.name + "`='" + column.getResetObject( ) + "'," );
            } else {
                query.append( "`" + column.name + "`='" + column.getResetObject( ) + "'" );
            }
            i++;
        }

        String endQuery = String.format( "WHERE `id`='%s';", client.getDbId( ) );

        query.append( endQuery );

        MySql.update( query.toString( ) );
    }

    @Override
    public MyTableSql getObject() {
        return null;
    }


    // Convert json array to arrayList<Double>
    public void convertJsonArrayToDoubleArray( JSONArray jsonArray, ArrayList< Double > list ) {

        for ( int i = 0; i < jsonArray.length( ); i++ ) {

            list.add( jsonArray.getDouble( i ) );

        }

    }

}
