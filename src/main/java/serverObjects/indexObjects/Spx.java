package serverObjects.indexObjects;

import DDE.DDECells;
import DDE.DDECellsEnum;
import api.tws.requesters.SpxRequester;
import options.IndexOptions;
import options.OptionsDDeCells;
import options.OptionsEnum;
import options.OptionsHandler;
import serverObjects.ApiEnum;
import tws.TwsContractsEnum;

import java.time.LocalTime;

public class Spx extends INDEX_CLIENT_OBJECT {

    static Spx client = null;

    // Constructor
    public Spx() {
        setName( "spx" );
        setRacesMargin( 0.3 );
        setIndexBidAskMargin( .5 );
        setDbId( 2 );
        setStrikeMargin( 5 );
        setBaseId( 10000 );
        initDDECells();
        setIndexStartTime(LocalTime.of(16, 30, 0));
        setIndexEndTime(LocalTime.of(23, 0, 0));
        setFutureEndTime(LocalTime.of(23, 15, 0));
        setiTwsRequester(new SpxRequester());
        roll( );
    }

    private void roll() {
//        rollHandler = new RollHandler( this );
//
//        Roll quarter_quarterFar = new Roll( getOptionsHandler( ).getOptions( OptionsEnum.QUARTER ), getOptionsHandler( ).getOptions( OptionsEnum.QUARTER_FAR ) );
//        rollHandler.addRoll( RollEnum.QUARTER_QUARTER_FAR, quarter_quarterFar );

    }

    // Get instance
    public static Spx getInstance() {
        if ( client == null ) {
            client = new Spx( );
        }
        return client;
    }

    @Override
    public void initOptionsHandler() throws NullPointerException {

        // Fut Quarter
        OptionsDDeCells quarterDDeCells = new OptionsDDeCells( "R19C2", "R19C1", "R19C3" );
        IndexOptions quarterOptions = new IndexOptions( getBaseId( ) + 3000, this, OptionsEnum.QUARTER, TwsContractsEnum.OPT_QUARTER, quarterDDeCells );
        
        // Fut Quarter far
        OptionsDDeCells quarterFarDDeCells = new OptionsDDeCells( "R21C2", "R21C1", "R21C3" );
        IndexOptions quarterFarOptions = new IndexOptions( getBaseId( ) + 4000, this, OptionsEnum.QUARTER_FAR, TwsContractsEnum.OPT_QUARTER_FAR, quarterFarDDeCells );

        OptionsHandler optionsHandler = new OptionsHandler( this );
        optionsHandler.addOptions( quarterOptions );
        optionsHandler.addOptions( quarterFarOptions );
        optionsHandler.setMainOptions( quarterOptions );
        setOptionsHandler( optionsHandler );
    }

        @Override
        public void initDDECells ( ) {
            DDECells ddeCells = new DDECells( ) {
                @Override
                public boolean isWorkWithDDE() {
                    return true;
                }
            };

            // Ind
            ddeCells.addCell( DDECellsEnum.IND_BID, "R2C2" );
            ddeCells.addCell( DDECellsEnum.IND, "R2C3" );
            ddeCells.addCell( DDECellsEnum.IND_ASK, "R2C4" );

            ddeCells.addCell( DDECellsEnum.OPEN, "R10C4" );
            ddeCells.addCell( DDECellsEnum.HIGH, "R10C1" );
            ddeCells.addCell( DDECellsEnum.LOW, "R10C2" );
            ddeCells.addCell( DDECellsEnum.BASE, "R8C5" );

            setDdeCells( ddeCells );
        }

        @Override
        public ApiEnum getApi ( ) {
            return ApiEnum.DDE;
        }

        @Override
        public void requestApi ( ) {

        }

    @Override
    public void initBaseId() {
        setBaseId(10000);
    }

    @Override
        public double getTheoAvgMargin ( ) {
            return 0.05;
        }


    }
