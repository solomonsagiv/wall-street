package arik.alerts;

import arik.Arik;
import arik.dataHandler.DataHandler;
import arik.dataHandler.DataObject;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Ndx;
import serverObjects.indexObjects.Spx;
import java.util.ArrayList;

public class Plus_Minus_Algo extends ArikAlgoAlert {

    BASE_CLIENT_OBJECT client;

    ArrayList<DataObject> dataObjects;

    Arik arik;

    DataObject index;

    // Constructor
    public Plus_Minus_Algo(double target_price_for_position, BASE_CLIENT_OBJECT client) {
        super(target_price_for_position);
        this.target_price_for_exit_position = 0;
        this.dataObjects = new ArrayList<>();
        this.client = client;
        this.arik = Arik.getInstance();

        init_data_objects(client);
    }

    private void init_data_objects(BASE_CLIENT_OBJECT client) {
        if (client instanceof Spx) {
            dataObjects.add(arik.getDataHandler().get(DataHandler.SPX_DF_2));
            dataObjects.add(arik.getDataHandler().get(DataHandler.SPX_DF_7));
            index = arik.getDataHandler().get(DataHandler.SPX_INDEX);
        }

        if (client instanceof Ndx) {
            dataObjects.add(arik.getDataHandler().get(DataHandler.NDX_DF_2));
            dataObjects.add(arik.getDataHandler().get(DataHandler.NDX_DF_7));
            index = arik.getDataHandler().get(DataHandler.NDX_INDEX);
        }

    }

    @Override
    public void go() {

        System.out.println("Plus minus position algo running " + client.getName());

        // --------------- LONG --------------- //
        // Enter long
        if (!LONG) {
            boolean b = true;
            for (DataObject ts : dataObjects) {
                if (ts.getValue() < target_price_for_position) {
                    b = false;
                    break;
                }
            }
            LONG = b;

            if (LONG) {
                Arik.getInstance().sendMessageToSlo("LONG \n" + client.getName() + " " + index.getValue());
            }
        }

        // Exit long
        if (LONG) {
            for (DataObject ts : dataObjects) {
                if (ts.getValue() < target_price_for_exit_position) {
                    LONG = false;
                    Arik.getInstance().sendMessageToSlo("EXIT LONG \n" + client.getName() + " " + index.getValue());
                    break;
                }
            }
        }

        // --------------- SHORT --------------- //
        // Enter short
        if (!SHORT) {
            boolean b = true;
            for (DataObject ts : dataObjects) {
                if (ts.getValue() > target_price_for_position * -1) {
                    b = false;
                    break;
                }
            }
            SHORT = b;

            if (SHORT) {
                Arik.getInstance().sendMessageToSlo("SHORT \n" + client.getName() + " " + index.getValue());
            }
        }

        // Exit short
        if (SHORT) {
            for (DataObject ts : dataObjects) {
                if (ts.getValue() > target_price_for_exit_position * -1) {
                    SHORT = false;
                    Arik.getInstance().sendMessageToSlo("EXIT SHORT \n" + client.getName() + " " + index.getValue());
                    break;
                }
            }
        }
    }
}
