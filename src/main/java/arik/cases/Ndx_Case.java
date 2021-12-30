package arik.cases;

import arik.Arik;
import arik.ArikCase;
import arik.locals.KeyBoards;
import com.pengrad.telegrambot.model.Update;
import jibeDataGraber.DecisionsFunc;
import jibeDataGraber.DecisionsFuncFactory;
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
        
        DecisionsFunc df_day = client.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_DAY);
        DecisionsFunc df_n_day = client.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_N_DAY);

        String return_text = client.getName() + " price " + L.format100(client.getIndex()) + "\n" +
                "DF DAY = " + L.format_int(df_day.getValue()) + "\n" +
                "DF N DAY = " + L.format_int(df_n_day.getValue()) + "\n";

        Arik.getInstance().sendMessage(update, return_text, getKeyboard());
        return true;
    }
}