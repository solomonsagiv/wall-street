package dataBase.mySql.tables;

import arik.Arik;
import dataBase.mySql.MySql;
import dataBase.mySql.mySqlComps.MyColumnSql;
import dataBase.mySql.mySqlComps.MyLoadAbleColumn;
import dataBase.mySql.mySqlComps.MyTableSql;
import org.json.JSONArray;
import serverObjects.BASE_CLIENT_OBJECT;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;

public class MyArraysTable extends MyTableSql {

    private MyColumnSql< String > name;
    private MyColumnSql< String > time;
    private MyLoadAbleColumn< String > indexlist;
    private MyLoadAbleColumn< String > opList;
    private MyLoadAbleColumn< String > equalMoveList;
    private MyLoadAbleColumn< String > opAvgMoveList;

    public MyArraysTable( BASE_CLIENT_OBJECT client, String tableName ) {
        super( client, tableName );
    }

    @Override
    public void initColumns() {
        name = new MyColumnSql< String >( this, "name", MyColumnSql.STRING ) {
            @Override
            public String getObject() {
                return client.getName( );
            }
        };

        time = new MyColumnSql< String >( this, "time", MyColumnSql.STRING ) {
            @Override
            public String getObject() {
                return LocalTime.now( ).toString( );
            }
        };

        indexlist = new MyLoadAbleColumn<>( this, "indexlist", MyColumnSql.STRING ) {
            @Override
            public String getObject() {
                return client.getIndexList( ).toString( );
            }

            @Override
            public void setLoadedObject( String object ) {
                convertJsonArrayToDoubleArray( new JSONArray( object ), ( ArrayList< Double > ) client.getIndexList( ) );
            }

            @Override
            public String getResetObject() {
                return new JSONArray( ).toString( );
            }
        };

        opList = new MyLoadAbleColumn<>( this, "opList", MyColumnSql.STRING ) {
            @Override
            public String getObject() {
                return client.getOptionsHandler( ).getMainOptions( ).getOpList( ).toString( );
            }

            @Override
            public void setLoadedObject( String object ) {
                convertJsonArrayToDoubleArray( new JSONArray( object ), ( ArrayList< Double > ) client.getOptionsHandler( ).getMainOptions( ).getOpList( ) );
            }

            @Override
            public String getResetObject() {
                return new JSONArray( ).toString( );
            }
        };

        equalMoveList = new MyLoadAbleColumn<>( this, "equalMoveList", MyColumnSql.STRING ) {
            @Override
            public String getObject() {
                return client.getOptionsHandler( ).getMainOptions( ).getEqualMoveService( ).getMoveList( ).toString( );
            }

            @Override
            public void setLoadedObject( String object ) {
                convertJsonArrayToDoubleArray( new JSONArray( object ), ( ArrayList< Double > ) client.getOptionsHandler( ).getMainOptions( ).getEqualMoveService( ).getMoveList( ) );
            }

            @Override
            public String getResetObject() {
                return new JSONArray( ).toString( );
            }
        };

        opAvgMoveList = new MyLoadAbleColumn<>( this, "opAvgMoveList", MyColumnSql.STRING ) {
            @Override
            public String getObject() {
                return client.getOptionsHandler( ).getMainOptions( ).getOpAvgMoveService( ).getMoveList( ).toString( );
            }

            @Override
            public void setLoadedObject( String object ) {
                convertJsonArrayToDoubleArray( new JSONArray( object ), ( ArrayList< Double > ) client.getOptionsHandler( ).getMainOptions( ).getOpAvgMoveService( ).getMoveList( ) );
            }

            @Override
            public String getResetObject() {
                return new JSONArray( ).toString( );
            }
        };

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
