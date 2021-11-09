package arik.cases;

import arik.Arik;
import arik.ArikCase;
import arik.locals.KeyBoards;
import com.pengrad.telegrambot.model.Update;

public class Df_Case extends ArikCase {

    public Df_Case(String name) {
        super(name);
        setKeyboard(KeyBoards.main());
    }

    @Override
    public boolean doCase(Update update) {
        String return_text = "DF price ndx , spx ";
        Arik.getInstance().sendMessage(update, return_text, getKeyboard());
        return true;
    }
}