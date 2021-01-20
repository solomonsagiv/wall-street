package IDDEReaderUpdater;

import DDE.DDECellsEnum;
import com.pretty_tools.dde.client.DDEClientConversation;
import exp.ExpStrings;
import serverObjects.BASE_CLIENT_OBJECT;

public class DDEReaderUpdater_A extends IDDEReaderUpdater {

    // Constructor
    public DDEReaderUpdater_A( BASE_CLIENT_OBJECT client ) {
        super( client );
    }

    @Override
    public void updateData( DDEClientConversation conversation ) {

        // Index
        client.setIndex( requestDouble( client.getDdeCells( ).getCell( DDECellsEnum.IND ), conversation ) );
        client.setIndexBid( requestDouble( client.getDdeCells( ).getCell( DDECellsEnum.IND_BID ), conversation ) );
        client.setIndexAsk( requestDouble( client.getDdeCells( ).getCell( DDECellsEnum.IND_ASK ), conversation ) );

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

    }

}
