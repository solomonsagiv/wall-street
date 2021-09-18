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
    Exp week;
    Exp month;
    E q1;
    E q2;

    private boolean init_exp = false;

    String indCell = "R2C3";
    String indBidCell = "R2C2";
    String indAskCell = "R2C4";
    String openCell = "R13C4";
    String highCell = "R13C1";
    String lowCell = "R13C2";
    String baseCell = "R11C5";
    String futDayCell = "R9C10";
    String futWeekCell = "R10C10";
    String futMonthCell = "R11C10";
    String e1Cell = "R12C10";
    String e2Cell = "R13C10";

    // Naked future for delta
    String naked_future_cell = "R32C13";
    String naked_future_bid_cell = "R33C13";
    String naked_future_ask_cell = "R31C13";
    String naked_future_volume_cell = "R38C13";

    // Interest
    String day_interest_cell = "R38C5";
    String week_interest_cell = "R39C5";
    String month_interest_cell = "R40C5";
    String q1_interest_cell = "R41C5";
    String q2_interest_cell = "R42C5";

    // Div
    String day_div_cell = "R38C5";
    String week_div_cell = "R39C6";
    String month_div_cell = "R40C6";
    String q1_div_cell = "R41C6";
    String q2_div_cell = "R42C6";

    // Day to exp
    String day_days_cell = "R38C5";
    String week_days_cell = "R39C7";
    String month_days_cell = "R40C7";
    String q1_days_cell = "R41C7";
    String q2_days_cell = "R42C7";

    // Constructor
    public DDEReader_Spx(BASE_CLIENT_OBJECT client) {
        super(client);
    }

    @Override
    public void updateData(DDEClientConversation conversation) {

        if (!init_exp) {
            init_exps();
        }

        // Naked future and volume (Delta calc)
        q1.setNaked_future(requestDouble(naked_future_cell, conversation));
        q1.setNaked_future_bid(requestDouble(naked_future_bid_cell, conversation));
        q1.setNaked_future_ask(requestDouble(naked_future_ask_cell, conversation));
        q1.setVolume((int) requestDouble(naked_future_volume_cell, conversation));

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
        week.set_future(requestDouble(futWeekCell, conversation));
        month.set_future(requestDouble(futMonthCell, conversation));
        q1.set_future(requestDouble(e1Cell, conversation));
        q2.set_future(requestDouble(e2Cell, conversation));
    }

    private void init_exps() {
        day = client.getExps().getExp(ExpStrings.day);
        week = client.getExps().getExp(ExpStrings.week);
        month = client.getExps().getExp(ExpStrings.month);
        q1 = (E) client.getExps().getExp(ExpStrings.q1);
        q2 = (E) client.getExps().getExp(ExpStrings.q2);
        init_exp = true;
    }

    @Override
    public void init_rates() {
        try {
            DDEClientConversation conversation = new DDEConnection().createNewConversation(client.getExcel_path());

            Exp day = client.getExps().getExp(ExpStrings.day);
            Exp week = client.getExps().getExp(ExpStrings.week);
            Exp month = client.getExps().getExp(ExpStrings.month);
            Exp q1 = client.getExps().getExp(ExpStrings.q1);
            Exp q2 = client.getExps().getExp(ExpStrings.q2);

            // Day
            day.setInterest(requestDouble(day_interest_cell, conversation));
            day.setDividend(requestDouble(day_div_cell, conversation));
            day.setDays_to_exp(requestDouble(day_days_cell, conversation));

            // Week
            week.setInterest(requestDouble(week_interest_cell, conversation));
            week.setDividend(requestDouble(week_div_cell, conversation));
            week.setDays_to_exp(requestDouble(week_days_cell, conversation));

            // Month
            month.setInterest(requestDouble(month_interest_cell, conversation));
            month.setDividend(requestDouble(month_div_cell, conversation));
            month.setDays_to_exp(requestDouble(month_days_cell, conversation));

            // Q1
            q1.setInterest(requestDouble(q1_interest_cell, conversation));
            q1.setDividend(requestDouble(q1_div_cell, conversation));
            q1.setDays_to_exp(requestDouble(q1_days_cell, conversation));

            // Q2
            q2.setInterest(requestDouble(q2_interest_cell, conversation));
            q2.setDividend(requestDouble(q2_div_cell, conversation));
            q2.setDays_to_exp(requestDouble(q2_days_cell, conversation));

            conversation.disconnect();
        } catch (DDEException e) {
            e.printStackTrace();
        }
    }
}