package arik.alerts;

import threads.MyThread;
import java.util.ArrayList;

public class AlertsHandler extends MyThread implements Runnable {

    private String speed_table_name = "data.research_spx500_501_speed_900";
    private String acc_table_name = "data.research_spx500_501_speed2_900";
    private ArrayList<Alert> alerts;
    private int sleep = 10000;

    public AlertsHandler() {
        this.alerts = new ArrayList<>();
    }

    @Override
    public void run() {
        while (isRun()) {
            try {
                // Sleep
                Thread.sleep(sleep);

                // Logic
                logic();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void logic() {



    }

    @Override
    public void initRunnable() {
        setRunnable(this);
    }

//    public Alert create_alert() {
//        Alert alert = new Alert();
//        alerts.add(alert);
//        return alert;
//    }

    public void add_alert(Alert alert) {
        alerts.add(alert);
    }

    public void kill_alert(Alert alert) {
        alert.getHandler().close();
    }


}
