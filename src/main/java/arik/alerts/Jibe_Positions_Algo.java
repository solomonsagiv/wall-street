package arik.alerts;

import arik.dataHandler.DataHandler;
import arik.dataHandler.DataObject;
import serverObjects.indexObjects.Ndx;
import serverObjects.indexObjects.Spx;

import java.util.ArrayList;

public class Jibe_Positions_Algo extends ArikAlgoAlert {

    Spx spx;
    Ndx ndx;

    DataHandler dataHandler;
    ArrayList<DataObject> dataObjects;

    DataObject spx_index;
    DataObject ndx_index;

    String status = "";

    final String NO_POSITION = "NO_POSITION";
    final String LONG = "LONG";
    final String SHORT = "SHORT";

    // Constructor

    public Jibe_Positions_Algo(double target_price_for_position) {
        super(target_price_for_position);
    }


    @Override
    public void go() {



    }
}

