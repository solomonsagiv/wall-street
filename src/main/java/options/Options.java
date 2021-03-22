package options;

import exp.Exp;
import serverObjects.BASE_CLIENT_OBJECT;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Options {

    protected Exp exp;
    OptionsDDeCells optionsDDeCells;
    List< Strike > strikes;
    HashMap< Integer, Option > optionsMap;
    BASE_CLIENT_OBJECT client;

    public Options( BASE_CLIENT_OBJECT client, Exp exp ) {
        this.client = client;
        this.exp = exp;
        strikes = new ArrayList<>( );
        optionsMap = new HashMap<>( );
    }

    public Call getCall( double targetStrike ) {
        for ( Strike strike : strikes ) {
            if ( targetStrike == strike.getStrike( ) ) {
                return strike.getCall( );
            }
        }
        return null;
    }

    public Put getPut( double targetStrike ) {
        for ( Strike strike : strikes ) {
            if ( targetStrike == strike.getStrike( ) ) {
                return strike.getPut( );
            }
        }
        return null;
    }

    public void removeStrike( double strikeToRemove ) {
        for ( Strike strike : getStrikes( ) ) {
            if ( strikeToRemove == strike.getStrike( ) ) {
                getStrikes( ).remove( strike );
            }
        }

        for ( Map.Entry< Integer, Option > entry : optionsMap.entrySet( ) ) {
            Option option = entry.getValue( );
            if ( strikeToRemove == option.getStrike( ) ) {
                optionsMap.remove( option );
            }
        }
    }

    public HashMap< Integer, Option > getOptionsMap() {
        return optionsMap;
    }

    public Option getOption( String name ) {

        double targetStrike = Double.parseDouble( name.substring( 1 ) );

        for ( Strike strike : strikes ) {
            if ( strike.getStrike( ) == targetStrike ) {
                if ( name.toLowerCase( ).contains( "c" ) ) {
                    return strike.getCall( );
                } else {
                    return strike.getPut( );
                }
            }
        }
        return null;
    }

    double dividend = 0;

    public Option getOption( String side, double targetStrike ) {
        for ( Strike strike : strikes ) {
            if ( strike.getStrike( ) == targetStrike ) {
                if ( side.toLowerCase( ).contains( "c" ) ) {
                    return strike.getCall( );
                } else {
                    return strike.getPut( );
                }
            }
        }
        return null;
    }

    public Option getOption( Class c, double targetStrike ) {
        for ( Strike strike : strikes ) {
            if ( strike.getStrike( ) == targetStrike ) {
                if ( c == Call.class ) {
                    return strike.getCall( );
                } else {
                    return strike.getPut( );
                }
            }
        }
        return null;
    }

    // Return single strike by strike price (double)
    public Strike getStrike( double strikePrice ) {
        for ( Strike strike : strikes ) {
            if ( strikePrice == strike.getStrike( ) ) {
                return strike;
            }
        }
        return null;
    }

    // Return list of strikes prices
    public ArrayList< Double > getStrikePricesList() {
        ArrayList< Double > list = new ArrayList<>( );
        strikes.forEach( strike -> list.add( strike.getStrike( ) ) );
        return list;
    }

    // Remove strike from strikes arr by strike class
    public void removeStrike( Strike strike ) {
        strikes.remove( strike );
    }

    // Add strike to strikes arr
    public void addStrike( Strike strike ) {

        boolean contains = getStrikePricesList( ).contains( strike.getStrike( ) );

        // Not inside
        if ( !contains ) {
            strikes.add( strike );
        }
    }

    public Option getOptionById( int id ) {
        return optionsMap.get( id );
    }

    // Set option in strikes arr
    public List< Strike > getStrikes() {
        return strikes;
    }

    public void setStrikes( List< Strike > strikes ) {
        this.strikes = strikes;
    }

    public String toStringVertical() {
        String string = "";

        for ( Strike strike : strikes ) {
            string += strike.toString( ) + "\n\n";
        }
        return string;
    }

    public double floor( double d, int zeros ) {
        return Math.floor( d * zeros ) / zeros;
    }

    public String str( Object o ) {
        return String.valueOf( o );
    }

    public LocalDate getToDay() {
        return LocalDate.now( );
    }


    // ---------- Basic Functions ---------- //
    private double dbl( String s ) {
        return Double.parseDouble( s );
    }

    private int INT( String s ) {
        return Integer.parseInt( s );
    }

    public OptionsDDeCells getOptionsDDeCells() {
        return optionsDDeCells;
    }


}