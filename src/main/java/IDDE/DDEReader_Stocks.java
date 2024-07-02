package IDDE;

import com.pretty_tools.dde.DDEException;
import com.pretty_tools.dde.client.DDEClientConversation;
import exp.E;
import exp.Exp;
import serverObjects.BASE_CLIENT_OBJECT;
import stocksHandler.MiniStock;

public class DDEReader_Stocks extends IDDEReader {

    Exp week;
    E q1;
    E q2;
    Exp month;

    private boolean init_exp = false;

    boolean initStocksCells = false;

    String vix_f_2_cell = "";

//    String cofCell = "R40C9";

    // Constructor
    public DDEReader_Stocks(BASE_CLIENT_OBJECT client) {
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

        // Stocks
        updateStocks(conversation);
    }

    private void init_exps() {
    }

    @Override
    public void init_rates() {
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
