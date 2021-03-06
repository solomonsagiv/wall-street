package IDDEReaderUpdater;

import DDE.DDECellsEnum;
import com.pretty_tools.dde.client.DDEClientConversation;
import exp.ExpStrings;
import serverObjects.BASE_CLIENT_OBJECT;

import java.util.ArrayList;

public class DDEReaderUpdater_B extends IDDEReaderUpdater {

    boolean initStocksCeels = false;

    ArrayList< String > stocksPriceCells = new ArrayList<>( );
    ArrayList< String > stocksVolumeCells = new ArrayList<>( );

    // Constructor
    public DDEReaderUpdater_B( BASE_CLIENT_OBJECT client ) {
        super( client );

        System.out.println( "Initittttttttt" );
    }

    private void initStockCells( DDEClientConversation conversation ) {

        int lastPriceCol = 27;
        int volumeCol = 28;
        int row = 0;

        while ( true ) {
            System.out.println( "Enter" );
            String cell = "R%sC%s";
            String lastCell = String.format( cell, row, lastPriceCol );
            cell = "R%sC%s";
            String volumeCell = String.format( cell, row, volumeCol );

            stocksPriceCells.add( lastCell );
            stocksVolumeCells.add( volumeCell );

            System.out.println( lastCell + " " + volumeCell );

            double v = requestDouble( lastCell, conversation );

            System.out.println( "V : " + v );
//
//            if ( v == 0 ) {
//                break;
//            }

            if ( row > 500 ) {
                break;
            }

            row++;
        }

        initStocksCeels = true;

    }

    @Override
    public void updateData( DDEClientConversation conversation ) {

        if ( !initStocksCeels ) {
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

    }


}
