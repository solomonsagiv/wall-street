package dataBase.mySql.myTables;

import arik.Arik;
import dataBase.mySql.MySql;
import dataBase.mySql.mySqlComps.MyColumnSql;
import dataBase.mySql.mySqlComps.MyLoadAbleColumn;
import dataBase.mySql.mySqlComps.MyTableSql;
import options.Options;
import options.OptionsEnum;
import org.json.JSONObject;
import serverObjects.BASE_CLIENT_OBJECT;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;

public abstract class MyStatusTable extends MyTableSql {

    // Constructor
    public MyStatusTable( BASE_CLIENT_OBJECT client, String tableName ) {
        super( client, tableName );
    }

    @Override
    public void initColumns() {}

    @Override
    public void insert() {}

    @Override
    public void load() {
        try {

            String query = String.format( "SELECT * FROM stocks.%s WHERE id ='%S'", tableName, client.getDbId( ) );

            ResultSet rs = MySql.select( query );

            while ( rs.next( ) ) {

                for ( MyLoadAbleColumn column : loadAbleColumns ) {
                    switch ( column.type ) {
                        case MyColumnSql.DOUBLE:
                            double d = rs.getDouble( column.name );
                            column.setLoadedObject( d );
                            break;
                        case MyColumnSql.INT:
                            int i = rs.getInt( column.name );
                            column.setLoadedObject( i );
                            break;
                        case MyColumnSql.STRING:
                            String s = rs.getString( column.name );
                            System.out.println(s );
                            column.setLoadedObject( s );
                            break;
                        default:
                            break;
                    }
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

        String endQuery = String.format( " WHERE `id`='%s';", client.getDbId( ) );

        query.append( endQuery );

        System.out.println( query );

        MySql.update( query.toString( ) );
    }

    @Override
    public MyTableSql getObject() {
        return null;
    }
}
