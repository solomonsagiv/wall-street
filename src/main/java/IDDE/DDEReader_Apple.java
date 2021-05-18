package IDDE;

import DDE.DDEConnection;
import com.pretty_tools.dde.DDEException;
import com.pretty_tools.dde.client.DDEClientConversation;
import exp.Exp;
import exp.ExpStrings;
import locals.L;
import serverObjects.BASE_CLIENT_OBJECT;
import stocksHandler.MiniStock;

public class DDEReader_Apple extends IDDEReader {

    boolean initStocksCells = false;

    String indCell = "R2C3";
    String openCell = "R12C4";
    String highCell = "R12C1";
    String lowCell = "R12C2";
    String baseCell = "R10C5";

    // Future
    String futWeekCell = "R9C10";
    String futMonthCell = "R10C10";

    // Interest
    String week_interest_cell = "R38C5";
    String month_interest_cell = "R39C5";

    // Div
    String week_div_cell = "R38C6";
    String month_div_cell = "R39C6";

    // Day to exp
    String week_days_cell = "R38C7";
    String month_days_cell = "R39C7";

    // Constructor
    public DDEReader_Apple(BASE_CLIENT_OBJECT client) {
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
        client.getExps().getExp(ExpStrings.week).set_future(requestDouble(futWeekCell, conversation));
        client.getExps().getExp(ExpStrings.month).set_future(requestDouble(futMonthCell, conversation));
    }

    @Override
    public void init_rates() {
        try {
            DDEClientConversation conversation = new DDEConnection().createNewConversation(client.getDdeHandler().getPath());

            Exp week = client.getExps().getExp(ExpStrings.week);
            Exp month = client.getExps().getExp(ExpStrings.month);
            Exp q1 = client.getExps().getExp(ExpStrings.q1);
            Exp q2 = client.getExps().getExp(ExpStrings.q2);

            // Week
            week.setInterest(requestDouble(week_interest_cell, conversation));
            week.setDividend(requestDouble(week_div_cell, conversation));
            week.setDays_to_exp(requestDouble(week_days_cell, conversation));

            // Month
            month.setInterest(requestDouble(month_interest_cell, conversation));
            month.setDividend(requestDouble(month_div_cell, conversation));
            month.setDays_to_exp(requestDouble(month_days_cell, conversation));

            conversation.disconnect();
        } catch (DDEException e) {
            e.printStackTrace();
        }
    }

    private void updateStocks(DDEClientConversation conversation) {
        for (MiniStock stock : client.getStocksHandler().getStocks()) {
            try {
                stock.setLastPrice(L.dbl(conversation.request(stock.getDdeCells().getLastPriceCell())));
//                stock.setBid(L.dbl(conversation.request(stock.getDdeCells().getBidCell())));
//                stock.setAsk(L.dbl(conversation.request(stock.getDdeCells().getAskCell())));
                stock.setVolume(L.dbl(conversation.request(stock.getDdeCells().getVolumeCell())));
//                stock.setWeight(L.dbl(conversation.request(stock.getDdeCells().getWeightCell())));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
