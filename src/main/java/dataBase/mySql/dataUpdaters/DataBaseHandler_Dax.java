package dataBase.mySql.dataUpdaters;

import dataBase.mySql.MySql;
import exp.ExpStrings;
import exp.Exps;
import serverObjects.BASE_CLIENT_OBJECT;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;

public class DataBaseHandler_Dax extends IDataBaseHandler {

    ArrayList<MyTimeStampObject> index_timestamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> fut_week_timeStamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> fut_month_timeStamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> fut_e1_timeStamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> fut_e2_timeStamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> ind_counter_timestamp = new ArrayList<>();

    double index_0 = 0;
    double fut_week_0 = 0;
    double fut_month_0 = 0;
    double fut_e1_0 = 0;
    double fut_e2_0 = 0;
    double ind_counter_0;

    Exps exps;

    public DataBaseHandler_Dax(BASE_CLIENT_OBJECT client) {
        super(client);
        exps = client.getExps();
    }

    int sleep_count = 100;

    @Override
    public void insertData(int sleep) {

        // Update lists retro
        if (sleep_count % 10000 == 0) {
            updateListsRetro();
        }

        // Index
        if (client.getIndex() != index_0) {
            index_0 = client.getIndex();
            index_timestamp.add(new MyTimeStampObject(Instant.now(), index_0));
        }

        // Fut week
        double fut_week = exps.getExp(ExpStrings.week).getFuture();

        if (fut_week != fut_week_0) {
            fut_week_0 = fut_week;
            fut_week_timeStamp.add(new MyTimeStampObject(Instant.now(), fut_week_0));
        }

        // Fut month
        double fut_month = exps.getExp(ExpStrings.month).getFuture();

        if (fut_month != fut_month_0) {
            fut_month_0 = fut_month;
            fut_month_timeStamp.add(new MyTimeStampObject(Instant.now(), fut_month_0));
        }

        // Fut e1
        double fut_e1 = exps.getExp(ExpStrings.q1).getFuture();

        if (fut_e1 != fut_e1_0) {
            fut_e1_0 = fut_e1;
            fut_e1_timeStamp.add(new MyTimeStampObject(Instant.now(), fut_e1_0));
        }

        // Fut e2
        double fut_e2 = exps.getExp(ExpStrings.q2).getFuture();

        if (fut_e2 != fut_e2_0) {
            fut_e2_0 = fut_e2;
            fut_e2_timeStamp.add(new MyTimeStampObject(Instant.now(), fut_e2_0));
        }

        // Races ind counter
        double ind_counter = client.getIndexSum();
        if (ind_counter != ind_counter_0) {
            double change = ind_counter - ind_counter_0;
            ind_counter_0 = ind_counter;
            ind_counter_timestamp.add(new MyTimeStampObject(Instant.now(), change));
        }

        // --------------- Raw data --------------- //
        // Fut e1
//        double fut_e1 = exps.getExp( ExpStrings.e1 ).getFuture( );
//
//        if ( fut_e1 != fut_e1_0 ) {
//            fut_e1_0 = fut_e1;
//            fut_e1_timeStamp.add( new MyTimeStampObject( Instant.now( ), fut_e1_0 ) );
//        }


        // Update count
        sleep_count += sleep;
    }

    @Override
    public void loadData() {
        // OP AVG
        load_data_agg(MySql.Queries.op_query(DATA_SCHEME, "dax_index", "dax_fut_week"), client, client.getExps().getExp(ExpStrings.week), OP_AVG_TYPE);
        load_data_agg(MySql.Queries.op_query(DATA_SCHEME, "dax_index", "dax_fut_month"), client, client.getExps().getExp(ExpStrings.month), OP_AVG_TYPE);
        load_data_agg(MySql.Queries.op_query(DATA_SCHEME, "dax_index", "dax_fut_e1"), client, client.getExps().getExp(ExpStrings.q1), OP_AVG_TYPE);
        load_data_agg(MySql.Queries.op_query(DATA_SCHEME, "dax_index", "dax_fut_e2"), client, client.getExps().getExp(ExpStrings.q2), OP_AVG_TYPE);

        // BASKETS
        load_data_agg(MySql.Queries.get_serie(tablesNames.get(BASKETS_TABLE)), client, null, BASKETS_TYPE);

        //  RACES
        load_data_agg(MySql.Queries.get_serie(tablesNames.get(INDEX_RACES_TABLE)), client, null, INDEX_RACES_TYPE);
        load_data_agg(MySql.Queries.get_serie(tablesNames.get(FUT_RACES_TABLE)), client, null, FUT_RACES_TYPE);
    }

    @Override
    public void initTablesNames() {
        tablesNames.put(INDEX_TABLE, "data.spx500_index");
        tablesNames.put(INDEX_RACES_TABLE, "sagiv.dax_index_races");
        tablesNames.put(FUT_RACES_TABLE, "sagiv.dax_fut_races");
        tablesNames.put(BASKETS_TABLE, "data.dax_baskets");
    }

    private void loadDDeCells() {
        String query = "SELECT * FROM sagiv.dde_cells;";

        ResultSet rs = MySql.select(query);

        while (true) {
            try {
                if (!rs.next()) break;

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    private void updateListsRetro() {
        insertListRetro(index_timestamp, DATA_SCHEME, "dax_index");
        insertListRetro(fut_week_timeStamp, DATA_SCHEME, "dax_fut_week");
        insertListRetro(fut_month_timeStamp, DATA_SCHEME, "dax_fut_month");
        insertListRetro(fut_e1_timeStamp, DATA_SCHEME, "dax_fut_gx1");
        insertListRetro(fut_e2_timeStamp, DATA_SCHEME, "dax_fut_gx2");
        insertListRetro(ind_counter_timestamp, SAGIV_SCHEME, "dax_index_races");
    }
}