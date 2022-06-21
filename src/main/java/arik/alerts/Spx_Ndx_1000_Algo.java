package arik.alerts;

import arik.Arik;
import arik.dataHandler.DataHandler;
import arik.dataHandler.DataObject;
import serverObjects.indexObjects.Ndx;
import serverObjects.indexObjects.Spx;

import java.util.ArrayList;

public class Spx_Ndx_1000_Algo extends ArikAlgoAlert {

    Spx spx;
    Ndx ndx;

    DataHandler dataHandler;
    ArrayList<DataObject> dataObjects;

    DataObject spx_index;
    DataObject ndx_index;

    // Constructor
    public Spx_Ndx_1000_Algo(double target_price_for_position) {
        super(target_price_for_position);
        this.target_price_for_exit_position = 0;
        spx = Spx.getInstance();
        ndx = Ndx.getInstance();
        dataHandler = Arik.getInstance().getDataHandler();

        init_data_objects();
        
    }

    private void init_data_objects() {
        this.dataObjects = new ArrayList<>();
        dataObjects.add(dataHandler.get(DataHandler.NDX_DF_2));
        dataObjects.add(dataHandler.get(DataHandler.NDX_DF_7));
        dataObjects.add(dataHandler.get(DataHandler.SPX_DF_2));
        dataObjects.add(dataHandler.get(DataHandler.SPX_DF_7));

        spx_index = dataHandler.get(DataHandler.SPX_INDEX);
        ndx_index = dataHandler.get(DataHandler.NDX_INDEX);
    }

    @Override
    public void go() {
        // --------------- LONG --------------- //
        // Enter long
        if (!LONG) {
            boolean b = true;
            for (DataObject df : dataObjects) {
                if (df.getValue() < target_price_for_position) {
                    b = false;
                    break;
                }
            }
            LONG = b;

            if (LONG) {
                Arik.getInstance().sendMessageToEveryOne("LONG \n" + spx.getName() + " " + spx_index.getValue() + "\n" + ndx.getName() + " " + ndx_index.getValue());
            }
        }

        // Exit long
        if (LONG) {
            for (DataObject df : dataObjects) {
                if (df.getValue() < target_price_for_exit_position) {
                    LONG = false;
                    Arik.getInstance().sendMessageToEveryOne("EXIT LONG \n" + spx.getName() + " " + spx_index.getValue() + "\n" + ndx.getName() + " " + ndx_index.getValue());
                    break;
                }
            }
        }

        // --------------- SHORT --------------- //
        // Enter short
        if (!SHORT) {
            boolean b = true;
            for (DataObject df : dataObjects) {
                if (df.getValue() > target_price_for_position * -1) {
                    b = false;
                    break;
                }
            }
            SHORT = b;

            if (SHORT) {
                Arik.getInstance().sendMessageToEveryOne("SHORT \n" + spx.getName() + " " + spx_index.getValue() + "\n" + ndx.getName() + " " + ndx_index.getValue());
            }
        }

        // Exit short
        if (SHORT) {
            for (DataObject df : dataObjects) {
                if (df.getValue() > target_price_for_exit_position * -1) {
                    SHORT = false;
                    Arik.getInstance().sendMessageToEveryOne("EXIT SHORT \n" + spx.getName() + " " + spx_index.getValue() + "\n" + ndx.getName() + " " + ndx_index.getValue());
                    break;
                }
            }
        }
    }
}
