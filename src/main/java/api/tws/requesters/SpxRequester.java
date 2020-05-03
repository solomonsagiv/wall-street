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
import serverObjects.indexObjects.Spx;
import tws.TwsContractsEnum;

import java.util.Map;

public class SpxRequester implements ITwsRequester {

    Spx spx;
    int indexId, futureId, futureFarId;
    Options optionsQuarter, optionsQuarterFar;
    StocksHandler stocksHandler;

    @Override
    public void request(Downloader downloader) {
        try {
            init();

            TwsHandler twsHandler = spx.getTwsHandler();

            // Index
            downloader.reqMktData(twsHandler.getMyContract(TwsContractsEnum.INDEX).getMyId(), twsHandler.getMyContract(TwsContractsEnum.INDEX));

            // Futures
            // Quarter
            downloader.reqMktData(twsHandler.getMyContract(TwsContractsEnum.FUTURE).getMyId(), twsHandler.getMyContract(TwsContractsEnum.FUTURE));
            downloader.reqMktData(twsHandler.getMyContract(TwsContractsEnum.FUTURE_FAR).getMyId(), twsHandler.getMyContract(TwsContractsEnum.FUTURE_FAR));

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
        contract.currency("USD");
        contract.primaryExch("ISLAND");

        for (Map.Entry<Integer, MiniStock> entry : spx.getStocksHandler().getMiniStockMap().entrySet()) {
            MiniStock stock = entry.getValue();
            contract.symbol(stock.getName());
            downloader.reqMktData(stock.getId(), contract);
        }
    }

    private void init() {
        spx = Spx.getInstance();
        optionsQuarter = spx.getOptionsHandler().getOptions(OptionsEnum.QUARTER);
        optionsQuarterFar = spx.getOptionsHandler().getOptions(OptionsEnum.QUARTER_FAR);

        indexId = spx.getTwsHandler().getMyContract(TwsContractsEnum.INDEX).getMyId();
        futureId = spx.getTwsHandler().getMyContract(TwsContractsEnum.FUTURE).getMyId();
        futureFarId = spx.getTwsHandler().getMyContract(TwsContractsEnum.FUTURE_FAR).getMyId();

        stocksHandler = spx.getStocksHandler();
    }

    @Override
    public void reciever(int tickerId, int field, double price, TickAttr attribs) {

        if (spx.isStarted()) {

            // ---------- Future ---------- //
            if (tickerId == futureId && price > 0) {
                // Bid
                if (field == 1) {
                    optionsQuarter.setFutureBid(price);
                }
                // Ask
                if (field == 2) {
                    optionsQuarter.setFutureAsk(price);
                }
            }

            // ---------- Future far ---------- //
            if (tickerId == futureFarId && price > 0) {
                // Bid
                if (field == 1) {
                    optionsQuarterFar.setFutureBid(price);
                }
                // Ask
                if (field == 2) {
                    optionsQuarterFar.setFutureAsk(price);
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
                stock.setVolume(size);
            }

        }

    }
}
