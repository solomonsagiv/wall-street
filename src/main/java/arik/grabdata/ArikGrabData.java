package arik.grabdata;

import arik.Arik;
import arik.alerts.Jibe_Positions_Algo;
import arik.window.ArikMainPanel;
import dataBase.mySql.MySql;
import serverObjects.indexObjects.Dax;
import serverObjects.indexObjects.Ndx;
import serverObjects.indexObjects.Spx;
import threads.MyThread;
import tws.accounts.ConnectionsAndAccountHandler;
import java.sql.ResultSet;

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

            if (ArikMainPanel.connectionTextArea != null) {
                ArikMainPanel.connectionTextArea.setText(ConnectionsAndAccountHandler.get_active_sessions());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void grab_data() {

//        Get races
        get_race();

        // Get ticker data
//        getTickerData();
    }

    private void get_race() {
        ResultSet rs = MySql.Queries.get_races(MySql.JIBE_PROD_CONNECTION);

        Spx spx = Spx.getInstance();
        Ndx ndx = Ndx.getInstance();
        Dax dax = Dax.getInstance();

        while (true) {
            try {
                if (!rs.next())break;
                dax.getArikData().index_race = rs.getInt("dax_index");
                dax.getArikData().q1_race = rs.getInt("dax_q1");
                dax.getArikData().q1_roll_race = rs.getInt("dax_q1_roll");

                spx.getArikData().index_race = rs.getInt("spx_index");
                spx.getArikData().q1_race = rs.getInt("spx_q1");
                spx.getArikData().q1_roll_race = rs.getInt("spx_q1_roll");

                ndx.getArikData().index_race = rs.getInt("ndx_index");
                ndx.getArikData().q1_race = rs.getInt("ndx_q1");
                ndx.getArikData().q1_roll_race = rs.getInt("ndx_q1_roll");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initRunnable() {
        setRunnable(this);
    }
}
