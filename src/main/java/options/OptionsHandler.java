package options;

import locals.L;
import options.fullOptions.PositionCalculator;
import org.json.JSONObject;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.INDEX_CLIENT_OBJECT;
import serverObjects.stockObjects.STOCK_OBJECT;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class OptionsHandler implements IOptionsHandler {

    // Variables
    BASE_CLIENT_OBJECT client;
    private HashMap< OptionsEnum, Options > optionsMap = new HashMap<>( );
    private ArrayList< Options > optionsList = new ArrayList<>( );
    private PositionCalculator positionCalculator;

    public Options getOptions( OptionsEnum options ) {
        return optionsMap.get( options );
    }

    // Constructor
    public OptionsHandler( INDEX_CLIENT_OBJECT client ) {
        this.client = client;

        positionCalculator = new PositionCalculator( client );

        optionsDay = new IndexOptions( client, OptionsEnum.WEEK, client.getTwsData( ).getOptionsDayContract( ) );
        getOptionsList( ).add( optionsDay );
        getOptionsMap( ).put( optionsDay.getType( ), optionsDay );

        optionsMonth = new IndexOptions( client, OptionsEnum.MONTH, client.getTwsData( ).getOptionMonthContract( ) );
        getOptionsList( ).add( optionsMonth );
        getOptionsMap( ).put( optionsMonth.getType( ), optionsMonth );

        optionsQuarter = new IndexOptions( client, OptionsEnum.QUARTER, client.getTwsData( ).getOptionsQuarterContract( ) );
        getOptionsList( ).add( optionsQuarter );
        getOptionsMap( ).put( optionsQuarter.getType( ), optionsQuarter );

    }

    public OptionsHandler( STOCK_OBJECT client ) {
        this.client = client;

        positionCalculator = new PositionCalculator( client );

        optionsDay = new StockOptions( client, OptionsEnum.WEEK, client.getTwsData( ).getOptionsDayContract( ) );
        getOptionsList( ).add( optionsDay );
        getOptionsMap( ).put( optionsDay.getType( ), optionsDay );

        optionsMonth = new StockOptions( client, OptionsEnum.MONTH, client.getTwsData( ).getOptionMonthContract( ) );
        getOptionsList( ).add( optionsMonth );
        getOptionsMap( ).put( optionsMonth.getType( ), optionsMonth );

        optionsQuarter = new StockOptions( client, OptionsEnum.QUARTER, client.getTwsData( ).getOptionsQuarterContract( ) );
        getOptionsList( ).add( optionsQuarter );
        getOptionsMap( ).put( optionsQuarter.getType( ), optionsQuarter );

    }

    public void addOptions( Options options ) {
        getOptionsList( ).add( options );
        getOptionsMap( ).put( options.getType( ), options );
    }

    // Functions
    public JSONObject getAllOptionsAsJson() {
        JSONObject object = new JSONObject( );
        for ( Options options : getOptionsList( ) ) {

            if ( options.getOptionsAsJson( ).length( ) == 0 || !client.isStarted( ) ) {
                object.put( options.getName( ), options.getOptionsAsJson( ) );
            } else {
                object.put( options.getName( ), options.getOptionsAsJson( ) );
            }

        }
        return object;
    }


    public JSONObject getAllOptionsEmptyJson() {

        JSONObject object = new JSONObject( );
        for ( Options options : getOptionsList( ) ) {
            object.put( options.getName( ), options.getEmptyOptionsAsJson( ) );
        }

        return object;

    }

    private void initStartEndStrikes( double future ) {

        double last = L.modulu( future );
        double margin = L.modulu( last * 0.03 );
        double startStrike = last - margin;
        double endStrike = last + margin;

        client.setStartStrike( startStrike );
        client.setEndStrike( endStrike );

    }

    public void initOptions( double future ) {

        initStartEndStrikes( future );

        for ( Options options : getOptionsList( ) ) {
            System.out.println( "Init options: " + options.getName( ) );
            options.initOptions( );

            System.out.println( options.toStringVertical( ) );
        }

        client.getTwsRequestHandler( ).startRunner( );

    }


    public JSONObject getOptionsProps() {

        JSONObject json = new JSONObject( );

        for ( Options options : getOptionsList( ) ) {
            json.put( options.getName( ), options.getProps( ) );
        }

        return json;
    }

    public JSONObject getEmptyOptionsProps() {

        JSONObject json = new JSONObject( );

        for ( Options options : getOptionsList( ) ) {
            json.put( options.getName( ), options.getEmptyProps( ) );
        }

        return json;
    }


    // Getters and setters
    public HashMap< OptionsEnum, Options > getOptionsMap() {
        return optionsMap;
    }

    public void setOptionsMap( HashMap< OptionsEnum, Options > optionsMap ) {
        this.optionsMap = optionsMap;
    }

    public ArrayList< Options > getOptionsList() {
        return optionsList;
    }

    public void setOptionsList( ArrayList< Options > optionsList ) {
        this.optionsList = optionsList;
    }

    public PositionCalculator getPositionCalculator() {
        return positionCalculator;
    }

    public void setPositionCalculator( PositionCalculator positionCalculator ) {
        this.positionCalculator = positionCalculator;
    }



}

interface IOptionsHandler {
    void initOptions();
}
