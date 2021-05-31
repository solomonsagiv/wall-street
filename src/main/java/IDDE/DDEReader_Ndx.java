package IDDE;

import DDE.DDEConnection;
import com.pretty_tools.dde.DDEException;
import com.pretty_tools.dde.client.DDEClientConversation;
import exp.Exp;
import exp.ExpStrings;
import locals.L;
import serverObjects.BASE_CLIENT_OBJECT;
import stocksHandler.MiniStock;

public class DDEReader_Ndx extends IDDEReader {

    boolean initStocksCells = false;

    String indCell = "R2C3";
    String openCell = "R13C4";
    String highCell = "R13C1";
    String lowCell = "R13C2";
    String baseCell = "R11C5";
    String futDayCell = "R9C10";
    String futWeekCell = "R10C10";
    String futMonthCell = "R11C10";
    String e1Cell = "R12C10";
    String e2Cell = "R13C10";

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
    public DDEReader_Ndx(BASE_CLIENT_OBJECT client) {
        super(client);
    }

    private void initStockCells(DDEClientConversation conversation) {

        int nameCol = 26;
        int row = 2;

        while (true) {
            try {
                String name = conversation.request(String.format("R%sC%s", row, nameCol));

                // End
                if (row > 500) {
                    break;
                }

                // End
                if (name.replaceAll("\\s+", "").equals("0")) {
                    break;
                }

                // Add stock
                client.getStocksHandler().addStock(name, row);
                row++;

            } catch (DDEException e) {
                e.printStackTrace();
            }
        }

        initStocksCells = true;
    }

    @Override
    public void updateData(DDEClientConversation conversation) {

        if (!initStocksCells) {
            initStockCells(conversation);
        }

        // Index
        client.setIndex(requestDouble(indCell, conversation));

        // Ticker
        client.setOpen(requestDouble(openCell, conversation));
        client.setHigh(requestDouble(highCell, conversation));
        client.setLow(requestDouble(lowCell, conversation));
        client.setBase(requestDouble(baseCell, conversation));

        // Exps
        client.getExps().getExp(ExpStrings.day).set_future(requestDouble(futDayCell, conversation));
        client.getExps().getExp(ExpStrings.week).set_future(requestDouble(futWeekCell, conversation));
        client.getExps().getExp(ExpStrings.month).set_future(requestDouble(futMonthCell, conversation));
        client.getExps().getExp(ExpStrings.q1).set_future(requestDouble(e1Cell, conversation));
        client.getExps().getExp(ExpStrings.q2).set_future(requestDouble(e2Cell, conversation));

        // Stocks
        updateStocks(conversation);

    }

    @Override
    public void init_rates() {
        try {
            DDEClientConversation conversation = new DDEConnection().createNewConversation(client.getDdeHandler().getPath());

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

    private void updateStocks(DDEClientConversation conversation) {
        for (MiniStock stock : client.getStocksHandler().getStocks()) {
            try {
                stock.setLastPrice(requestDouble(stock.getDdeCells().getLastPriceCell(), conversation));
                stock.setBid(requestDouble(stock.getDdeCells().getBidCell(), conversation));
                stock.setAsk(requestDouble(stock.getDdeCells().getAskCell(), conversation));
                stock.setVolume(requestDouble(stock.getDdeCells().getVolumeCell(), conversation));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
