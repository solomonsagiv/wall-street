package tws;

import com.ib.client.Contract;
import java.util.HashMap;
import java.util.Map;

public abstract class TwsData {

    Map< TwsContractsEnum, Contract > contractsMap = new HashMap<>();

    private int indexId;
    private int futureId;
    private int contractDetailsId;
    private int quantity = 0;



    public TwsData() {}

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

    @Override
    public String toString() {
        return "TwsData{" +
                "contractsMap=" + contractsMap +
                ", indexId=" + indexId +
                ", futureId=" + futureId +
                ", quantity=" + quantity +
                '}';
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

    public int getContractDetailsId() {
        return contractDetailsId;
    }

    public void setContractDetailsId(int contractDetailsId) {
        this.contractDetailsId = contractDetailsId;
    }
}
