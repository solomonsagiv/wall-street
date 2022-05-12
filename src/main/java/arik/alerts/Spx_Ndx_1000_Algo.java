package arik.alerts;

import arik.Arik;
import jibeDataGraber.DecisionsFunc;
import jibeDataGraber.DecisionsFuncFactory;
import serverObjects.indexObjects.Ndx;
import serverObjects.indexObjects.Spx;
import java.util.ArrayList;
import java.util.Map;

public class Spx_Ndx_1000_Algo extends ArikAlgoAlert {

    Spx spx;
    Ndx ndx;

    ArrayList<DecisionsFunc> df_list;

    // Constructor
    public Spx_Ndx_1000_Algo(double target_price_for_position) {
        super(target_price_for_position);
        this.target_price_for_exit_position = 0;
        spx = Spx.getInstance();
        ndx = Ndx.getInstance();
        this.df_list = new ArrayList<>();
        
        Map<String, DecisionsFunc> spx_list =  spx.getDecisionsFuncHandler().getMap();
        Map<String, DecisionsFunc> ndx_list =  ndx.getDecisionsFuncHandler().getMap();

        df_list.add(spx_list.get(DecisionsFuncFactory.DF_7));
        df_list.add(ndx_list.get(DecisionsFuncFactory.DF_7));
    }

    @Override
    public void go() {
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
                Arik.getInstance().sendMessageToEveryOne("LONG \n" + spx.getName() + " " + spx.getIndex() + "\n" + ndx.getName() + " " + ndx.getIndex());
            }
        }

        // Exit long
        if (LONG) {
            for (DecisionsFunc df : df_list) {
                if (df.getValue() < target_price_for_exit_position) {
                    LONG = false;
                    Arik.getInstance().sendMessageToEveryOne("EXIT LONG \n" + spx.getName() + " " + spx.getIndex() + "\n" + ndx.getName() + " " + ndx.getIndex());
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
                Arik.getInstance().sendMessageToEveryOne("SHORT \n" + spx.getName() + " " + spx.getIndex() + "\n" + ndx.getName() + " " + ndx.getIndex());
            }
        }

        // Exit short
        if (SHORT) {
            for (DecisionsFunc df : df_list) {
                if (df.getValue() > target_price_for_exit_position * -1) {
                    SHORT = false;
                    Arik.getInstance().sendMessageToEveryOne("EXIT SHORT \n" + spx.getName() + " " + spx.getIndex() + "\n" + ndx.getName() + " " + ndx.getIndex());
                    break;
                }
            }
        }
    }
}
