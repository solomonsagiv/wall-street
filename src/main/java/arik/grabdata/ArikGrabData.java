package arik.grabdata;

import arik.window.ArikMainPanel;
import charts.timeSeries.MyTimeSeries;
import serverObjects.indexObjects.Ndx;
import serverObjects.indexObjects.Spx;
import threads.MyThread;

import java.util.ArrayList;

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
