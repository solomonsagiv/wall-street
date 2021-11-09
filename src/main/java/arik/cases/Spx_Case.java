package arik.cases;

import arik.Arik;
import arik.ArikCase;
import arik.locals.KeyBoards;
import com.pengrad.telegrambot.model.Update;
import jibeDataGraber.DecisionsFunc;
import jibeDataGraber.DecisionsFuncFactory;
import serverObjects.indexObjects.Spx;

public class Spx_Case extends ArikCase {

    public Spx_Case(String name) {
        super(name);
        setKeyboard(KeyBoards.main());
    }

    @Override
    public boolean doCase(Update update) {
        Spx client = Spx.getInstance();

        DecisionsFunc df_5 = client.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_5);
        DecisionsFunc df_n_5 = client.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_N_5);
        DecisionsFunc df_day = client.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_DAY);
        DecisionsFunc df_n_day = client.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_N_DAY);

        String return_text = client.getName() + " price " + client.getIndex() + "\n" +
                "DF 5 = " + (int) df_5.getValue() + "\n" +
                "DF N 5 = " + (int) df_n_5.getValue() + "\n" +
                "DF DAY = " + (int) df_day.getValue() + "\n" +
                "DF N DAY = " + (int) df_n_day.getValue() + "\n";

        Arik.getInstance().sendMessage(update, return_text, getKeyboard());
        return true;
    }
}