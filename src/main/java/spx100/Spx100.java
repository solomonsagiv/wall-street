package spx100;

import api.Downloader;
import com.ib.client.Contract;
import java.util.HashMap;
import java.util.Map;

public class Spx100 {

    // Variables
    private Map< Integer, MiniStock > miniStockMap = new HashMap<>( );

    // Constructor
    public Spx100() {
        init( );
    }

    public void request( Downloader downloader ) throws Exception {

        Spx100 spx100 = new Spx100( );

        Contract contract = new Contract( );
        contract.secType( "STK" );
        contract.exchange( "SMART" );
        contract.currency( "USD" );
        contract.primaryExch( "ISLAND" );

        for ( Map.Entry< Integer, MiniStock > entry : spx100.getMiniStockMap().entrySet()) {
            MiniStock stock = entry.getValue();
            contract.symbol( stock.getName() );
            downloader.reqMktData( stock.getId(), contract );

            System.out.println( "Stock: " + stock.getName() + " Id: " + stock.getId());
        }
    }

    private void init() {

        int id = 5000;

        miniStockMap.put( id, new MiniStock( "MSFT", id++ ) );
        miniStockMap.put( id, new MiniStock( "AAPL", id++ ) );
        miniStockMap.put( id, new MiniStock( "AMZN", id++ ) );
        miniStockMap.put( id, new MiniStock( "FB", id++ ) );
        miniStockMap.put( id, new MiniStock( "INTC", id++ ) );
        miniStockMap.put( id, new MiniStock( "GOOG", id++ ) );
        miniStockMap.put( id, new MiniStock( "GOOGL", id++ ) );
        miniStockMap.put( id, new MiniStock( "PEP", id++ ) );
        miniStockMap.put( id, new MiniStock( "NFLX", id++ ) );
        miniStockMap.put( id, new MiniStock( "CSCO", id++ ) );
        miniStockMap.put( id, new MiniStock( "NVDA", id++ ) );
        miniStockMap.put( id, new MiniStock( "CMCSA", id++ ) );
        miniStockMap.put( id, new MiniStock( "ADBE", id++ ) );
        miniStockMap.put( id, new MiniStock( "PYPL", id++ ) );
        miniStockMap.put( id, new MiniStock( "AMGN", id++ ) );
        miniStockMap.put( id, new MiniStock( "COST", id++ ) );
        miniStockMap.put( id, new MiniStock( "TSLA", id++ ) );
        miniStockMap.put( id, new MiniStock( "CHTR", id++ ) );
        miniStockMap.put( id, new MiniStock( "TXN", id++ ) );
        miniStockMap.put( id, new MiniStock( "AVGO", id++ ) );
        miniStockMap.put( id, new MiniStock( "GILD", id++ ) );
        miniStockMap.put( id, new MiniStock( "SBUX", id++ ) );
        miniStockMap.put( id, new MiniStock( "QCOM", id++ ) );
        miniStockMap.put( id, new MiniStock( "TMUS", id++ ) );
        miniStockMap.put( id, new MiniStock( "MDLZ", id++ ) );
        miniStockMap.put( id, new MiniStock( "VRTX", id++ ) );
        miniStockMap.put( id, new MiniStock( "INTU", id++ ) );
        miniStockMap.put( id, new MiniStock( "FISV", id++ ) );
        miniStockMap.put( id, new MiniStock( "AMD", id++ ) );
        miniStockMap.put( id, new MiniStock( "REGN", id++ ) );
        miniStockMap.put( id, new MiniStock( "ADP", id++ ) );
        miniStockMap.put( id, new MiniStock( "ISRG", id++ ) );
        miniStockMap.put( id, new MiniStock( "BKNG", id++ ) );
        miniStockMap.put( id, new MiniStock( "BIIB", id++ ) );
        miniStockMap.put( id, new MiniStock( "ATVI", id++ ) );
        miniStockMap.put( id, new MiniStock( "CSX", id++ ) );
        miniStockMap.put( id, new MiniStock( "MU", id++ ) );
        miniStockMap.put( id, new MiniStock( "AMAT", id++ ) );
        miniStockMap.put( id, new MiniStock( "ILMN", id++ ) );
        miniStockMap.put( id, new MiniStock( "JD", id++ ) );
        miniStockMap.put( id, new MiniStock( "ADSK", id++ ) );
        miniStockMap.put( id, new MiniStock( "WBA", id++ ) );
        miniStockMap.put( id, new MiniStock( "ADI", id++ ) );
        miniStockMap.put( id, new MiniStock( "LRCX", id++ ) );
        miniStockMap.put( id, new MiniStock( "KHC", id++ ) );
        miniStockMap.put( id, new MiniStock( "EXC", id++ ) );
        miniStockMap.put( id, new MiniStock( "XEL", id++ ) );
        miniStockMap.put( id, new MiniStock( "EA", id++ ) );
        miniStockMap.put( id, new MiniStock( "MNST", id++ ) );
        miniStockMap.put( id, new MiniStock( "EBAY", id++ ) );
        miniStockMap.put( id, new MiniStock( "ROST", id++ ) );
        miniStockMap.put( id, new MiniStock( "DXCM", id++ ) );
        miniStockMap.put( id, new MiniStock( "CTSH", id++ ) );
        miniStockMap.put( id, new MiniStock( "ORLY", id++ ) );
        miniStockMap.put( id, new MiniStock( "MELI", id++ ) );
        miniStockMap.put( id, new MiniStock( "BIDU", id++ ) );
        miniStockMap.put( id, new MiniStock( "LULU", id++ ) );
        miniStockMap.put( id, new MiniStock( "MAR", id++ ) );
        miniStockMap.put( id, new MiniStock( "NXPI", id++ ) );
        miniStockMap.put( id, new MiniStock( "KLAC", id++ ) );
        miniStockMap.put( id, new MiniStock( "NTES", id++ ) );
        miniStockMap.put( id, new MiniStock( "VRSN", id++ ) );
        miniStockMap.put( id, new MiniStock( "SIRI", id++ ) );
        miniStockMap.put( id, new MiniStock( "VRSK", id++ ) );
        miniStockMap.put( id, new MiniStock( "PAYX", id++ ) );
        miniStockMap.put( id, new MiniStock( "ALXN", id++ ) );
        miniStockMap.put( id, new MiniStock( "WDAY", id++ ) );
        miniStockMap.put( id, new MiniStock( "IDXX", id++ ) );
        miniStockMap.put( id, new MiniStock( "PCAR", id++ ) );
        miniStockMap.put( id, new MiniStock( "SNPS", id++ ) );
        miniStockMap.put( id, new MiniStock( "CERN", id++ ) );
        miniStockMap.put( id, new MiniStock( "WLTW", id++ ) );
        miniStockMap.put( id, new MiniStock( "CDNS", id++ ) );
        miniStockMap.put( id, new MiniStock( "XLNX", id++ ) );
        miniStockMap.put( id, new MiniStock( "INCY", id++ ) );
        miniStockMap.put( id, new MiniStock( "ANSS", id++ ) );
        miniStockMap.put( id, new MiniStock( "CSGP", id++ ) );
        miniStockMap.put( id, new MiniStock( "ASML", id++ ) );
        miniStockMap.put( id, new MiniStock( "CTAS", id++ ) );
        miniStockMap.put( id, new MiniStock( "FAST", id++ ) );
        miniStockMap.put( id, new MiniStock( "MCHP", id++ ) );
        miniStockMap.put( id, new MiniStock( "SPLK", id++ ) );
        miniStockMap.put( id, new MiniStock( "CTXS", id++ ) );
        miniStockMap.put( id, new MiniStock( "DLTR", id++ ) );
        miniStockMap.put( id, new MiniStock( "BMRN", id++ ) );
        miniStockMap.put( id, new MiniStock( "CPRT", id++ ) );
        miniStockMap.put( id, new MiniStock( "SWKS", id++ ) );
        miniStockMap.put( id, new MiniStock( "CHKP", id++ ) );
        miniStockMap.put( id, new MiniStock( "ALGN", id++ ) );
        miniStockMap.put( id, new MiniStock( "CDW", id++ ) );
        miniStockMap.put( id, new MiniStock( "TTWO", id++ ) );
        miniStockMap.put( id, new MiniStock( "MXIM", id++ ) );
        miniStockMap.put( id, new MiniStock( "TCOM", id++ ) );
        miniStockMap.put( id, new MiniStock( "ULTA", id++ ) );
        miniStockMap.put( id, new MiniStock( "WDC", id++ ) );
        miniStockMap.put( id, new MiniStock( "NTAP", id++ ) );
        miniStockMap.put( id, new MiniStock( "EXPE", id++ ) );
        miniStockMap.put( id, new MiniStock( "FOXA", id++ ) );
        miniStockMap.put( id, new MiniStock( "LBTYK", id++ ) );
        miniStockMap.put( id, new MiniStock( "FOX", id++ ) );
        miniStockMap.put( id, new MiniStock( "UAL", id++ ) );
        miniStockMap.put( id, new MiniStock( "LBTYA", id++ ) );

    }


    public Map< Integer, MiniStock > getMiniStockMap() {
        return miniStockMap;
    }
}
