package dataBase.mySql.tables;

import api.Manifest;
import dataBase.mySql.ConnectionPool;
import dataBase.mySql.MySql;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.SpxCLIENTObject;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class DayTable extends BaseTable {

    // ---------- Variables ---------- //

    // Columns
    private TableComps.MyTableColumn id;
    private TableComps.MyTableColumn date;
    private TableComps.MyTableColumn exp_name;
    private TableComps.MyTableColumn time;
    private TableComps.MyTableColumn con;
    private TableComps.MyTableColumn conDay;
    private TableComps.MyTableColumn conMonth;
    private TableComps.MyTableColumn conQuarter;
    private TableComps.MyTableColumn ind;
    private TableComps.MyTableColumn con_up;
    private TableComps.MyTableColumn con_down;
    private TableComps.MyTableColumn index_up;
    private TableComps.MyTableColumn index_down;
    private TableComps.MyTableColumn options;
    private TableComps.MyTableColumn base;
    private TableComps.MyTableColumn opAvg;

    // ---------- Constructor ---------- //
    public DayTable( BASE_CLIENT_OBJECT client, String tableName ) {
        super( client );
        setName( tableName );

    }

    public static void main( String[] args ) throws SQLException {

        SpxCLIENTObject spx = SpxCLIENTObject.getInstance( );
        System.out.println( spx.getOptionsHandler( ).getMainOptions( ).getContract( ) );

        DayTable dayTable = new DayTable( spx, "spx" );

        ConnectionPool.getConnectionsPoolInstance().getConnection();

        final long startTime = System.currentTimeMillis();
        System.out.println(startTime );

        dayTable.insert( );
        System.out.println( "Inserts success" );

        final long endTime = System.currentTimeMillis();
        System.out.println(endTime );
        System.out.println("Total execution time: " + (endTime - startTime) / 1000);

    }

    @Override
    public void initColumns() {

        // Date
        date = new TableComps.MyTableColumn( this, "date", TableComps.MyTableColumn.STRING ) {
            @Override
            public Object getVal() {
                return LocalDate.now( ).toString( );
            }
        };

        // Exp name
        exp_name = new TableComps.MyTableColumn( this, "exp_name", TableComps.MyTableColumn.STRING ) {
            @Override
            public Object getVal() {
                return Manifest.EXP;
            }
        };

        // Time
        time = new TableComps.MyTableColumn( this, "time", TableComps.MyTableColumn.STRING ) {
            @Override
            public Object getVal() {
                return LocalTime.now( ).toString( );
            }
        };

        // Con
        con = new TableComps.MyTableColumn( this, "con", client.getOptionsHandler( ).getMainOptions( ).getContract( ) ) {
            @Override
            public Object getVal() {
                return getMyDouble( ).getVal( );
            }
        };

        // Con day
        conDay = new TableComps.MyTableColumn( this, "conDay", client.getOptionsHandler( ).getOptionsDay( ).getContract( ) ) {
            @Override
            public Object getVal() {
                return getMyDouble( ).getVal( );
            }
        };

        // Con month
        conMonth = new TableComps.MyTableColumn( this, "conMonth", client.getOptionsHandler( ).getOptionsMonth( ).getContract( ) ) {
            @Override
            public Object getVal() {
                return getMyDouble( ).getVal( );
            }
        };

        // Con quarter
        conQuarter = new TableComps.MyTableColumn( this, "conQuarter", client.getOptionsHandler( ).getOptionsQuarter( ).getContract( ) ) {
            @Override
            public Object getVal() {
                return getMyDouble( ).getVal( );
            }
        };

        // Index
        ind = new TableComps.MyTableColumn( this, "ind", TableComps.MyTableColumn.DOUBLE ) {
            @Override
            public Object getVal() {
                return client.getIndex( );
            }
        };

        // ConUp
        con_up = new TableComps.MyTableColumn( this, "con_up", TableComps.MyTableColumn.INT ) {
            @Override
            public Object getVal() {
                return client.getConUp( );
            }
        };

        // ConDown
        con_down = new TableComps.MyTableColumn( this, "con_down", TableComps.MyTableColumn.INT ) {
            @Override
            public Object getVal() {
                return client.getConDown( );
            }
        };

        // IndexUp
        index_up = new TableComps.MyTableColumn( this, "index_up", TableComps.MyTableColumn.INT ) {
            @Override
            public Object getVal() {
                return client.getIndexUp( );
            }
        };

        // IndexDown
        index_down = new TableComps.MyTableColumn( this, "index_down", TableComps.MyTableColumn.INT ) {
            @Override
            public Object getVal() {
                return client.getIndexDown( );
            }
        };

        // Options
        options = new TableComps.MyTableColumn( this, "options", TableComps.MyTableColumn.STRING ) {
            @Override
            public Object getVal() {
                return getMyString( ).getVal( );
            }
        };

        // Base
        base = new TableComps.MyTableColumn( this, "base", TableComps.MyTableColumn.DOUBLE ) {
            @Override
            public Object getVal() {
                return client.getBase( );
            }
        };

        // OpAvg
        opAvg = new TableComps.MyTableColumn( this, "op_avg", client.getOptionsHandler( ).getMainOptions( ).getOpAvg( ) ) {
            @Override
            public Object getVal() {
                return getMyDouble( ).getVal( );
            }
        };
    }

    @Override
    public void insert() {
        String query = String.format( "INSERT INTO `stocks`.`%s` ", getName() );
        StringBuilder insertQuery = new StringBuilder( query );
        StringBuilder insertColumns = new StringBuilder();

        String values = " VALUES ";
        StringBuilder valuesColumns = new StringBuilder();

        int i = 0;

        for ( TableComps.MyTableColumn column : getColumns( ) ) {
            if ( i < getColumns().size() - 1 ) {
                // Columns
                insertColumns.append( "`" + column.getName() + "`," );
                // Values
                valuesColumns.append( "'" + column.getVal() + "'," );
            } else {
                // Columns
                insertColumns.append( "`" + column.getName() + "`" );
                // Values
                valuesColumns.append( "'" + column.getVal() + "'" );
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
    public void load() {



    }

    @Override
    public void update() {

    }

    @Override
    public void reset() {

    }

    @Override
    public Object getTableObject() {
        return null;
    }


    // ---------- Methods ---------- //


    // ---------- Getters and Setters ---------- //


}
