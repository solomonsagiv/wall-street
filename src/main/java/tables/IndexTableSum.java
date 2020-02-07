package tables;

import api.Manifest;
import dataBase.HB;
import serverObjects.BASE_CLIENT_OBJECT;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

@MappedSuperclass
public class IndexTableSum extends TableSumFather {

    @Column( name = "fut_bid_ask_counter" )
    private int fut_bid_ask_counter;
    @Column( name = "equalMove" )
    private double equalMove;

    public IndexTableSum() {
        super( );
    }

    public IndexTableSum( String date, String exp_name, double open, double high, double low, double close,
                          int future_up, int future_down, int index_up, int index_down, double op_avg, int fut_bid_ask_counter, double base, String options, int conBidAskCounter, double equalMove ) {
        super( date, exp_name, open, high, low, close, future_up, future_down, index_up, index_down, op_avg, base, options, conBidAskCounter );

        this.fut_bid_ask_counter = fut_bid_ask_counter;
        this.equalMove = equalMove;
    }

    public int getFut_bid_ask_counter() {
        return fut_bid_ask_counter;
    }

    public void setFut_bid_ask_counter( int fut_bid_ask_counter ) {
        this.fut_bid_ask_counter = fut_bid_ask_counter;
    }

    public double getEqualMove() {
        return equalMove;
    }

    public void setEqualMove( double equalMove ) {
        this.equalMove = equalMove;
    }

    public static class Handler implements ITablesHandler {

        BASE_CLIENT_OBJECT client;

        public Handler( BASE_CLIENT_OBJECT client ) {
            this.client = client;
        }

        @Override
        public void insertLine() {
            IndexTableSum table = ( IndexTableSum ) getTableObject( );
            HB.save( table, client.getTables( ).getTableSum( ).getClass( ).getName( ), client.getSessionfactory( ) );
        }

        @Override
        public Object getTableObject() {
            IndexTableSum table = new IndexTableSum( );
            table.setDate( LocalDate.now( ).toString( ) );
            table.setExp_name( Manifest.EXP );
            table.setOpen( client.getOpen( ).getVal() );
            table.setHigh( client.getHigh( ).getVal() );
            table.setLow( client.getLow( ).getVal() );
            table.setClose( client.getIndex( ).getVal() );
            table.setCon_up( client.getConUp( ) );
            table.setCon_down( client.getConDown( ) );
            table.setIndex_up( client.getIndexUp( ) );
            table.setIndex_down( client.getIndexDown( ) );
            table.setOp_avg( client.getOptionsHandler( ).getMainOptions( ).getOpAvg( ).getVal() );
            table.setBase( client.getBase( ).getVal() );
            table.setOptions( client.getOptionsHandler( ).getMainOptions( ).getOptionsJson().getVal().toString() );
            table.setConBidAskCounter( client.getOptionsHandler( ).getMainOptions( ).getContractBidAskCounter( ) );
            table.setEqualMove( client.getOptionsHandler( ).getMainOptions( ).getEqualMoveCalculator( ).getMove( ).getVal() );
            return table;
        }

        @Override
        public void loadData() {

        }

        @Override
        public void resetData() {

        }

        @Override
        public void updateData() {

        }

        @Override
        public void updateObject() {

        }
    }

}
