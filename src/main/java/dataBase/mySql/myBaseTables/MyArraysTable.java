package dataBase.mySql.myBaseTables;

import arik.Arik;
import dataBase.mySql.MySql;
import dataBase.mySql.mySqlComps.MyLoadAbleColumn;
import dataBase.mySql.mySqlComps.MySqlColumnEnum;
import dataBase.mySql.mySqlComps.MySqlDataTypeEnum;
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
            String query = String.format( "SELECT * FROM stocks.%s;", getName() );

            ResultSet rs = MySql.select( query );

            while ( rs.next( ) ) {
                for (Map.Entry<MySqlColumnEnum, MyLoadAbleColumn> entry : loadAbleColumns.entrySet()) {
                    MyLoadAbleColumn column = entry.getValue();

                    if ( column.getType( ).getDataType( ) == MySqlDataTypeEnum.DOUBLE ) {
                        double d = rs.getDouble( column.name );
                        column.setLoadedObject( d );
                        continue;
                    }

                    if ( column.getType( ).getDataType( ) == MySqlDataTypeEnum.STRING ) {
                        String s = rs.getString( column.name );
                        column.setLoadedObject( s );
                        continue;
                    }

                }
            }

            setLoad(true);

        } catch ( SQLException e ) {
            e.printStackTrace( );
            Arik.getInstance( ).sendErrorMessage( e );
        }
    }

    @Override
    public void update() {}

    @Override
    public void reset() {
        try {
            MySql.trunticate( getName() );
        } catch ( Exception e ) {
            e.printStackTrace();
            Arik.getInstance( ).sendErrorMessage( e );
        }
    }

    // Convert json array to arrayList<Double>
    public void convertJsonArrayToDoubleArray( JSONArray jsonArray, ArrayList< Double > list ) {

        for ( int i = 0; i < jsonArray.length( ); i++ ) {

            list.add( jsonArray.getDouble( i ) );

        }

    }

}
