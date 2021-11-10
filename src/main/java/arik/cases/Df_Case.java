package arik.cases;

import arik.Arik;
import arik.ArikCase;
import arik.locals.KeyBoards;
import com.pengrad.telegrambot.model.Update;
import jibeDataGraber.DecisionsFuncFactory;
import serverObjects.indexObjects.Ndx;
import serverObjects.indexObjects.Spx;

public class Df_Case extends ArikCase {

    public Df_Case(String name) {
        super(name);
        setKeyboard(KeyBoards.main());
    }

    @Override
    public boolean doCase(Update update) {
        Spx spx = Spx.getInstance();
        Ndx ndx = Ndx.getInstance();

        double spx_df_5 = spx.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_5).getValue();
        double spx_df_n_5 = spx.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_N_5).getValue();
        double ndx_df_5 = ndx.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_5).getValue();
        double ndx_df_n_5 = ndx.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_N_5).getValue();

        String return_text = "SPX DF_5 = %s \n" +
                "SPX DF_N_5 = %s \n" +
                "NDX DF_5 = %s \n" +
                "NDX DF_N_5 %s";

        String str = String.format(return_text, spx_df_5, spx_df_n_5, ndx_df_5, ndx_df_n_5);

        Arik.getInstance().sendMessage(update, str, getKeyboard());
        return true;
    }
}