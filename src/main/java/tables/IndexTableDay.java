package tables;

import api.Manifest;
import arik.Arik;
import dataBase.HB;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.NdxCLIENTObject;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

@MappedSuperclass
public class IndexTableDay extends TableDayFather {

    @Column( name = "fut_bid_ask_counter" )
    private int future_bid_ask_counter;
    @Column( name = "con_bid_ask_counter" )
    private int con_bid_ask_counter;
    @Column( name = "equalMove" )
    private double equalMove;
    @Column( name = "op_avg15" )
    private double opAvg15;
    @Column( name = "conDay" )
    private double conDay = 0;
    @Column( name = "conMonth" )
    private double conMonth = 0;
    @Column( name = "conQuarter" )
    private double conQuarter = 0;
    @Column( name = "conQuarterFar" )
    private double conQuarterFar = 0;
    public IndexTableDay() {
        super( );
    }

    public IndexTableDay( String date, String exp_name, String time, double con, double index, int future_up,
                          int future_down, int index_up, int index_down, String options, double base,
                          double opAvg, double conDay, double conMonth, double conQuarter, double conQuarterFar,
                          int future_bid_ask_counter, int con_bid_ask_counter, double equalMove, double opAvg15,
                          double conDay1, double conMonth1, double conQuarter1, double conQuarterFar1 ) {

        super( date, exp_name, time, con, index, future_up, future_down, index_up, index_down, options,
                base, opAvg, conDay, conMonth, conQuarter, conQuarterFar );

        this.future_bid_ask_counter = future_bid_ask_counter;
        this.con_bid_ask_counter = con_bid_ask_counter;
        this.equalMove = equalMove;
        this.opAvg15 = opAvg15;
        this.conDay = conDay1;
        this.conMonth = conMonth1;
        this.conQuarter = conQuarter1;
        this.conQuarterFar = conQuarterFar1;
    }

    public static void main( String[] args ) {
        NdxCLIENTObject.getInstance( ).getTablesHandler( ).getStatusHandler( ).getHandler( ).resetData( );
    }

    public int getFuture_bid_ask_counter() {
        return future_bid_ask_counter;
    }

    public void setFuture_bid_ask_counter( int future_bid_ask_counter ) {
        this.future_bid_ask_counter = future_bid_ask_counter;
    }

    public int getCon_bid_ask_counter() {
        return con_bid_ask_counter;
    }

    public void setCon_bid_ask_counter( int con_bid_ask_counter ) {
        this.con_bid_ask_counter = con_bid_ask_counter;
    }

    public double getEqualMove() {
        return equalMove;
    }

    public void setEqualMove( double equalMove ) {
        this.equalMove = equalMove;
    }

    public double getOpAvg15() {
        return opAvg15;
    }

    public void setOpAvg15( double opAvg15 ) {
        this.opAvg15 = opAvg15;
    }

    public double getConDay() {
        return conDay;
    }

    public void setConDay( double conDay ) {
        this.conDay = conDay;
    }

    public double getConMonth() {
        return conMonth;
    }

    public void setConMonth( double conMonth ) {
        this.conMonth = conMonth;
    }

    public double getConQuarter() {
        return conQuarter;
    }

    public void setConQuarter( double conQuarter ) {
        this.conQuarter = conQuarter;
    }

    public double getConQuarterFar() {
        return conQuarterFar;
    }

    public void setConQuarterFar( double conQuarterFar ) {
        this.conQuarterFar = conQuarterFar;
    }

    // To list
    public ArrayList< Object > toList() {
        ArrayList< Object > list = new ArrayList<>( );
        list.add( getTime( ) );
        list.add( getCon( ) );
        list.add( getIndex( ) );
        list.add( getCon_up( ) );
        list.add( getCon_down( ) );
        list.add( getIndex_up( ) );
        list.add( getIndex_down( ) );
        list.add( future_bid_ask_counter );
        return list;
    }

    @Override
    public String toString() {
        return "IndexTableDay{" +
                "future_bid_ask_counter=" + future_bid_ask_counter +
                ", con_bid_ask_counter=" + con_bid_ask_counter +
                ", equalMove=" + equalMove +
                ", opAvg15=" + opAvg15 +
                ", conDay=" + conDay +
                ", conMonth=" + conMonth +
                ", conQuarter=" + conQuarter +
                ", conQuarterFar=" + conQuarterFar +
                '}';
    }

    // Handler
    public static class Handler implements ITablesHandler {

        BASE_CLIENT_OBJECT client;

        public Handler( BASE_CLIENT_OBJECT client ) {
            this.client = client;
        }

        @Override
        public void insertLine() {

            try {

                IndexTableDay table = ( IndexTableDay ) getTableObject( );
                HB.save( table, client.getTables( ).getTableDay( ).getClass( ).getName( ), client.getSessionfactory( ) );

            } catch ( Exception e ) {
                e.printStackTrace();
                Arik.getInstance( ).sendMessage( Arik.sagivID, client.getName( ) + " MYSQL exception \n" + e.getCause( ),
                        null );
            }

        }

        @Override
        public Object getTableObject() {

            IndexTableDay table = new IndexTableDay( );
            table.setDate( LocalDate.now( ).toString( ) );
            table.setExp_name( Manifest.EXP );
            table.setTime( LocalTime.now( ).toString( ) );
            table.setCon( client.getOptionsHandler( ).getMainOptions( ).getContract().getVal() );
            table.setConDay( client.getOptionsHandler( ).getOptionsDay( ).getContract().getVal() );
            table.setConMonth( client.getOptionsHandler( ).getOptionsMonth( ).getContract().getVal() );
            table.setConQuarter( client.getOptionsHandler( ).getOptionsQuarter( ).getContract().getVal() );
            table.setIndex( client.getIndex( ).getVal() );
            table.setIndex_up( client.getIndexUp( ) );
            table.setIndex_down( client.getIndexDown( ) );
            table.setBase( client.getBase( ).getVal() );
            table.setCon_up( client.getConUp( ) );
            table.setCon_down( client.getConDown( ) );
            table.setOptions( client.getOptionsHandler( ).getMainOptions( ).getOptionsJson().getVal() );
            table.setOpAvg( client.getOptionsHandler( ).getMainOptions( ).getOpAvg().getVal() );
            table.setOpAvg15( client.getOptionsHandler( ).getMainOptions( ).getOpAvg15( ) );
            table.setEqualMove( client.getOptionsHandler( ).getMainOptions( ).getEqualMoveCalculator( ).getMove( ).getVal() );
            table.setCon_bid_ask_counter( client.getOptionsHandler( ).getMainOptions( ).getContractBidAskCounter( ) );

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

