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
    
    private boolean init_exp = false;

    String indCell = "R2C3";
    String indBidCell = "R2C2";
    String indAskCell = "R2C4";
    String openCell = "R13C4";
    String highCell = "R13C1";
    String lowCell = "R13C2";
    String baseCell = "R11C5";
    String futDayCell = "R9C10";
    String futDayBidCell = "R9C9";
    String futDayAskCell = "R9C11";
    String put_day_bid_cell = "R61C8";
    String put_day_ask_cell = "R61C6";
    String call_day_bid_cell = "R61C2";
    String call_day_ask_cell = "R61C4";


    String futWeekCell = "R10C10";
    String futWeekBidCell = "R10C9";
    String futWeekAskCell = "R10C11";
    String futMonthCell = "R11C10";
    String e1Cell = "R12C10";
    String e1_contract_bid_cell = "R27C13";
    String e1_contract_ask_cell = "R27C15";
    String e2Cell = "R13C10";

    // Naked future for delta
    String naked_future_cell = "R32C13";
    String naked_future_bid_cell = "R33C13";
    String naked_future_ask_cell = "R31C13";
    String naked_future_volume_cell = "R38C13";

    // Interest
    String day_interest_cell = "R40C4";
    String week_interest_cell = "R41C4";
    String month_interest_cell = "R42C4";
    String q1_interest_cell = "R43C4";
    String q2_interest_cell = "R44C4";

    // Div
    String day_div_cell = "R40C5";
    String week_div_cell = "R41C5";
    String month_div_cell = "R42C5";
    String q1_div_cell = "R43C5";
    String q2_div_cell = "R44C5";

    // Day to exp
    String day_days_cell = "R40C6";
    String week_days_cell = "R41C6";
    String month_days_cell = "R42C6";
    String q1_days_cell = "R43C6";
    String q2_days_cell = "R44C6";

    // Cof
    String cofCell = "R40C9";

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
        double index = client.getIndex();
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

            // Q2
            q2.setInterest(requestDouble(q2_interest_cell, conversation));
            q2.setDividend(requestDouble(q2_div_cell, conversation));
            q2.setDays_to_exp(requestDouble(q2_days_cell, conversation));
            q2.setCof(requestDouble(cofCell, conversation));

            conversation.disconnect();
        } catch (DDEException e) {
            e.printStackTrace();
        }
    }
}