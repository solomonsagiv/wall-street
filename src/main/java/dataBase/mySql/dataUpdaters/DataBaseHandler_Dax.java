package dataBase.mySql.dataUpdaters;

import dataBase.mySql.MySql;
import exp.E;
import exp.Exp;
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
    ArrayList<MyTimeStampObject> baskets_timestamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> fut_delta_timestamp = new ArrayList<>();

    double index_0 = 0;
    double fut_week_0 = 0;
    double fut_month_0 = 0;
    double fut_e1_0 = 0;
    double fut_e2_0 = 0;
    double baskets_0 = 0;
    double fut_delta_0;

    Exps exps;
    E q1;

    public DataBaseHandler_Dax(BASE_CLIENT_OBJECT client) {
        super(client);
        initTablesNames();
    }

    int sleep_count = 100;

    @Override
    public void insertData(int sleep) {

        // Set exps
        if (this.exps == null || this.q1 == null) {
            this.exps = client.getExps();
            this.q1 = (E) this.exps.getExp(ExpStrings.q1);
        }

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
        double fut_week = exps.getExp(ExpStrings.week).get_future();

        if (fut_week != fut_week_0) {
            fut_week_0 = fut_week;
            fut_week_timeStamp.add(new MyTimeStampObject(Instant.now(), fut_week_0));
        }

        // Fut month
        double fut_month = exps.getExp(ExpStrings.month).get_future();

        if (fut_month != fut_month_0) {
            fut_month_0 = fut_month;
            fut_month_timeStamp.add(new MyTimeStampObject(Instant.now(), fut_month_0));
        }

        // Fut e1
        double fut_e1 = exps.getExp(ExpStrings.q1).get_future();

        if (fut_e1 != fut_e1_0) {
            fut_e1_0 = fut_e1;
            fut_e1_timeStamp.add(new MyTimeStampObject(Instant.now(), fut_e1_0));
        }

        // Fut e2
        double fut_e2 = exps.getExp(ExpStrings.q2).get_future();

        if (fut_e2 != fut_e2_0) {
            fut_e2_0 = fut_e2;
            fut_e2_timeStamp.add(new MyTimeStampObject(Instant.now(), fut_e2_0));
        }

        // Baskets
        int basket = client.getBasketFinder().getBaskets();

        if (basket != baskets_0) {
            double last_count = basket - baskets_0;
            baskets_0 = basket;
            baskets_timestamp.add(new MyTimeStampObject(Instant.now(), last_count));
        }

        // Delta
        double fut_delta = q1.getDelta();

        if (fut_delta != fut_delta_0) {
            double last_count = fut_delta - fut_delta_0;
            fut_delta_0 = fut_delta;
            fut_delta_timestamp.add(new MyTimeStampObject(Instant.now(), last_count));
        }

        // Update count
        sleep_count += sleep;
    }

    @Override
    public void loadData() {

        this.exps = client.getExps();

        // OP AVG
        Exp week = exps.getExp(ExpStrings.week);
        Exp month = exps.getExp(ExpStrings.month);
        Exp q1 = exps.getExp(ExpStrings.q1);
        Exp q2 = exps.getExp(ExpStrings.q2);

        load_op_avg(week, MySql.Queries.op_query(tablesNames.get(INDEX_TABLE), tablesNames.get(FUT_WEEK_TABLE)));
        load_op_avg(month, MySql.Queries.op_query(tablesNames.get(INDEX_TABLE), tablesNames.get(FUT_MONTH_TABLE)));
        load_op_avg(q1, MySql.Queries.op_query(tablesNames.get(INDEX_TABLE), tablesNames.get(FUT_Q1_TABLE)));
        load_op_avg(q2, MySql.Queries.op_query(tablesNames.get(INDEX_TABLE), tablesNames.get(FUT_Q2_TABLE)));

        load_fut_delta(q1, MySql.Queries.get_sum(tablesNames.get(FUT_DELTA_TABLE)));

        // LOAD PROPERTIES
        load_properties();

        // Set load
        client.setLoadFromDb(true);
    }

    @Override
    public void initTablesNames() {
        tablesNames.put(INDEX_TABLE, "data.dax_index");
        tablesNames.put(BASKETS_TABLE, "data.dax_baskets_cdf");
        tablesNames.put(FUT_WEEK_TABLE, "data.dax_fut_week");
        tablesNames.put(FUT_MONTH_TABLE, "data.dax_fut_month");
        tablesNames.put(FUT_Q1_TABLE, "data.dax_fut_q1");
        tablesNames.put(FUT_Q2_TABLE, "data.dax_fut_q2");
        tablesNames.put(FUT_DELTA_TABLE, "data.dax_fut_delta_cdf");
    }

    @Override
    protected void open_chart_on_start() {
        //todo
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
        insertListRetro(index_timestamp, tablesNames.get(INDEX_TABLE));
        insertListRetro(fut_week_timeStamp, tablesNames.get(FUT_WEEK_TABLE));
        insertListRetro(fut_month_timeStamp, tablesNames.get(FUT_MONTH_TABLE));
        insertListRetro(fut_e1_timeStamp, tablesNames.get(FUT_Q1_TABLE));
        insertListRetro(fut_e2_timeStamp, tablesNames.get(FUT_Q2_TABLE));
        insertListRetro(baskets_timestamp, tablesNames.get(BASKETS_TABLE));
        insertListRetro(fut_delta_timestamp, tablesNames.get(FUT_DELTA_TABLE));
    }
}