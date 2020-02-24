package tws;

import com.ib.client.Contract;

public interface ITws {

    Contract getIndexContract();

    Contract getFutureContract( String expiry );

    Contract getOptionContract( String expiry, String right, double strike );

    Contract getFutureOptionContract( String expiry, String symbol, String traingClass, String right, double strike );

    void initTwsData();

}
