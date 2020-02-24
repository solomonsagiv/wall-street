package tws;

import com.ib.client.Contract;

import java.util.HashMap;
import java.util.Map;

public class TwsData {

    Map< TwsContractsEnum, Contract > contractsMap;

    private int indexId;
    private int futureId;
    private int quantity = 0;

    public TwsData() {
        contractsMap = new HashMap<>( );
    }

    public Contract getContract( TwsContractsEnum twsContractsEnum ) {
        return contractsMap.get( twsContractsEnum );
    }

    public boolean isContractExist( TwsContractsEnum twsContractsEnum ) {
        return contractsMap.containsKey( twsContractsEnum );
    }

    public void appendTwsContract( TwsContractsEnum twsContractsEnum, Contract contract ) {
        if ( !isContractExist( twsContractsEnum ) ) contractsMap.put( twsContractsEnum, contract );
    }

    public void setContracts( TwsContractsEnum twsContractsEnum, Contract contract ) {
        contractsMap.put( twsContractsEnum, contract );
    }

    // Getters and Setters
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity( int quantity ) {
        this.quantity = quantity;
    }

    public int getIndexId() {
        return indexId;
    }

    public void setIndexId( int indexId ) {
        this.indexId = indexId;
    }

    public int getFutureId() {
        return futureId;
    }

    public void setFutureId( int futureId ) {
        this.futureId = futureId;
    }
}
