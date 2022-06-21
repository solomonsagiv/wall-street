package arik.cases;

import arik.Arik;
import arik.ArikCase;
import arik.dataHandler.DataHandler;
import arik.dataHandler.DataObject;
import arik.locals.KeyBoards;
import com.pengrad.telegrambot.model.Update;
import locals.L;
import serverObjects.indexObjects.Ndx;

public class Ndx_Case extends ArikCase {

    public Ndx_Case(String name) {
        super(name);
        setKeyboard(KeyBoards.main());
    }

    @Override
    public boolean doCase(Update update) {
        Ndx client = Ndx.getInstance();

        DataHandler dataHandler = Arik.getInstance().getDataHandler();

        DataObject index = dataHandler.get(DataHandler.NDX_INDEX);
        DataObject df_2 = dataHandler.get(DataHandler.NDX_DF_2);
        DataObject df_7 = dataHandler.get(DataHandler.NDX_DF_7);


        String return_text = client.getName() + " price " + L.format100(index.getValue()) + "\n" +
                "DF 2 = " + L.format_int(df_2.getValue()) + "\n" +
                "DF 7 = " + L.format_int(df_7.getValue()) + "\n";
//
        Arik.getInstance().sendMessage(update, return_text, getKeyboard());
        return true;
    }
}