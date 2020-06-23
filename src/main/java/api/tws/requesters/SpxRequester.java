package api.tws.requesters;

import api.Downloader;
import api.tws.ITwsRequester;
import api.tws.TwsHandler;
import basketFinder.MiniStock;
import basketFinder.handlers.StocksHandler;
import com.ib.client.Contract;
import com.ib.client.TickAttr;
import exp.Exp;
import exp.ExpEnum;
import serverObjects.indexObjects.Spx;
import tws.TwsContractsEnum;

import java.util.Map;

public class SpxRequester implements ITwsRequester {

    Spx spx;
    int indexId, futureId, futureFarId;
    Exp e1, e2;

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
//            requestStocks(downloader);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private void requestStocks(Downloader downloader) throws Exception {
//
//        Contract contract = new Contract();
//        contract.secType("STK");
//        contract.exchange("SMART");
//        contract.currency("USD");
//        contract.primaryExch("ISLAND");
//
//        for (Map.Entry<Integer, MiniStock> entry : spx.getStocksHandler().getMiniStockMap().entrySet()) {
//            MiniStock stock = entry.getValue();
//            contract.symbol(stock.getName());
//            downloader.reqMktData(stock.getId(), contract);
//        }
//    }

    private void init() {
        spx = Spx.getInstance();
        e1 = spx.getExps().getExp( ExpEnum.E1);
        e2 = spx.getExps().getExp(ExpEnum.E2);

        indexId = spx.getTwsHandler().getMyContract(TwsContractsEnum.INDEX).getMyId();
        futureId = spx.getTwsHandler().getMyContract(TwsContractsEnum.FUTURE).getMyId();
        futureFarId = spx.getTwsHandler().getMyContract(TwsContractsEnum.FUTURE_FAR).getMyId();

    }

    @Override
    public void reciever(int tickerId, int field, double price, TickAttr attribs) {

        if (spx.isStarted()) {

            // ---------- Future ---------- //
            if (tickerId == futureId && price > 0) {
                // Bid
                if (field == 1) {
                    e1.setFutBid(price);
                }
                // Ask
                if (field == 2) {
                    e1.setFutAsk(price);
                }
            }

            // ---------- Future far ---------- //
            if (tickerId == futureFarId && price > 0) {
                // Bid
                if (field == 1) {
                    e2.setFutBid(price);
                }
                // Ask
                if (field == 2) {
                    e2.setFutAsk(price);
                }
            }

            // Spx miniStocks
//            if (tickerId >= stocksHandler.getMinId() && tickerId < stocksHandler.getMaxId()) {
//
//                MiniStock stock = stocksHandler.getMiniStockMap().get(tickerId);
//
//                // Bid
//                if (field == 1) {
//                    stock.setIndexBid(price);
//                }
//
//                // Ask
//                if (field == 2) {
//                    stock.setIndexAsk(price);
//                }
//
//                // Last
//                if (field == 4) {
//                    stock.setInd(price);
//                }
//            }
        }
    }

    @Override
    public void sizeReciever(int tickerId, int field, int size) {

        // Spx miniStocks
//        if (tickerId >= stocksHandler.getMinId() && tickerId < stocksHandler.getMaxId()) {
//
//            MiniStock stock = stocksHandler.getMiniStockMap().get(tickerId);
//
//            if (field == 8) {
//                System.out.println(stock.getName() + " " + size );
//                stock.setVolume(size);
//            }
//
//        }

    }
}
