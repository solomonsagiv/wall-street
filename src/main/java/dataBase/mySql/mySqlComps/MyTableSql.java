package dataBase.mySql.mySqlComps;

import dataBase.mySql.MySql;
import serverObjects.BASE_CLIENT_OBJECT;

import java.util.HashSet;
import java.util.Set;

public abstract class MyTableSql implements IMyTableSql {

    // Variables
    public String tableName;
    public Set< MyColumnSql > columns = new HashSet<>( );
    public Set< MyLoadAbleColumn > loadAbleColumns = new HashSet<>( );
    protected BASE_CLIENT_OBJECT client;

    // Constructor
    public MyTableSql( BASE_CLIENT_OBJECT client, String tableName ) {
        this.client = client;
        this.tableName = tableName;
        initColumns( );
    }

    public void addColumn( MyColumnSql columnSql ) {
        columns.add( columnSql );
    }

    public void addLoadAbleColumn( MyColumnSql columnSql ) {
        if ( columnSql instanceof MyLoadAbleColumn ) {
            loadAbleColumns.add( ( MyLoadAbleColumn ) columnSql );
        }
    }


    protected void insertFromSuper() {

        String query = String.format( "INSERT INTO `stocks`.`%s` ", tableName );
        StringBuilder insertQuery = new StringBuilder( query );
        StringBuilder insertColumns = new StringBuilder( );

        String values = " VALUES ";
        StringBuilder valuesColumns = new StringBuilder( );

        int i = 0;

        for ( MyColumnSql column : columns ) {
            if ( i < columns.size( ) - 1 ) {
                // Columns
                insertColumns.append( "`" + column.name + "`," );
                // Values
                valuesColumns.append( "'" + column.getObject( ) + "'," );
            } else {
                // Columns
                insertColumns.append( "`" + column.name + "`" );
                // Values
                valuesColumns.append( "'" + column.getObject( ) + "'" );
            }
            i++;
        }

        String columns = "(" + insertColumns + ")";
        String vColumns = "(" + valuesColumns + ")";

        insertQuery.append( columns ).append( values ).append( vColumns );

        // Insert
        MySql.insert( insertQuery.toString( ) );

    }


    protected void updateFromSuper() {

        StringBuilder query = new StringBuilder( String.format( "UPDATE `stocks`.`%s` SET ", tableName ) );

        int i = 0;

        for ( MyColumnSql column : columns ) {
            if ( i < columns.size( ) - 1 ) {
                query.append( "`" + column.name + "`='" + column.getObject( ) + "'," );
            } else {
                query.append( "`" + column.name + "`='" + column.getObject( ) + "'" );
            }
            i++;
        }

        String endQuery = String.format( "WHERE `id`='%s';", client.getDbId( ) );

        query.append( endQuery );

        MySql.update( query.toString( ) );

    }

}
