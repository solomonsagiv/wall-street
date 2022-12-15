package arik.grabdata;

import arik.Arik;
import arik.alerts.Jibe_Positions_Algo;
import arik.dataHandler.DataHandler;
import arik.window.ArikMainPanel;
import serverObjects.indexObjects.Ndx;
import serverObjects.indexObjects.Spx;
import threads.MyThread;

public class ArikGrabData extends MyThread implements Runnable {

    Spx spx;
    Ndx ndx;

    Arik arik;

    public ArikGrabData() {
        spx = Spx.getInstance();
        ndx = Ndx.getInstance();
        arik = Arik.getInstance();
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
                ArikMainPanel.textArea.setText(Jibe_Positions_Algo.positions_text());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void grab_data() {

        DataHandler dataHandler = arik.getDataHandler();

        // SPX
//        dataHandler.get(DataHandler.SPX_INDEX).setValue(MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(3, MySql.RAW)));
//        dataHandler.get(DataHandler.SPX_DF_2).setValue(MySql.Queries.handle_rs(MySql.Queries.get_df_cdf_by_frame(1022, 21600)));
//        dataHandler.get(DataHandler.SPX_DF_7).setValue(MySql.Queries.handle_rs(MySql.Queries.get_df_cdf_by_frame(1028, 21600)));
//        dataHandler.get(DataHandler.SPX_DF_8).setValue(MySql.Queries.handle_rs(MySql.Queries.get_df_cdf_by_frame(1029, 21600)));

//        String position_status = MySql.Queries.get_position_status();

        // NDX
//        dataHandler.get(DataHandler.NDX_INDEX).setValue(MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(1, MySql.RAW)));
//        dataHandler.get(DataHandler.NDX_DF_2).setValue(MySql.Queries.handle_rs(MySql.Queries.get_df_cdf_by_frame(989, 21600)));
//        dataHandler.get(DataHandler.NDX_DF_7).setValue(MySql.Queries.handle_rs(MySql.Queries.get_df_cdf_by_frame(995, 21600)));
//        dataHandler.get(DataHandler.NDX_DF_8).setValue(MySql.Queries.handle_rs(MySql.Queries.get_df_cdf_by_frame(996, 21600)));

    }

    @Override
    public void initRunnable() {
        setRunnable(this);
    }
}
