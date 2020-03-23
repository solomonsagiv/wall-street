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
    public BASE_CLIENT_OBJECT client;
    private HashMap< OptionsEnum, Options > optionsMap = new HashMap<>( );
    private ArrayList< Options > optionsList = new ArrayList<>( );
    private PositionCalculator positionCalculator;
    Options mainOptions;

    public Options getOptions( OptionsEnum options ) {
        return optionsMap.get( options );
    }

    // Constructor
    public OptionsHandler( INDEX_CLIENT_OBJECT client ) {
        this.client = client;
        initOptions();
        initMainOptions();
        positionCalculator = new PositionCalculator( client );
    }

    public OptionsHandler( STOCK_OBJECT client ) {
        this.client = client;
        initOptions();
        initMainOptions();
        positionCalculator = new PositionCalculator( client );
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
                object.put( options.getType().toString(), options.getOptionsAsJson( ) );
            } else {
                object.put( options.getType().toString(), options.getOptionsAsJson( ) );
            }
        }
        return object;
    }

    public JSONObject getAllOptionsEmptyJson() {
        JSONObject object = new JSONObject( );
        for ( Options options : getOptionsList( ) ) {
            object.put( options.getType().toString(), options.getEmptyOptionsAsJson( ) );
        }
        return object;
    }

    private void initStartEndStrikes( double future ) {

        double last = L.modulu( future );
        double margin = client.getStrikeMargin();

        double startStrike = last - ( margin * 10 );
        double endStrike = last + ( margin * 10 );

        client.setStartStrike( startStrike );
        client.setEndStrike( endStrike );

    }

    public void initOptions( double future ) {

        initStartEndStrikes( future );

        for ( Options options : getOptionsList( ) ) {
            System.out.println( "Init options: " + options.getType().toString() );
            options.initOptions( );
            System.out.println( options.toStringVertical( ) );
        }

    }

    public JSONObject getOptionsProps() {

        JSONObject json = new JSONObject( );

        for ( Options options : getOptionsList( ) ) {
            json.put( options.getType().toString(), options.getProps( ) );
        }

        return json;
    }

    public JSONObject getEmptyOptionsProps() {

        JSONObject json = new JSONObject( );

        for ( Options options : getOptionsList( ) ) {
            json.put( options.getType( ).toString(), options.getEmptyProps( ) );
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

    public Options getMainOptions() {
        return mainOptions;
    }

    public void setMainOptions( Options mainOptions ) {
        this.mainOptions = mainOptions;
    }


    @Override
    public String toString() {
        return "OptionsHandler{" +
                "optionsList=" + optionsList +
                '}';
    }
}

interface IOptionsHandler {
    void initOptions();
    void initMainOptions();
}
