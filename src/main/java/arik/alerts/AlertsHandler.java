package arik.alerts;

import arik.Arik;
import jibeDataGraber.DecisionsFunc;
import jibeDataGraber.DecisionsFuncFactory;
import serverObjects.BASE_CLIENT_OBJECT;
import service.MyBaseService;
import java.util.ArrayList;
import java.util.Map;

public class AlertsHandler extends MyBaseService {


    public static void main(String[] args) {
        Arik.getInstance().sendMessageToEveryOne("Test");
        Arik.getInstance().close();
    }

    boolean LONG = false;
    boolean SHORT = false;
    
    ArrayList<DecisionsFunc> df_list;

    BASE_CLIENT_OBJECT spx;
    BASE_CLIENT_OBJECT ndx;

    public AlertsHandler(BASE_CLIENT_OBJECT spx, BASE_CLIENT_OBJECT ndx) {
        super(spx);
        this.spx = spx;
        this.ndx = ndx;
        this.df_list = new ArrayList<>();

        Map<String, DecisionsFunc> spx_list =  spx.getDecisionsFuncHandler().getMap();
        Map<String, DecisionsFunc> ndx_list =  ndx.getDecisionsFuncHandler().getMap();

        df_list.add(spx_list.get(DecisionsFuncFactory.DF_5));
        df_list.add(spx_list.get(DecisionsFuncFactory.DF_N_5));
        df_list.add(ndx_list.get(DecisionsFuncFactory.DF_5));
        df_list.add(ndx_list.get(DecisionsFuncFactory.DF_N_5));
    }

    int target_price = 1000;

    @Override
    public void go() {

        // --------------- LONG --------------- //
        // Enter long
        if (!LONG) {
            boolean b = true;
            for (DecisionsFunc df : df_list) {
                if (df.getValue() < target_price) {
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
                if (df.getValue() < target_price) {
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
                if (df.getValue() > target_price * -1) {
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
                if (df.getValue() > target_price * -1) {
                    SHORT = false;
                    Arik.getInstance().sendMessageToEveryOne("EXIT SHORT \n" + spx.getName() + " " + spx.getIndex() + "\n" + ndx.getName() + " " + ndx.getIndex());
                    break;
                }
            }
        }
    }

    @Override
    public String getName() {
        return "Alert service";
    }

    @Override
    public int getSleep() {
        return 10000;
    }
}
