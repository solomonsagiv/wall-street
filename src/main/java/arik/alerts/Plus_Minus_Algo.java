package arik.alerts;

import arik.Arik;
import jibeDataGraber.DecisionsFunc;
import jibeDataGraber.DecisionsFuncFactory;
import serverObjects.BASE_CLIENT_OBJECT;
import java.util.ArrayList;
import java.util.Map;

public class Plus_Minus_Algo extends ArikAlgoAlert {

    ArrayList<DecisionsFunc> df_list;
    BASE_CLIENT_OBJECT client;

    // Constructor
    public Plus_Minus_Algo(double target_price_for_position, BASE_CLIENT_OBJECT client) {
        super(target_price_for_position);
        this.target_price_for_exit_position = 0;
        this.df_list = new ArrayList<>();

        Map<String, DecisionsFunc> df_map = client.getDecisionsFuncHandler().getMap();

        df_list.add(df_map.get(DecisionsFuncFactory.DF_DAY));
        df_list.add(df_map.get(DecisionsFuncFactory.DF_N_DAY));
    }

    @Override
    public void go() {

        System.out.println("Plus minus position algo running " + client.getName());

        // --------------- LONG --------------- //
        // Enter long
        if (!LONG) {
            boolean b = true;
            for (DecisionsFunc df : df_list) {
                if (df.getValue() < target_price_for_position) {
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
            for (DecisionsFunc df : df_list) {
                if (df.getValue() < target_price_for_exit_position) {
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
            for (DecisionsFunc df : df_list) {
                if (df.getValue() > target_price_for_position * -1) {
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
            for (DecisionsFunc df : df_list) {
                if (df.getValue() > target_price_for_exit_position * -1) {
                    SHORT = false;
                    Arik.getInstance().sendMessageToEveryOne("EXIT SHORT \n" + client.getName() + " " + client.getIndex());
                    break;
                }
            }
        }
    }
}
