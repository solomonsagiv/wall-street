package IDDE;

import com.pretty_tools.dde.DDEException;
import com.pretty_tools.dde.client.DDEClientConversation;
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

    private void updateStocks(DDEClientConversation conversation) {
        for (MiniStock stock : client.getStocksHandler().getStocks()) {
            try {
                stock.setLastPrice(L.dbl(conversation.request(stock.getDdeCells().getLastPriceCell())));
                stock.setVolume(L.dbl(conversation.request(stock.getDdeCells().getVolumeCell())));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
