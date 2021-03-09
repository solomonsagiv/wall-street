package IDDE;

import com.pretty_tools.dde.DDEException;
import com.pretty_tools.dde.client.DDEClientConversation;
import exp.ExpStrings;
import locals.L;
import serverObjects.BASE_CLIENT_OBJECT;
import stocksHandler.MiniStock;

public class DDEReader_B extends IDDEReader {

    boolean initStocksCells = false;

    String indCell = "R2C3";
    String openCell = "R12C4";
    String highCell = "R12C1";
    String lowCell = "R12C2";
    String baseCell = "R10C5";
    String futWeekCell = "R9C10";
    String futMonthCell = "R10C10";
    String e1Cell = "R11C10";
    String e2Cell = "R12C10";

    // Constructor
    public DDEReader_B( BASE_CLIENT_OBJECT client ) {
        super( client );
    }

    private void initStockCells( DDEClientConversation conversation ) {

        int nameCol = 26;
        int row = 2;

        while ( true ) {
            try {
                String name = conversation.request( String.format( "R%sC%s", row, nameCol ) );

                // End
                if ( row > 500 ) {
                    break;
                }

                // End
                if ( name.replaceAll("\\s+","").equals( "0" ) ) {
                    break;
                }

                // Add stock
                client.getStocksHandler( ).addStock( name, row );
                row++;

            } catch ( DDEException e ) {
                e.printStackTrace( );
            }
        }

        initStocksCells = true;
    }

    @Override
    public void updateData( DDEClientConversation conversation ) {

        if ( !initStocksCells ) {
            initStockCells( conversation );
        }

        // Index
        client.setIndex( requestDouble( indCell, conversation ) );

        // Ticker
        client.setOpen( requestDouble( openCell, conversation ) );
        client.setHigh( requestDouble( highCell, conversation ) );
        client.setLow( requestDouble( lowCell, conversation ) );
        client.setBase( requestDouble( baseCell, conversation ) );

        // Exps
        client.getExps( ).getExp( ExpStrings.week ).setFuture( requestDouble( futWeekCell, conversation ) );
        client.getExps( ).getExp( ExpStrings.month ).setFuture( requestDouble( futMonthCell, conversation ) );
        client.getExps( ).getExp( ExpStrings.e1 ).setFuture( requestDouble( e1Cell, conversation ) );
        client.getExps( ).getExp( ExpStrings.e2 ).setFuture( requestDouble( e2Cell, conversation ) );

        // Stocks
        updateStocks( conversation );

    }

    private void updateStocks( DDEClientConversation conversation ) {
        for ( MiniStock stock : client.getStocksHandler( ).getStocks( ) ) {
            try {
                stock.setLastPrice( L.dbl( conversation.request( stock.getDdeCells( ).getLastPriceCell( ) ) ) );
                stock.setVolume( L.dbl( conversation.request( stock.getDdeCells( ).getVolumeCell( ) ) ) );
            } catch ( Exception e ) {
                e.printStackTrace( );
            }
        }
    }
}
