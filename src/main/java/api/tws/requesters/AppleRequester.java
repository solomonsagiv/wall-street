package api.tws.requesters;

import api.Downloader;
import api.tws.ITwsRequester;
import com.ib.client.TickAttr;
import exp.Exp;
import options.Options;
import serverObjects.stockObjects.Apple;
import tws.TwsContractsEnum;

import java.util.ArrayList;

public class AppleRequester implements ITwsRequester {

    ArrayList<Exp> exp;
    Apple apple;

    @Override
    public void request(Downloader downloader) {
        try {
            apple = Apple.getInstance();
            exp = apple.getExps().getExpList();

            // Index
            downloader.reqMktData(apple.getTwsHandler().getMyContract(TwsContractsEnum.INDEX).getMyId(), apple.getTwsHandler().getMyContract(TwsContractsEnum.INDEX));

            // Options
            apple.getTwsHandler().requestOptions(apple.getExps().getExpList());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void reciever(int tickerId, int field, double price, TickAttr attribs) {
        int index;
        int minID, maxID;

        // ---------- Apple ---------- //
        index = apple.getTwsHandler().getMyContract(TwsContractsEnum.INDEX).getMyId();

        if (tickerId == index && price > 0) {
            // Last
            if (field == 4) {
                apple.setIndex(price);
            }
            // Bid
            if (field == 1) {
                apple.setIndexBid(price);
            }
            // Ask
            if (field == 2) {
                apple.setIndexAsk(price);
            }

            // Bid
            if (field == 6) {
                apple.setHigh(price);
            }
            // Ask
            if (field == 7) {
                apple.setLow(price);
            }

            // Base
            if (field == 9) {
                apple.setBase(price);
            }

            // Open
            if (field == 14) {
                apple.setOpen(price);
            }
        }

        for (Exp exp : this.exp) {

            Options options = exp.getOptions();
            minID = options.getMinId();
            maxID = options.getMaxId();

            if (tickerId >= minID && tickerId <= maxID && price > 0) {
                // Bid
                if (field == 1) {
                    options.getOptionById(tickerId).setBid(price);
                }
                // Ask
                if (field == 2) {
                    options.getOptionById(tickerId).setAsk(price);
                }
            }
        }
    }

    @Override
    public void sizeReciever(int tickerId, int field, int size) {

    }
}
