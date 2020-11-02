package arik.cases;

import arik.Arik;
import arik.ArikCase;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;

public class ChooseStockCase extends ArikCase {

    Keyboard keyboard = new ReplyKeyboardMarkup(new KeyboardButton[]{new KeyboardButton("Spx"),
            new KeyboardButton("Ndx"), new KeyboardButton("Apple")});

    // Constructor
    public ChooseStockCase() {

        setKeyboard(keyboard);
    }

    @Override
    public boolean doCase(Update update) {
        Arik.getInstance().sendMessage(update, "Choose stock", getKeyboard());
        return true;
    }


}
