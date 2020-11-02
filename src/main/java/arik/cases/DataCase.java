package arik.cases;

import arik.ArikCase;
import arik.ArikSingleCase;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;

import java.util.Map;

public class DataCase extends ArikCase {

    // Variables
    Keyboard keyboard = new ReplyKeyboardMarkup(new KeyboardButton[]{
            new KeyboardButton("Status"),
            new KeyboardButton("Exp"),
            new KeyboardButton("Yesterday")
    });

    // Constructor
    public DataCase() {
        setKeyboard(keyboard);

//        caseMaps.put( "status", new StatusCase() );
        caseMaps.put("exp", new ExpCase());
        caseMaps.put("yesterday", new YesterDayCase());

    }

    @Override
    public boolean doCase(Update update) {

        for (Map.Entry<String, ArikCase> entry : caseMaps.entrySet()) {
            ArikCase arikCase = entry.getValue();


        }

        return false;

    }

}

class StatusCase extends ArikSingleCase {

    // Constructor
    public StatusCase(String message) {
        super(message);
    }

    @Override
    public boolean doCase(Update update) {
        return false;
    }
}

class ExpCase extends ArikCase {

    @Override
    public boolean doCase(Update update) {
        return false;
    }
}

class YesterDayCase extends ArikCase {

    @Override
    public boolean doCase(Update update) {
        return false;
    }
}