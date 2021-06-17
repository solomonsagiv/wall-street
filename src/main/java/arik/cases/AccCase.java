package arik.cases;

import arik.Arik;
import arik.ArikCase;
import com.pengrad.telegrambot.model.Update;

public class AccCase extends ArikCase {

    public AccCase(String name) {
        super(name);
    }

    @Override
    public boolean doCase(Update update) {
        Arik.getInstance().sendMessage("Alert is turned on Acc" );
        return true;
    }


}