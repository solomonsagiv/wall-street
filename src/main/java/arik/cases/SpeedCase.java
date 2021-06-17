package arik.cases;

import arik.Arik;
import arik.ArikCase;
import com.pengrad.telegrambot.model.Update;

public class SpeedCase extends ArikCase {

    public SpeedCase(String name) {
        super(name);
    }

    @Override
    public boolean doCase(Update update) {
//        Alert alert = new Alert();
        Arik.getInstance().sendMessage("Alert is turned on Speed" );
        return true;
    }
}