package arik.cases;

import arik.Arik;
import arik.ArikCase;
import arik.grabdata.ArikGrabData;
import arik.locals.KeyBoards;
import com.pengrad.telegrambot.model.Update;
import locals.L;

public class TA35_Case extends ArikCase {

    public TA35_Case(String name) {
        super(name);
        setKeyboard(KeyBoards.main());
    }

    @Override
    public boolean doCase(Update update) {
        String return_text = "TA35" + " price " + L.format100(ArikGrabData.ta35_index) + "\n" +
                "V5 = " + L.format_int(ArikGrabData.ta35_v5) + "\n" +
                "V6 = " + L.format_int(ArikGrabData.ta35_v6) + "\n";

        Arik.getInstance().sendMessage(update, return_text, getKeyboard());
        return true;
    }
}