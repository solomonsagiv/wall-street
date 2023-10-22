package IDDE;

import DDE.DDEConnection;
import com.pretty_tools.dde.DDEException;
import com.pretty_tools.dde.client.DDEClientConversation;
import exp.E;
import exp.Exp;
import exp.ExpStrings;
import serverObjects.BASE_CLIENT_OBJECT;

public class DDEReader_Spx extends IDDEReader {

    Exp day;
    E q1;
    E q2;
    Exp month;
    
    private boolean init_exp = false;

    String indCell = "R11C4";
    String indBidCell = "R11C3";
    String indAskCell = "R11C5";
    String openCell = "R11C18";
    String highCell = "R11C20";
    String lowCell = "R11C19";
    String baseCell = "R11C21";
    String futDayCell = "R9C10";

    String futWeekCell = "R12C4";
    String futMonthCell = "R13C4";
    String e1Cell = "R14C4";
    String e2Cell = "R15C4";

    // Interest
    String day_interest_cell = "R12C9";
    String month_interest_cell = "R13C9";
    String q1_interest_cell = "R14C9";
    String q2_interest_cell = "R15C9";

    // Div
    String day_div_cell = "R12C14";
    String month_div_cell = "R13C14";
    String q1_div_cell = "R14C14";
    String q2_div_cell = "R15C14";

    // Day to exp
    String day_days_cell = "R12C13";
    String month_days_cell = "R13C13";
    String q1_days_cell = "R14C13";
    String q2_days_cell = "R15C13";

    // Cof
    String cofCell = "R40C9";

    String q1_normalized_num_cell = "R43C10";
    String q2_normalized_num_cell = "R44C10";

    // Constructor
    public DDEReader_Spx(BASE_CLIENT_OBJECT client) {
        super(client);
    }

    @Override
    public void updateData(DDEClientConversation conversation) {

        if (!init_exp) {
            init_exps();
        }

        // Index
        client.setIndex(requestDouble(indCell, conversation));
        client.setIndexBid(requestDouble(indBidCell, conversation));
        client.setIndexAsk(requestDouble(indAskCell, conversation));

        // Ticker
        client.setHigh(requestDouble(highCell, conversation));
        client.setLow(requestDouble(lowCell, conversation));
        client.setBase(requestDouble(baseCell, conversation));

        // Exps
        day.set_future(requestDouble(futDayCell, conversation));
        q1.set_future(requestDouble(e1Cell, conversation));
        q2.set_future(requestDouble(e2Cell, conversation));
    }

    private void init_exps() {
        day = client.getExps().getExp(ExpStrings.day);
        q1 = (E) client.getExps().getExp(ExpStrings.q1);
        q2 = (E) client.getExps().getExp(ExpStrings.q2);
        init_exp = true;
    }
    
    @Override
    public void init_rates() {
        try {
            DDEClientConversation conversation = new DDEConnection().createNewConversation(client.getExcel_path());

            Exp day = client.getExps().getExp(ExpStrings.day);
            Exp q1 = client.getExps().getExp(ExpStrings.q1);
            Exp q2 = client.getExps().getExp(ExpStrings.q2);

            // Day
            day.setInterest(requestDouble(day_interest_cell, conversation));
            day.setDividend(requestDouble(day_div_cell, conversation));
            day.setDays_to_exp(requestDouble(day_days_cell, conversation));
            day.setCof(requestDouble(cofCell, conversation));

            // Q1
            q1.setInterest(requestDouble(q1_interest_cell, conversation));
            q1.setDividend(requestDouble(q1_div_cell, conversation));
            q1.setDays_to_exp(requestDouble(q1_days_cell, conversation));
            q1.setCof(requestDouble(cofCell, conversation));
            q1.setNormalized_num(requestDouble(q1_normalized_num_cell, conversation));

            // Q2
            q2.setInterest(requestDouble(q2_interest_cell, conversation));
            q2.setDividend(requestDouble(q2_div_cell, conversation));
            q2.setDays_to_exp(requestDouble(q2_days_cell, conversation));
            q2.setCof(requestDouble(cofCell, conversation));
            q2.setNormalized_num(requestDouble(q2_normalized_num_cell, conversation));

            conversation.disconnect();
        } catch (DDEException e) {
            e.printStackTrace();
        }
    }
}