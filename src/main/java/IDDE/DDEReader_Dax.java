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
    Exp month;

    private boolean init_exp = false;

    boolean initStocksCells = false;

    String indCell = "R17C4";
    String openCell = "R17C17";
    String highCell = "R17C19";
    String lowCell = "R17C18";
    String baseCell = "R17C20";

    // Future
    String futWeekCell = "R18C4";
    String futMonthCell = "R19C4";
    String e1Cell = "R20C4";
    String e2Cell = "R21C4";

    // Interest
    String week_interest_cell = "R18C9";
    String month_interest_cell = "R19C9";
    String q1_interest_cell = "R20C9";
    String q2_interest_cell = "R21C9";

    // Div
    String week_div_cell = "R18C10";
    String month_div_cell = "R19C10";
    String q1_div_cell = "R20C10";
    String q2_div_cell = "R21C10";

    // Day to exp
    String week_days_cell = "R18C13";
    String month_days_cell = "R192C13";
    String q1_days_cell = "R20C13";
    String q2_days_cell = "R21C13";

    String q1_normalized_num_cell = "R20C17";
    String q2_normalized_num_cell = "R21C17";

    // Vix
    String vix_cell = "R17C9";
    String vix_f_1_cell = "";
    String vix_f_2_cell = "";

//    String cofCell = "R40C9";

    // Constructor
    public DDEReader_Dax(BASE_CLIENT_OBJECT client) {
        super(client);
    }

    private void initStockCells(DDEClientConversation conversation) {

        int nameCol = 2;
        int row = 50;

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

        // Ticker
        client.setOpen(requestDouble(openCell, conversation));
        client.setHigh(requestDouble(highCell, conversation));
        client.setLow(requestDouble(lowCell, conversation));
        client.setBase(requestDouble(baseCell, conversation));

        // Exps
        week.set_future(requestDouble(futWeekCell, conversation));
        month.set_future(requestDouble(futMonthCell, conversation));
        q1.set_future(requestDouble(e1Cell, conversation));
        q2.set_future(requestDouble(e2Cell, conversation));

        // Vix
        client.setVix(requestDouble(vix_cell, conversation));
        client.setVix_f_1(requestDouble(vix_f_1_cell, conversation));
        client.setVix_f_2(requestDouble(vix_f_2_cell, conversation));


        // Stocks
        updateStocks(conversation);
    }

    private void init_exps() {
        week = client.getExps().getExp(ExpStrings.day);
        month = client.getExps().getExp(ExpStrings.month);
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
//            week.setCof(requestDouble(cofCell, conversation));

            // Month
            month.setInterest(requestDouble(month_interest_cell, conversation));
            month.setDividend(requestDouble(month_div_cell, conversation));
            month.setDays_to_exp(requestDouble(month_days_cell, conversation));

            // Q1
            q1.setInterest(requestDouble(q1_interest_cell, conversation));
            q1.setDividend(requestDouble(q1_div_cell, conversation));
            q1.setDays_to_exp(requestDouble(q1_days_cell, conversation));
            q1.setNormalized_num(requestDouble(q1_normalized_num_cell, conversation));
//            q1.setCof(requestDouble(cofCell, conversation));

            // Q2
            q2.setInterest(requestDouble(q2_interest_cell, conversation));
            q2.setDividend(requestDouble(q2_div_cell, conversation));
            q2.setDays_to_exp(requestDouble(q2_days_cell, conversation));
            q2.setNormalized_num(requestDouble(q2_normalized_num_cell, conversation));
//            q2.setCof(requestDouble(cofCell, conversation));

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
