package arik.alerts;

import arik.Arik;
import charts.timeSeries.MyTimeSeries;
import charts.timeSeries.TimeSeriesFactory;
import serverObjects.BASE_CLIENT_OBJECT;
import java.util.ArrayList;

public class Plus_Minus_Algo extends ArikAlgoAlert {

    ArrayList<MyTimeSeries> df_list;
    BASE_CLIENT_OBJECT client;

    // Constructor
    public Plus_Minus_Algo(double target_price_for_position, BASE_CLIENT_OBJECT client) {
        super(target_price_for_position);
        this.target_price_for_exit_position = 0;
        this.df_list = new ArrayList<>();
        this.client = client;

        df_list.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_7_CDF));
        df_list.add(client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_2_CDF));
    }

    @Override
    public void go() {

        System.out.println("Plus minus position algo running " + client.getName());

        // --------------- LONG --------------- //
        // Enter long
        if (!LONG) {
            boolean b = true;
            for (MyTimeSeries ts : df_list) {
                if (ts.getData() < target_price_for_position) {
                    b = false;
                    break;
                }
            }
            LONG = b;

            if (LONG) {
                Arik.getInstance().sendMessageToEveryOne("LONG \n" + client.getName() + " " + client.getIndex());
            }
        }

        // Exit long
        if (LONG) {
            for (MyTimeSeries ts : df_list) {
                if (ts.getData() < target_price_for_exit_position) {
                    LONG = false;
                    Arik.getInstance().sendMessageToEveryOne("EXIT LONG \n" + client.getName() + " " + client.getIndex());
                    break;
                }
            }
        }

        // --------------- SHORT --------------- //
        // Enter short
        if (!SHORT) {
            boolean b = true;
            for (MyTimeSeries ts : df_list) {
                if (ts.getData() > target_price_for_position * -1) {
                    b = false;
                    break;
                }
            }
            SHORT = b;

            if (SHORT) {
                Arik.getInstance().sendMessageToEveryOne("SHORT \n" + client.getName() + " " + client.getIndex());
            }
        }

        // Exit short
        if (SHORT) {
            for (MyTimeSeries ts : df_list) {
                if (ts.getData() > target_price_for_exit_position * -1) {
                    SHORT = false;
                    Arik.getInstance().sendMessageToEveryOne("EXIT SHORT \n" + client.getName() + " " + client.getIndex());
                    break;
                }
            }
        }
    }
}
