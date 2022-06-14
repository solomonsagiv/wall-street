package arik.grabdata;

import arik.window.ArikMainPanel;
import charts.timeSeries.MyTimeSeries;
import charts.timeSeries.TimeSeriesFactory;
import charts.timeSeries.TimeSeriesHandler;
import dataBase.mySql.MySql;
import dataBase.mySql.dataUpdaters.IDataBaseHandler;
import serverObjects.indexObjects.Ndx;
import serverObjects.indexObjects.Spx;
import threads.MyThread;

import java.util.ArrayList;
import java.util.Objects;

public class ArikGrabData extends MyThread implements Runnable {

    Spx spx;
    Ndx ndx;

    public static double ta35_index = 0;
    public static int ta35_v5 = 0;
    public static int ta35_v6 = 0;
    public static int ta35_pre_v5 = 0;
    public static int ta35_pre_v6 = 0;

    String ta35_dec_table = "data.ta35_decision_func";

    ArrayList<MyTimeSeries> series_list;

    public ArikGrabData() {
        spx = Spx.getInstance();
        ndx = Ndx.getInstance();


    }

    @Override
    public void run() {
        go();
    }

    private void go() {

        while (isRun()) {
            try {
                // Sleep
                Thread.sleep(20000);

                // Grab data
                grab_data();

                // Set text to scroll pane
                set_text();

            } catch (Exception e) {
                e.printStackTrace();
                ArikMainPanel.textArea.setText("Not working");
            }
        }
    }

    private void set_text() {
        try {
            if (ArikMainPanel.textArea != null) {
                ArikMainPanel.textArea.setText(spx.toStringPretty());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void grab_data() {

        // ---------------------------------- Spx ---------------------------------- //

        spx.setIndex(MySql.Queries.handle_rs(Objects.requireNonNull(MySql.Queries.get_last_record_mega(TimeSeriesHandler.INDEX, MySql.RAW))));
        spx.getTimeSeriesHandler().get(TimeSeriesFactory.DF_7_RAW);


//        spx.setIndex(spx_index);
//        spx.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_5).setValue(spx_df_5);
//        spx.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_N_5).setValue(spx_df_n_5);
//        spx.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_DAY).setValue(spx_df_day);
//        spx.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_N_DAY).setValue(spx_df_n_day);

        // ---------------------------------- Ndx ---------------------------------- //
        ndx = Ndx.getInstance();
        double ndx_index = IDataBaseHandler.handle_rs(MySql.Queries.get_last_record("data.ndx_index"));
        double ndx_df_5 = IDataBaseHandler.handle_rs(MySql.Queries.get_sum("data.research_ndx_df_300_cdf"));
        double ndx_df_n_5 = IDataBaseHandler.handle_rs(MySql.Queries.get_sum("data.research_ndx_df_n_300_cdf"));
        double ndx_df_day = IDataBaseHandler.handle_rs(MySql.Queries.get_sum_from_df("data.ndx_decision_func", 601, 4));
        double ndx_df_n_day = IDataBaseHandler.handle_rs(MySql.Queries.get_sum_from_df("data.ndx_decision_func", 602, 4));

        ndx.setIndex(ndx_index);
//        ndx.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_5).setValue(ndx_df_5);
//        ndx.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_N_5).setValue(ndx_df_n_5);
//        ndx.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_DAY).setValue(ndx_df_day);
//        ndx.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_N_DAY).setValue(ndx_df_n_day);
//
        // ---------------------------------- TA35 ---------------------------------- //

        // V5
        // 0 Check and change check
//        if (v5 != 0 && v5 != ta35_v5) {
//            ta35_pre_v5 = ta35_v5;
//            ta35_v5 = v5;
//        }

        // V5
        // 0 Check and change check
//        if (v6 != 0 && v6 != ta35_v6) {
//            ta35_pre_v6 = ta35_v6;
//            ta35_v6 = v6;
//        }

    }

    public static void update_pre_v5() {
        ta35_pre_v5 = ta35_v5;
    }

    public static void update_pre_v6() {
        ta35_pre_v6 = ta35_v6;
    }

    @Override
    public void initRunnable() {
        setRunnable(this);
    }
}
