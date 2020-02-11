package options;

import locals.L;
import options.fullOptions.PositionCalculator;
import org.json.JSONObject;
import serverObjects.BASE_CLIENT_OBJECT;

import java.util.ArrayList;
import java.util.HashMap;

public class OptionsHandler {

    // Variables
    BASE_CLIENT_OBJECT client;
    private Options optionsMonth;
    private Options optionsDay;
    private Options optionsQuarter;
    private HashMap< Integer, Options > optionsMap;
    private ArrayList< Options > optionsList;
    private PositionCalculator positionCalculator;

    // Constructor
    public OptionsHandler( BASE_CLIENT_OBJECT client ) {
        this.client = client;
        optionsList = new ArrayList<>( );
        optionsMap = new HashMap<>( );

        positionCalculator = new PositionCalculator( client );

        optionsDay = new Options( client, Options.DAY, client.getTwsData( ).getOptionsDayContract( ) );
        getOptionsList( ).add( optionsDay );
        getOptionsMap( ).put( optionsDay.getType(), optionsDay );

        optionsMonth = new Options( client, Options.MONTH, client.getTwsData( ).getOptionMonthContract( ) );
        getOptionsList( ).add( optionsMonth );
        getOptionsMap( ).put( optionsMonth.getType(), optionsMonth );

        optionsQuarter = new Options( client, Options.QUARTER, client.getTwsData( ).getOptionsQuarterContract( ) );
        getOptionsList( ).add( optionsQuarter );
        getOptionsMap( ).put( optionsQuarter.getType(), optionsQuarter );

    }

    // Functions
    public JSONObject getAllOptionsAsJson() {
        JSONObject object = new JSONObject( );
        for ( Options options : getOptionsList( ) ) {

            if (options.getOptionsJson().getVal().length() == 0 || !client.isStarted()) {
                object.put( options.getName( ), options.getOptionsJson().getValByCurrentCalc() );
            } else {
                object.put( options.getName( ), options.getOptionsJson().getVal() );
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
    public Options getOptionsQuarter() {
        return optionsQuarter;
    }

    public Options getOptionsDay() {
        return optionsDay;
    }

    public Options getOptionsMonth() {
        return optionsMonth;
    }

    public void setOptionsMonth( Options optionsMonth ) {
        this.optionsMonth = optionsMonth;
    }

    public Options getMainOptions() {
        return optionsMonth;
    }

    public HashMap< Integer, Options > getOptionsMap() {
        return optionsMap;
    }

    public void setOptionsMap( HashMap< Integer, Options > optionsMap ) {
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
