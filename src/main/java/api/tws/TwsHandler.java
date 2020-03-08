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
    Map< Integer, MyContract > myContracts = new HashMap<>();

    // Constructor
    public TwsHandler() {}

    public void removeMyContract( int id ) {
        myContracts.remove( id );
    }

    public void addContract( MyContract contract ) {
        myContracts.put( contract.getMyId( ), contract );
    }

    public MyContract getMyContract( int id ) throws Exception {
        MyContract myContract = myContracts.get( id );
        if ( myContract != null ) {
            return myContract;
        } else {
            throw new NullPointerException( "No contract with this id: " + id );
        }
    }

    public MyContract getMyContract( TwsContractsEnum twsContractsEnum ) throws NullPointerException {
        for ( Map.Entry< Integer, MyContract > entry: myContracts.entrySet()) {
            MyContract contract = entry.getValue();
            if ( twsContractsEnum == contract.getType() ) {
                return contract;
            }
        }

        throw new NullPointerException( "Contract not exist: " + twsContractsEnum );
    }

    public boolean isRequested( int id ) throws Exception {
        MyContract myContract = myContracts.get( id );
        if ( myContract != null ) {
            return myContract.isRequested( );
        } else {
            throw new Exception( "No contract with this id: " + id );
        }
    }

    public boolean isRequested( MyContract myContract ) throws Exception {
        return isRequested( myContract.getMyId() );
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

    public void request( int id ) throws Exception {
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

