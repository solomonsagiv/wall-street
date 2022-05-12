package arik.cases;

import arik.Arik;
import arik.ArikCase;
import arik.locals.KeyBoards;
import com.pengrad.telegrambot.model.Update;
import jibeDataGraber.DecisionsFuncFactory;
import locals.L;
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

        String spx_df_n_5 = L.format_int(spx.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_7).getValue());
        String ndx_df_n_5 = L.format_int(ndx.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_7).getValue());

        String return_text = "SPX DF_3 = %s \n" +
                "SPX DF_7 = %s \n" +
                "NDX DF_3 = %s \n" +
                "NDX DF_7 %s";

        String str = String.format(return_text, spx_df_n_5, ndx_df_n_5);

        Arik.getInstance().sendMessage(update, str, getKeyboard());
        return true;
    }
}