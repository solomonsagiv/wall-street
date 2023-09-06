package IDDE;

import DDE.DDEConnection;
import com.pretty_tools.dde.DDEException;
import com.pretty_tools.dde.client.DDEClientConversation;
import exp.E;
import exp.Exp;
import exp.ExpStrings;
import serverObjects.BASE_CLIENT_OBJECT;
import stocksHandler.MiniStock;

public class DDEReader_Dax extends IDDEReader {

    Exp week;
    E q1;
    E q2;

    private boolean init_exp = false;

    boolean initStocksCells = false;

    String indCell = "R2C3";
    String openCell = "R13C4";
    String highCell = "R13C1";
    String lowCell = "R13C2";
    String baseCell = "R11C5";

    // Future
    String futWeekCell = "R9C10";
    String futMonthCell = "R11C10";
    String e1Cell = "R12C10";
    String e2Cell = "R13C10";

    // Interest
    String week_interest_cell = "R40C4";
    String month_interest_cell = "R42C4";
    String q1_interest_cell = "R43C4";
    String q2_interest_cell = "R44C4";

    // Div
    String week_div_cell = "R40C5";
    String month_div_cell = "R42C5";
    String q1_div_cell = "R43C5";
    String q2_div_cell = "R44C5";

    // Day to exp
    String week_days_cell = "R40C6";
    String month_days_cell = "R42C6";
    String q1_days_cell = "R43C6";
    String q2_days_cell = "R44C6";

    String cofCell = "R40C9";


    double fut_week_0 = 0;

    // Constructor
    public DDEReader_Dax(BASE_CLIENT_OBJECT client) {
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
                if (name.replaceAll("\\s+", "").equals("0") || name.replaceAll("\\s+", "").equals("")) {
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

        if (!init_exp) {
            init_exps();
        }
        
        double index = requestDouble(indCell, conversation);

        // Index
        client.setIndex(index);
        client.setIndex_bid_synthetic(index - client.getIndex_bid_ask_synthetic_margin());
        client.setIndex_ask_synthetic(index + client.getIndex_bid_ask_synthetic_margin());

        // Ticker
        client.setOpen(requestDouble(openCell, conversation));
        client.setHigh(requestDouble(highCell, conversation));
        client.setLow(requestDouble(lowCell, conversation));
        client.setBase(requestDouble(baseCell, conversation));

        // Exps

        double fut_week = requestDouble(futWeekCell, conversation);
        if (fut_week != fut_week_0) {
            if (Math.abs(fut_week - fut_week_0) < 15) {
                week.set_future(fut_week);
            }
            fut_week_0 = fut_week;
        }

        q1.set_future(requestDouble(e1Cell, conversation));
        q2.set_future(requestDouble(e2Cell, conversation));

        // Stocks
        updateStocks(conversation);
    }

    private void init_exps() {
        week = client.getExps().getExp(ExpStrings.day);
        q1 = (E) client.getExps().getExp(ExpStrings.q1);
        q2 = (E) client.getExps().getExp(ExpStrings.q2);
        init_exp = true;
    }
    

    @Override
    public void init_rates() {
        try {
            DDEClientConversation conversation = new DDEConnection().createNewConversation(client.getExcel_path());

            if (!init_exp) {
                init_exps();
            }

            // Week
            week.setInterest(requestDouble(week_interest_cell, conversation));
            week.setDividend(requestDouble(week_div_cell, conversation));
            week.setDays_to_exp(requestDouble(week_days_cell, conversation));
            week.setCof(requestDouble(cofCell, conversation));

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

    private void updateStocks(DDEClientConversation conversation) {
        for (MiniStock stock : client.getStocksHandler().getStocks()) {
            try {
                stock.setLastPrice(requestDouble(stock.getDdeCells().getLastPriceCell(), conversation));
                stock.setBid(requestDouble(stock.getDdeCells().getBidCell(), conversation));
                stock.setAsk(requestDouble(stock.getDdeCells().getAskCell(), conversation));
                stock.setVolume(requestDouble(stock.getDdeCells().getVolumeCell(), conversation));
                stock.setWeight(requestDouble(stock.getDdeCells().getWeightCell(), conversation));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
