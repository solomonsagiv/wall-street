package IDDE;

import DDE.DDECellsEnum;
import com.pretty_tools.dde.DDEException;
import com.pretty_tools.dde.client.DDEClientConversation;
import exp.ExpStrings;
import locals.L;
import serverObjects.BASE_CLIENT_OBJECT;
import stocksHandler.MiniStock;

public class DDEReader_B extends IDDEReader {

    boolean initStocksCells = false;

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
        client.setIndex( requestDouble( client.getDdeCells( ).getCell( DDECellsEnum.IND ), conversation ) );

        // Ticker
        client.setOpen( requestDouble( client.getDdeCells( ).getCell( DDECellsEnum.OPEN ), conversation ) );
        client.setHigh( requestDouble( client.getDdeCells( ).getCell( DDECellsEnum.HIGH ), conversation ) );
        client.setLow( requestDouble( client.getDdeCells( ).getCell( DDECellsEnum.LOW ), conversation ) );
        client.setBase( requestDouble( client.getDdeCells( ).getCell( DDECellsEnum.BASE ), conversation ) );

        // Exps
        client.getExps( ).getExp( ExpStrings.day ).setFuture( requestDouble( client.getDdeCells( ).getCell( DDECellsEnum.FUT_DAY ), conversation ) );
        client.getExps( ).getExp( ExpStrings.week ).setFuture( requestDouble( client.getDdeCells( ).getCell( DDECellsEnum.FUT_WEEK ), conversation ) );
        client.getExps( ).getExp( ExpStrings.month ).setFuture( requestDouble( client.getDdeCells( ).getCell( DDECellsEnum.FUT_MONTH ), conversation ) );
        client.getExps( ).getExp( ExpStrings.e1 ).setFuture( requestDouble( client.getDdeCells( ).getCell( DDECellsEnum.E1 ), conversation ) );
        client.getExps( ).getExp( ExpStrings.e2 ).setFuture( requestDouble( client.getDdeCells( ).getCell( DDECellsEnum.E2 ), conversation ) );

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
