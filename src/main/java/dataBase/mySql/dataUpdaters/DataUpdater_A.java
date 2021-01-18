package dataBase.mySql.dataUpdaters;

import dataBase.mySql.MySql;
import exp.ExpStrings;
import exp.Exps;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Spx;

import java.time.Instant;
import java.util.ArrayList;

public class DataUpdater_A extends IDataUpdater {

    public static void main(String[] args) {
        ArrayList<MyTimeStampObject> list = new ArrayList<>();
        list.add(new MyTimeStampObject(Instant.now(), 4545));
        list.add(new MyTimeStampObject(Instant.now(), 6323));
        list.add(new MyTimeStampObject(Instant.now(), 355));
        list.add(new MyTimeStampObject(Instant.now(), 243));
        list.add(new MyTimeStampObject(Instant.now(), 4343));
        list.add(new MyTimeStampObject(Instant.now(), 3434));

        DataUpdater_A dataUpdater_a = new DataUpdater_A(Spx.getInstance());
        dataUpdater_a.insertListRetro(list, "index");
    }


    ArrayList<MyTimeStampObject> index_timestamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> index_bid_timestamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> index_ask_timestamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> fut_day_timeStamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> fut_week_timeStamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> fut_month_timeStamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> fut_e1_timeStamp = new ArrayList<>();
    ArrayList<MyTimeStampObject> fut_e2_timeStamp = new ArrayList<>();


    double index_0 = 0;
    double index_bid_0 = 0;
    double index_ask_0 = 0;
    double fut_day_0 = 0;
    double fut_week_0 = 0;
    double fut_month_0 = 0;
    double fut_e1_0 = 0;
    double fut_e2_0 = 0;
    double index_bid_ask_counter_0 = 0;
    Exps exps;

    public DataUpdater_A(BASE_CLIENT_OBJECT client) {
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

        System.out.println(index_timestamp.size());

        // Index
//        if (client.getIndex() != index_0) {
            index_0 = client.getIndex();
            index_timestamp.add(new MyTimeStampObject(Instant.now(), index_0));
//        }
        // Index bid
//        if (client.getIndexBid() != index_bid_0) {
            index_bid_0 = client.getIndexBid();
            index_bid_timestamp.add(new MyTimeStampObject(Instant.now(), index_bid_0));
//        }

        // Index ask
//        if (client.getIndexAsk() != index_ask_0) {
            index_0 = client.getIndexAsk();
            index_ask_timestamp.add(new MyTimeStampObject(Instant.now(), index_ask_0));
//        }

        // Fut day
        double fut_day = exps.getExp(ExpStrings.day).getFuture();

//        if (fut_day != fut_day_0) {
            fut_day_0 = fut_day;
            fut_day_timeStamp.add(new MyTimeStampObject(Instant.now(), fut_day_0));
//        }

        // Fut week
        double fut_week = exps.getExp(ExpStrings.week).getFuture();

//        if (fut_week != fut_week_0) {
            fut_week_0 = fut_week;
            fut_week_timeStamp.add(new MyTimeStampObject(Instant.now(), fut_week_0));
//        }

        // Fut month
        double fut_month = exps.getExp(ExpStrings.month).getFuture();

//        if (fut_month != fut_month_0) {
            fut_month_0 = fut_month;
            fut_month_timeStamp.add(new MyTimeStampObject(Instant.now(), fut_month_0));
//        }

        // Fut e1
        double fut_e1 = exps.getExp(ExpStrings.e1).getFuture();

//        if (fut_e1 != fut_e1_0) {
            fut_e1_0 = fut_e1;
            fut_e1_timeStamp.add(new MyTimeStampObject(Instant.now(), fut_e1_0));
//        }

        // Fut e2
        double fut_e2 = exps.getExp(ExpStrings.e2).getFuture();

//        if (fut_e2 != fut_e2_0) {
            fut_e2_0 = fut_e2;
            fut_e2_timeStamp.add(new MyTimeStampObject(Instant.now(), fut_e2_0));
//        }

        // Update count
        sleep_count += sleep;

    }

    private void insertListRetro(ArrayList<MyTimeStampObject> list, String name) {

        if (list.size() > 0) {

            // Create the query
            StringBuilder queryBuiler = new StringBuilder("INSERT INTO %s.%s (time, value) VALUES ");
            int last_item_id = list.get(list.size() - 1).hashCode();
            for (MyTimeStampObject row : list) {
                queryBuiler.append(String.format("(cast('%s' as timestamp with time zone), %s)", row.getInstant(), row.getValue()));
                if (row.hashCode() != last_item_id) {
                    queryBuiler.append(",");
                }
            }
            queryBuiler.append(";");

            String q = String.format(queryBuiler.toString(), client.getName(), name);

            System.out.println(q);

            // Insert
            MySql.insert(q, true);

            // Clear the list
            list.clear();
        }
    }

    private void updateListsRetro() {
        insertListRetro(index_timestamp, "index");
        insertListRetro(index_bid_timestamp, "index_bid");
        insertListRetro(index_ask_timestamp, "index_ask");
        insertListRetro(fut_day_timeStamp, "fut_day");
        insertListRetro(fut_week_timeStamp, "fut_week");
        insertListRetro(fut_month_timeStamp, "fut_month");
        insertListRetro(fut_e1_timeStamp, "fut_e1");
        insertListRetro(fut_e2_timeStamp, "fut_e2");
    }
}
