package dataBase.mySql.tables;

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

public class MyStatusTable extends MyTableSql {

    private MyColumnSql< String > name;
    private MyColumnSql< String > time;
    private MyColumnSql< Double > ind;
    private MyLoadAbleColumn< Integer > conUp;
    private MyLoadAbleColumn< Integer > conDown;
    private MyLoadAbleColumn< Integer > indUp;
    private MyLoadAbleColumn< Integer > indDown;
    private MyColumnSql< Double > base;
    private MyColumnSql< Double > open;
    private MyColumnSql< Double > high;
    private MyColumnSql< Double > low;
    private MyLoadAbleColumn< String > options;

    public MyStatusTable( BASE_CLIENT_OBJECT client, String tableName ) {
        super( client, tableName );
    }

    @Override
    public void initColumns() {
        name = new MyColumnSql<>( this, "name", MyColumnSql.STRING ) {
            @Override
            public String getObject() {
                return client.getName( );
            }
        };

        time = new MyColumnSql<>( this, "time", MyColumnSql.STRING ) {
            @Override
            public String getObject() {
                return LocalTime.now( ).toString( );
            }
        };

        ind = new MyColumnSql<>( this, "ind", MyColumnSql.DOUBLE ) {
            @Override
            public Double getObject() {
                return client.getIndex( );
            }
        };

        conUp = new MyLoadAbleColumn<>( this, "conUp", MyColumnSql.INT ) {
            @Override
            public Integer getObject() {
                return client.getConUp( );
            }

            @Override
            public void setLoadedObject( Integer object ) {
                client.setConUp( object );
            }

            @Override
            public Integer getResetObject() {
                return 0;
            }

        };

        conDown = new MyLoadAbleColumn<>( this, "conDown", MyColumnSql.INT ) {
            @Override
            public Integer getObject() {
                return client.getConDown( );
            }

            @Override
            public void setLoadedObject( Integer object ) {
                client.setConDown( object );
            }

            @Override
            public Integer getResetObject() {
                return 0;
            }

        };

        indUp = new MyLoadAbleColumn<>( this, "indUp", MyColumnSql.INT ) {
            @Override
            public Integer getObject() {
                return client.getIndexUp( );
            }

            @Override
            public void setLoadedObject( Integer object ) {
                client.setIndexUp( object );
            }

            @Override
            public Integer getResetObject() {
                return 0;
            }

        };

        indDown = new MyLoadAbleColumn<>( this, "indDown", MyColumnSql.INT ) {
            @Override
            public Integer getObject() {
                return client.getIndexDown( );
            }

            @Override
            public void setLoadedObject( Integer object ) {
                client.setIndexDown( object );
            }

            @Override
            public Integer getResetObject() {
                return 0;
            }

        };

        base = new MyColumnSql< Double >( this, "base", MyColumnSql.DOUBLE ) {
            @Override
            public Double getObject() {
                return client.getBase( );
            }
        };

        open = new MyColumnSql<>( this, "open", MyColumnSql.DOUBLE ) {
            @Override
            public Double getObject() {
                return client.getOpen( );
            }
        };

        high = new MyColumnSql<>( this, "high", MyColumnSql.DOUBLE ) {
            @Override
            public Double getObject() {
                return client.getHigh( );
            }
        };

        low = new MyColumnSql<>( this, "low", MyColumnSql.DOUBLE ) {
            @Override
            public Double getObject() {
                return client.getLow( );
            }
        };

        options = new MyLoadAbleColumn<>( this, "options", MyColumnSql.STRING ) {
            @Override
            public String getObject() {
                return client.getOptionsHandler( ).getAllOptionsAsJson( ).toString( );
            }

            @Override
            public void setLoadedObject( String object ) {
                JSONObject optionsData = new JSONObject( object );
                for ( Options options : client.getOptionsHandler( ).getOptionsList( ) ) {
                    try {
                        options.setDataFromJson( optionsData.getJSONObject( options.getType( ).toString() ) );
                    } catch ( Exception e ) {
                        e.printStackTrace( );
                    }
                }
            }

            @Override
            public String getResetObject() {
                return client.getOptionsHandler( ).getAllOptionsEmptyJson( ).toString( );
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
