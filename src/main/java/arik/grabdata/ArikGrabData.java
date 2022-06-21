package arik.grabdata;

import arik.Arik;
import arik.dataHandler.DataHandler;
import arik.window.ArikMainPanel;
import dataBase.mySql.MySql;
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
                ArikMainPanel.textArea.setText(spx.toStringPretty());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void grab_data() {

        DataHandler dataHandler = arik.getDataHandler();

        // TA35
        dataHandler.get(DataHandler.TA35_INDEX).setValue(MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(5, MySql.RAW)));
        dataHandler.get(DataHandler.TA35_DF_5).setValue((int) MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(92, MySql.CDF)));
        dataHandler.get(DataHandler.TA35_DF_6).setValue((int) MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(93, MySql.CDF)));

        // SPX
        dataHandler.get(DataHandler.SPX_INDEX).setValue(MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(3, MySql.RAW)));
        dataHandler.get(DataHandler.SPX_DF_2).setValue(MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(1028, MySql.CDF)));
        dataHandler.get(DataHandler.SPX_DF_7).setValue(MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(1023, MySql.CDF)));

        // NDX
        dataHandler.get(DataHandler.NDX_INDEX).setValue(MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(1, MySql.RAW)));
        dataHandler.get(DataHandler.NDX_DF_2).setValue(MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(995, MySql.CDF)));
        dataHandler.get(DataHandler.NDX_DF_7).setValue(MySql.Queries.handle_rs(MySql.Queries.get_last_record_mega(990, MySql.CDF)));

    }

    @Override
    public void initRunnable() {
        setRunnable(this);
    }
}
