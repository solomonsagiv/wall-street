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
    private Options mainOptions;
    private Options optionsMonth;
    private Options optionsDay;
    private Options optionsQuarter;
    private Options optionsQuarterFar;
    private HashMap< Integer, Options > optionsMap = new HashMap<>( );
    private ArrayList< Options > optionsList = new ArrayList<>( );
    private PositionCalculator positionCalculator;

    // Constructor
    public OptionsHandler( BASE_CLIENT_OBJECT client ) {
        this.client = client;
        positionCalculator = new PositionCalculator( client );
    }

    // Functions
    public JSONObject getAllOptionsAsJson() {
        JSONObject object = new JSONObject( );
        for ( Options options : getOptionsList( ) ) {
            object.put( options.getName( ), options.getOptionsJson().getVal() );
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

        if ( optionsQuarter == null ) {
            optionsQuarter = new Options( client, Options.QUARTER, client.getTwsData( ).getOptionsQuarterContract( ) );
        }

        return optionsQuarter;
    }

    public void setOptionsQuarter( Options optionsQuarter ) {
        this.optionsQuarter = optionsQuarter;
    }

    public Options getOptionsQuarterFar() {

        if ( optionsQuarterFar == null ) {
            optionsQuarterFar = new Options( client, Options.QUARTER_FAR, client.getTwsData( ).getOptionsQuarterFarContract( ) );
        }

        return optionsQuarterFar;
    }

    public void setOptionsQuarterFar( Options optionsQuarterFar ) {
        this.optionsQuarterFar = optionsQuarterFar;
    }

    public Options getOptionsDay() {
        if ( optionsDay == null ) {
            optionsDay = new Options( client, Options.DAY, client.getTwsData( ).getOptionsDayContract( ) );
        }
        return optionsDay;
    }

    public void setOptionsDay( Options optionsDay ) {
        this.optionsDay = optionsDay;
    }

    public Options getOptionsMonth() {
        if ( optionsMonth == null ) {
            optionsMonth = new Options( client, Options.MONTH, client.getTwsData( ).getOptionMonthContract( ) );
        }
        return optionsMonth;
    }

    public void setOptionsMonth( Options optionsMonth ) {
        this.optionsMonth = optionsMonth;
    }

    public Options getMainOptions() {
        return mainOptions;
    }

    public void setMainOptions( Options mainOptions ) {
        this.mainOptions = mainOptions;
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
