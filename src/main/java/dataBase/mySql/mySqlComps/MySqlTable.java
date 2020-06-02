package dataBase.mySql.mySqlComps;

import arik.Arik;
import dataBase.mySql.MySql;
import serverObjects.BASE_CLIENT_OBJECT;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class MySqlTable implements IMyTableSql {

    // Variables
    protected BASE_CLIENT_OBJECT client;
    private String name;

    protected Map< MySqlColumnEnum, MyColumnSql > columns = new HashMap<>( );
    protected Map< MySqlColumnEnum, MyLoadAbleColumn > loadAbleColumns = new HashMap<>( );

    protected boolean isLoad = false;

    // Constructor
    public MySqlTable( BASE_CLIENT_OBJECT client ) {
        this.client = client;
        initColumns( );
    }

    public void updateColumn( MySqlColumnEnum columnEnum, MyColumnSql myColumnSql ) {
        columns.put( columnEnum, myColumnSql );

        if ( myColumnSql instanceof MyLoadAbleColumn ) {
            loadAbleColumns.put( columnEnum, ( MyLoadAbleColumn ) myColumnSql );
        }
    }

    // Constructor
    public MySqlTable( BASE_CLIENT_OBJECT client, String name ) {
        this( client );
        this.name = name;
    }

    protected void addColumn( MyColumnSql column ) {

        // Is loadable
        if ( column instanceof MyLoadAbleColumn ) {
            loadAbleColumns.put( column.getType( ), ( MyLoadAbleColumn ) column );
        }

        columns.put( column.getType( ), column );

    }

    protected void updateSpecificCols( ArrayList< MyColumnSql > columns ) {
        StringBuilder query = new StringBuilder( String.format( "UPDATE `stocks`.`%s` SET ", getName( ) ) );

        int i = 0;

        for ( MyColumnSql column : columns ) {
            try {
                if ( i < columns.size( ) - 1 ) {
                    query.append( "`" + column.name + "`='" + column.getObject( ) + "'," );
                } else {
                    query.append( "`" + column.name + "`='" + column.getObject( ) + "'" );
                }
            } catch ( Exception e ) {
                e.printStackTrace( );
            }
            i++;
        }

        String endQuery = String.format( "WHERE `id`='%s';", client.getDbId( ) );

        query.append( endQuery );

        MySql.update( query.toString( ) );
    }

    @Override
    public void insert() {

        String query = String.format( "INSERT INTO `stocks`.`%s` ", getName( ) );
        StringBuilder insertQuery = new StringBuilder( query );
        StringBuilder insertColumns = new StringBuilder( );

        String values = " VALUES ";
        StringBuilder valuesColumns = new StringBuilder( );

        int i = 0;

        for ( Map.Entry< MySqlColumnEnum, MyColumnSql > entry : columns.entrySet( ) ) {
            try {
                MyColumnSql column = entry.getValue( );

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
            } catch ( Exception e ) {
                e.printStackTrace( );
            }
            i++;
        }

        String columns = "(" + insertColumns + ")";
        String vColumns = "(" + valuesColumns + ")";

        insertQuery.append( columns ).append( values ).append( vColumns );

        // Insert
        MySql.insert( insertQuery.toString( ) );

    }

    @Override
    public void update() {

        StringBuilder query = new StringBuilder( String.format( "UPDATE `stocks`.`%s` SET ", getName( ) ) );

        int i = 0;

        for ( Map.Entry< MySqlColumnEnum, MyColumnSql > entry : columns.entrySet( ) ) {
            try {
                MyColumnSql column = entry.getValue( );

                if ( i < columns.size( ) - 1 ) {
                    query.append( "`" + column.name + "`='" + column.getObject( ) + "'," );
                } else {
                    query.append( "`" + column.name + "`='" + column.getObject( ) + "'" );
                }
            } catch ( Exception e ) {
                e.printStackTrace( );
            }
            i++;
        }

        String endQuery = String.format( "WHERE `id`='%s';", client.getDbId( ) );

        query.append( endQuery );

        MySql.update( query.toString( ) );

    }

    @Override
    public void load() {
        try {

            String query = String.format( "SELECT * FROM stocks.%s WHERE id ='%S'", getName( ), client.getDbId( ) );

            ResultSet rs = MySql.select( query );

            while ( rs.next( ) ) {

                for ( Map.Entry< MySqlColumnEnum, MyLoadAbleColumn > entry : loadAbleColumns.entrySet( ) ) {
                    MyLoadAbleColumn column = entry.getValue( );

                    if ( column.getType( ).getDataType( ) == MySqlDataTypeEnum.DOUBLE ) {
                        double d = rs.getDouble( column.name );
                        column.setLoadedObject( d );
                        continue;
                    }

                    if ( column.getType( ).getDataType( ) == MySqlDataTypeEnum.INT ) {
                        int i = rs.getInt( column.name );
                        column.setLoadedObject( i );
                        continue;
                    }

                    if ( column.getType( ).getDataType( ) == MySqlDataTypeEnum.STRING ) {
                        String s = rs.getString( column.name );
                        column.setLoadedObject( s );
                        continue;
                    }
                }
            }

        } catch ( SQLException e ) {
            e.printStackTrace( );
            Arik.getInstance( ).sendErrorMessage( e );
        }
    }

    public void loadAll() {
        try {

            String query = String.format( "SELECT * FROM stocks.%s", getName( ), client.getDbId( ) );

            ResultSet rs = MySql.select( query );

            while ( rs.next( ) ) {

                for ( Map.Entry< MySqlColumnEnum, MyLoadAbleColumn > entry : loadAbleColumns.entrySet( ) ) {
                    MyLoadAbleColumn column = entry.getValue( );

                    if ( column.getType( ).getDataType( ) == MySqlDataTypeEnum.DOUBLE ) {
                        double d = rs.getDouble( column.name );
                        column.setLoadedObject( d );
                        continue;
                    }

                    if ( column.getType( ).getDataType( ) == MySqlDataTypeEnum.INT ) {
                        int i = rs.getInt( column.name );
                        column.setLoadedObject( i );
                        continue;
                    }

                    if ( column.getType( ).getDataType( ) == MySqlDataTypeEnum.STRING ) {
                        String s = rs.getString( column.name );
                        column.setLoadedObject( s );
                        continue;
                    }
                }
            }

        } catch ( SQLException e ) {
            e.printStackTrace( );
            Arik.getInstance( ).sendErrorMessage( e );
        }
    }

    @Override
    public void reset() {
        StringBuilder query = new StringBuilder( String.format( "UPDATE `stocks`.`%s` SET ", getName( ) ) );
        int i = 0;

        for ( Map.Entry< MySqlColumnEnum, MyLoadAbleColumn > entry : loadAbleColumns.entrySet( ) ) {
            MyLoadAbleColumn column = entry.getValue( );
            if ( i < loadAbleColumns.size( ) - 1 ) {
                query.append( "`" + column.name + "`='" + column.getResetObject( ) + "'," );
            } else {
                query.append( "`" + column.name + "`='" + column.getResetObject( ) + "'" );
            }
            i++;
        }

        String endQuery = String.format( " WHERE `id`='%s';", client.getDbId( ) );

        query.append( endQuery );

        MySql.update( query.toString( ) );
    }

    public BASE_CLIENT_OBJECT getClient() {
        return client;
    }


    public String getName() {
        if ( this.name == null ) {
            return client.getName( );
        }
        return name;
    }

    public boolean isLoad() {
        return isLoad;
    }

    public void setLoad(boolean load) {
        isLoad = load;
    }

    public void setName(String name ) {
        this.name = name;
    }
}
