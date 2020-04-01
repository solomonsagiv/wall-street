package api.tws;

import api.Downloader;
import options.Call;
import options.Options;
import options.Put;
import options.Strike;
import tws.MyContract;
import tws.TwsContractsEnum;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TwsHandler {

    // Variables
    Map< TwsContractsEnum, MyContract > myContracts = new HashMap<>();

    // Constructor
    public TwsHandler() {}

    public void removeMyContract( int id ) {
        myContracts.remove( id );
    }

    public void addContract( MyContract contract ) {
        myContracts.put( contract.getType( ), contract );
    }

    public MyContract getMyContract( TwsContractsEnum twsContractsEnum ) throws NullPointerException {
        if ( myContracts.containsKey( twsContractsEnum ) ) {
            return myContracts.get( twsContractsEnum );
        }
        throw new NullPointerException( "Contract not exist: " + twsContractsEnum );
    }

    public boolean isRequested( TwsContractsEnum id ) throws Exception {
        MyContract myContract = myContracts.get( id );
        if ( myContract != null ) {
            return myContract.isRequested( );
        } else {
            throw new Exception( "No contract with this id: " + id );
        }
    }

    public boolean isRequested( MyContract myContract ) throws Exception {
        return isRequested( myContract.getType() );
    }

    public void request( MyContract contract ) {
        try {
            if ( !isRequested( contract ) ) {
                Downloader.getInstance( ).reqMktData( contract.getMyId( ), contract );
                contract.setRequested( true );
            }
        } catch ( Exception e ) {
            e.printStackTrace( );
        }
    }

    public void request( TwsContractsEnum id ) throws Exception {
        MyContract contract = getMyContract( id );
        request( contract );
    }

    public void requestOptions( List<Options> optionsList ) {
        for ( Options options: optionsList ) {
            requestOptions( options );
        }
    }

    public void requestOptions( Options options ) {
        for ( Strike strike : options.getStrikes( ) ) {
            try {

                // Sleep
                Thread.sleep( 100 );

                // ----- Call ----- //
                Call call = strike.getCall( );
                request( call.getMyContract( ) );

                // ----- Put ----- //
                Put put = strike.getPut( );
                request( put.getMyContract( ) );

            } catch ( Exception e ) {
                e.printStackTrace( );
            }
        }
        options.setRequested( true );
    }
}

