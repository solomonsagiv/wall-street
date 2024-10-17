package arik.cases;

import arik.Arik;
import arik.ArikCase;
import arik.locals.KeyBoards;
import com.pengrad.telegrambot.model.Update;
import serverObjects.indexObjects.Dax;
import serverObjects.indexObjects.Ndx;
import serverObjects.indexObjects.Spx;

public class RACE_Case extends ArikCase {

    public RACE_Case(String name) {
        super(name);
        setKeyboard(KeyBoards.main());
    }

    @Override
    public boolean doCase(Update update) {
        Spx spx = Spx.getInstance();
        Ndx ndx = Ndx.getInstance();
        Dax dax  = Dax.getInstance();

        double spx_open  = spx.getOpenPresent();
        double spx_last = spx.getLastPresent();
        int spx_index_race = spx.getArikData().index_race;
        int spx_q1_race =  spx.getArikData().q1_race;
        int spx_q1_roll_race = spx.getArikData().q1_roll_race;

        double ndx_open  = ndx.getOpenPresent();
        double ndx_last = ndx.getLastPresent();
        int ndx_index_race = ndx.getArikData().index_race;
        int ndx_q1_race = ndx.getArikData().q1_race;
        int ndx_q1_roll_race = ndx.getArikData().q1_roll_race;

        double dax_open  = dax.getOpenPresent();
        double dax_last = dax.getLastPresent();
        int dax_index_race = dax.getArikData().index_race;
        int dax_q1_race = dax.getArikData().q1_race;
        int dax_q1_roll_race = dax.getArikData().q1_roll_race;

        String text =  "-----SPX-----\n" +
                "Open: %s, Last: %s \n" +
                "Index  : %s\n" +
                "Q1     : %s\n" +
                "Q1 roll: %s\n" +
                "\n" +
                "-----NDX-----\n" +
                "Open: %s, Last: %s \n" +
                "Index  : %s\n" +
                "Q1     : %s\n" +
                "Q1 roll: %s\n" +
                "\n" +
                "-----DAX-----\n" +
                "Open: %s, Last: %s \n" +
                "Index  : %s\n" +
                "Q1     : %s\n" +
                "Q1 roll: %s\n" +
                "\n";

        String str = String.format(text, spx_open, spx_last, spx_index_race, spx_q1_race, spx_q1_roll_race
                , ndx_open, ndx_last, ndx_index_race, ndx_q1_race, ndx_q1_roll_race
                , dax_open, dax_last, dax_index_race, dax_q1_race, dax_q1_roll_race);

        Arik.getInstance().sendMessage(update, str, getKeyboard());
        return true;
    }
}