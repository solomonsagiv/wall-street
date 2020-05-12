package api.tws.requesters;

import api.Downloader;
import api.tws.ITwsRequester;
import api.tws.TwsHandler;
import basketFinder.MiniStock;
import basketFinder.handlers.StocksHandler;
import com.ib.client.Contract;
import com.ib.client.TickAttr;
import options.Options;
import options.OptionsEnum;
import serverObjects.indexObjects.Dax;
import tws.TwsContractsEnum;

import java.util.Map;

public class DaxRequester implements ITwsRequester {

    Dax dax;
    int indexId, futureId, futureFarId;
    Options optionsWeek, optionsMonth;
    StocksHandler stocksHandler;

    @Override
    public void request(Downloader downloader) {
        try {
            init();

            TwsHandler twsHandler = dax.getTwsHandler();

            // Index
            downloader.reqMktData(twsHandler.getMyContract(TwsContractsEnum.INDEX).getMyId(), twsHandler.getMyContract(TwsContractsEnum.INDEX));

            // Futures

            // Stocks
            requestStocks(downloader);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void requestStocks(Downloader downloader) throws Exception {

        Contract contract = new Contract();
        contract.secType("STK");
        contract.exchange("SMART");
        contract.currency("EUR");
        contract.primaryExch("IBIS");

        for (Map.Entry<Integer, MiniStock> entry : dax.getStocksHandler().getMiniStockMap().entrySet()) {
            MiniStock stock = entry.getValue();
            contract.symbol(stock.getName());

            System.out.println(stock.getName() + " ID: " + stock.getId());

            downloader.reqMktData(stock.getId(), contract);
        }
    }

    private void init() {
        dax = Dax.getInstance();
        optionsWeek = dax.getOptionsHandler().getOptions(OptionsEnum.WEEK);
        optionsMonth = dax.getOptionsHandler().getOptions(OptionsEnum.MONTH);

        indexId = dax.getTwsHandler().getMyContract(TwsContractsEnum.INDEX).getMyId();
        stocksHandler = dax.getStocksHandler();
    }

    @Override
    public void reciever(int tickerId, int field, double price, TickAttr attribs) {

        if (dax.isStarted()) {

            // ---------- Future ---------- //
            if (tickerId == futureId && price > 0) {
                // Bid
                if (field == 1) {
                    optionsWeek.setFutureBid(price);
                }
                // Ask
                if (field == 2) {
                    optionsWeek.setFutureAsk(price);
                }
            }

            // ---------- Future far ---------- //
            if (tickerId == futureFarId && price > 0) {
                // Bid
                if (field == 1) {
                    optionsMonth.setFutureBid(price);
                }
                // Ask
                if (field == 2) {
                    optionsMonth.setFutureAsk(price);
                }
            }

            // Spx miniStocks
            if (tickerId >= stocksHandler.getMinId() && tickerId < stocksHandler.getMaxId()) {

                MiniStock stock = stocksHandler.getMiniStockMap().get(tickerId);

                // Bid
                if (field == 1) {
                    stock.setIndexBid(price);
                }

                // Ask
                if (field == 2) {
                    stock.setIndexAsk(price);
                }

                // Last
                if (field == 4) {
                    stock.setInd(price);
                }
            }
        }
    }

    @Override
    public void sizeReciever(int tickerId, int field, int size) {

        // Spx miniStocks
        if (tickerId >= stocksHandler.getMinId() && tickerId < stocksHandler.getMaxId()) {

            MiniStock stock = stocksHandler.getMiniStockMap().get(tickerId);

            if (field == 8) {
                System.out.println(stock.getName() + " " + size );
                stock.setVolume(size);
            }

        }

    }
}
