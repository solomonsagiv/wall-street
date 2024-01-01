package arik.alerts;

import arik.Arik;
import dataBase.mySql.MySql;
import threads.MyThread;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Alert extends MyThread implements Runnable {

    // Veriables
    final int UP = 1;
    final int DOWN = 2;
    private int id;
    private double target;
    private int direction;
    private int sleep = 10000;
    private String table_location;
    private double value = Integer.MIN_VALUE;
    ArikPositionsAlert arikPositionsAlert;
    private boolean got_direction = false;
    private boolean target_hit = false;
    Arik arik;

    // Constructor
    public Alert(ArikPositionsAlert arikPositionsAlert, int id, double target, String table_location) {
        this.arikPositionsAlert = arikPositionsAlert;
        this.id = id;
        this.target = target;
        this.table_location = table_location;
        this.arik = Arik.getInstance();

        // Add alert
//        alertsHandler.add_alert(this);
    }

    @Override
    public void run() {

        while (isRun()) {
            try {
                // Sleep
                Thread.sleep(sleep);

                // End alert
                end_alert();

                // Get data
                get_data_from_db(table_location);

                // Find direction
                find_direction();

                // Look for target
                look_for_target();
            } catch (InterruptedException e) {
                getHandler().start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void end_alert() {
        if (target_hit) {
            getHandler().close();
        }
    }

    private void look_for_target() {
        // Up
        if (direction == UP) {
            if (value > direction) {
                arik.sendMessageToEveryOne("Target hit " + value);
                target_hit = true;
                return;
            }
        }

        // Down
        if (direction == DOWN) {
            if (value < target) {
                arik.sendMessageToEveryOne("Target hit " + value);
                target_hit = true;
                return;
            }
        }
    }

    private void find_direction() {
        // IF no target direction yet
        if (!got_direction) {
            // If there is current value yet
            if (value == Integer.MIN_VALUE) {
                // Find the direction
                // Up
                if (value < target) {
                    direction = UP;
                    got_direction = true;
                }
                // Down
                if (value > target) {
                    direction = DOWN;
                    got_direction = true;
                }
            }
        }
    }

    @Override
    public void initRunnable() {
        setRunnable(this);
    }

    private double get_data_from_db(String table_location) {

        ResultSet rs = MySql.Queries.get_last_record(table_location, MySql.JIBE_PROD_CONNECTION);
        while (true) {
            try {
                if (!rs.next()) break;
                double value = rs.getDouble("value");
                return value;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return Integer.MIN_VALUE;
    }

    // ---------- Getters and Setters ---------- //
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public double getTarget() {
        return target;
    }

    public void setTarget(double target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return "Alert{" +
                "id=" + id +
                ", target=" + target +
                ", direction=" + direction +
                '}';
    }
}
