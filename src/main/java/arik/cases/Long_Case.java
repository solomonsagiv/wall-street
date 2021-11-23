package arik.cases;

import arik.Arik;
import arik.ArikCase;
import arik.locals.KeyBoards;
import com.pengrad.telegrambot.model.Update;
import serverObjects.indexObjects.Ndx;
import serverObjects.indexObjects.Spx;

public class Long_Case extends ArikCase {

    public Long_Case(String name) {
        super(name);
        setKeyboard(KeyBoards.positions());
    }
    
    @Override
    public boolean doCase(Update update) {
        Spx spx = Spx.getInstance();
        Ndx ndx = Ndx.getInstance();

        String text = "LONG \n" + spx.getName() + " " + spx.getIndex() + "\n" + ndx.getName() + " " + ndx.getIndex();
        Arik.getInstance().sendMessageToEveryOne(text);
        Arik.getInstance().sendMessage(update, "Sent", null);
        return true;
    }
}