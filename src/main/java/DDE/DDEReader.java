package DDE;

import com.pretty_tools.dde.DDEException;
import com.pretty_tools.dde.client.DDEClientConversation;
import dataTable.RowData;
import exp.Exp;
import gui.mainWindow.ConnectionPanel;
import locals.L;
import locals.LocalHandler;
import options.Options;
import serverObjects.ApiEnum;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.INDEX_CLIENT_OBJECT;
import threads.MyThread;

public class DDEReader extends MyThread implements Runnable {

    // Variables
    int sleep = 150;
    DDEClientConversation conversation;
    private DDEConnection ddeConnection = new DDEConnection( );

    // Constructor
    public DDEReader( BASE_CLIENT_OBJECT client ) {
        super( client );
        conversation = ddeConnection.createNewConversation( ConnectionPanel.excelLocationField.getText( ) );
    }

    @Override
    public void initRunnable() {
        setRunnable( this );
    }

    @Override
    public void run() {

        while ( isRun( ) ) {
            try {
                // Sleep
                Thread.sleep( sleep );

                // DDE
                read( );

                // Add row data
                appendDataLine( );

            } catch ( InterruptedException e ) {
                break;
            } catch ( DDEException e ) {
                // TODO
                e.printStackTrace( );
            } catch ( Exception e ) {
                e.printStackTrace( );
            }
        }
    }

    private void appendDataLine() {
        if ( client.isStarted( ) ) {
            client.getDataTable( ).addRow( new RowData( client.getIndex( ), client.getIndexBid( ), client.getIndexAsk( ),
                    client.getFutDay( ), client.getFutWeek( ), client.getFutMonth( ), client.getFutQuarter( ), client.getFutQuarterFar( ) ) );
        }
    }

    private void read() throws DDEException {
        for ( BASE_CLIENT_OBJECT client : LocalHandler.clients ) {
            if ( client.getApi( ) == ApiEnum.DDE ) {
                updateData( ( INDEX_CLIENT_OBJECT ) client );
            }
        }
    }

    private void updateData( INDEX_CLIENT_OBJECT client ) throws DDEException {

        // Options
        for ( Exp exp : client.getExps( ).getExpList( ) ) {

            Options options = exp.getOptions( );

            if ( options.getOptionsDDeCells( ) != null ) {
                exp.setCalcFut( requestDouble( options.getOptionsDDeCells( ).getFut( ) ) );
            }
        }

        // Index
        client.setIndex( requestDouble( client.getDdeCells( ).getCell( DDECellsEnum.IND ) ) );
        client.setIndexBid( requestDouble( client.getDdeCells( ).getCell( DDECellsEnum.IND_BID ) ) );
        client.setIndexAsk( requestDouble( client.getDdeCells( ).getCell( DDECellsEnum.IND_ASK ) ) );

        // Ticker
        client.setOpen( requestDouble( client.getDdeCells( ).getCell( DDECellsEnum.OPEN ) ) );
        client.setHigh( requestDouble( client.getDdeCells( ).getCell( DDECellsEnum.HIGH ) ) );
        client.setLow( requestDouble( client.getDdeCells( ).getCell( DDECellsEnum.LOW ) ) );
        client.setBase( requestDouble( client.getDdeCells( ).getCell( DDECellsEnum.BASE ) ) );
        client.setFutDay( requestDouble( client.getDdeCells( ).getCell( DDECellsEnum.FUT_DAY ) ) );
        client.setFutWeek( requestDouble( client.getDdeCells( ).getCell( DDECellsEnum.FUT_WEEK ) ) );
        client.setFutMonth( requestDouble( client.getDdeCells( ).getCell( DDECellsEnum.FUT_MONTH ) ) );
        client.setFutQuarter( requestDouble( client.getDdeCells( ).getCell( DDECellsEnum.E1 ) ) );
        client.setFutQuarterFar( requestDouble( client.getDdeCells( ).getCell( DDECellsEnum.E2 ) ) );

    }

    public double requestDouble( String cell ) {
        double d = 0;
        try {
            d = L.dbl( conversation.request( cell ) );
        } catch ( NumberFormatException | DDEException e ) {
            // TODO
        } finally {
            return d;
        }
    }

}