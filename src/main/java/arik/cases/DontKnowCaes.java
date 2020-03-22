package arik.cases;

import arik.Arik;
import arik.ArikCase;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.Keyboard;

public class DontKnowCaes extends ArikCase {

    public DontKnowCaes() {
    }

    @Override
    public boolean doCase( Update update ) {
        Arik.getInstance().sendMessage( update, "Don't know what to do", null );
        return true;
    }
}
