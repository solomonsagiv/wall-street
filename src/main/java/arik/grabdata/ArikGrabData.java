package arik.grabdata;

import arik.window.ArikMainPanel;
import dataBase.mySql.MySql;
import dataBase.mySql.dataUpdaters.IDataBaseHandler;
import jibeDataGraber.DecisionsFuncFactory;
import serverObjects.indexObjects.Ndx;
import serverObjects.indexObjects.Spx;
import threads.MyThread;

public class ArikGrabData extends MyThread implements Runnable {

    Spx spx;
    Ndx ndx;


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

        ArikMainPanel.textArea.setText(spx.toStringPretty());
    }

    private void grab_data() {
        spx = Spx.getInstance();
        // ---------------------------------- Spx ---------------------------------- //
        double spx_index = IDataBaseHandler.handle_rs(MySql.Queries.get_last_record("data.spx500_index"));
        double spx_df_5 = IDataBaseHandler.handle_rs(MySql.Queries.get_sum("data.research_spx500_df_300_cdf"));
        double spx_df_n_5 = IDataBaseHandler.handle_rs(MySql.Queries.get_sum("data.research_spx500_df_n_300_cdf"));
        double spx_df_day = IDataBaseHandler.handle_rs(MySql.Queries.get_sum("data.research_spx500_df_cdf"));
        double spx_df_n_day = IDataBaseHandler.handle_rs(MySql.Queries.get_sum("data.research_spx500_df_n_cdf"));

        spx.setIndex(spx_index);
        spx.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_5).setValue(spx_df_5);
        spx.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_N_5).setValue(spx_df_n_5);
        spx.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_DAY).setValue(spx_df_day);
        spx.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_N_DAY).setValue(spx_df_n_day);


        ndx = Ndx.getInstance();
        // ---------------------------------- Ndx ---------------------------------- //
        double ndx_index = IDataBaseHandler.handle_rs(MySql.Queries.get_last_record("data.ndx_index"));
        double ndx_df_5 = IDataBaseHandler.handle_rs(MySql.Queries.get_sum("data.research_ndx_df_300_cdf"));
        double ndx_df_n_5 = IDataBaseHandler.handle_rs(MySql.Queries.get_sum("data.research_ndx_df_n_300_cdf"));
        double ndx_df_day = IDataBaseHandler.handle_rs(MySql.Queries.get_sum("data.research_ndx_df_cdf"));
        double ndx_df_n_day = IDataBaseHandler.handle_rs(MySql.Queries.get_sum("data.research_ndx_df_n_cdf"));

        ndx.setIndex(ndx_index);
        ndx.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_5).setValue(ndx_df_5);
        ndx.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_N_5).setValue(ndx_df_n_5);
        ndx.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_DAY).setValue(ndx_df_day);
        ndx.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_N_DAY).setValue(ndx_df_n_day);
    }

    @Override
    public void initRunnable() {
        setRunnable(this);
    }
}
