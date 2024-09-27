package api.tws;

import api.Downloader;
import exp.Exp;
import locals.IJson;
import myJson.MyJson;
import options.Call;
import options.Options;
import options.Put;
import options.Strike;
import tws.MyContract;
import tws.TwsContractsEnum;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TwsHandler implements IJson {

    // Variables
    Map<TwsContractsEnum, MyContract> myContracts = new HashMap<>();

    // Constructor
    public TwsHandler() {
    }

    public void removeMyContract(int id) {
        myContracts.remove(id);
    }

    public void addContract(MyContract contract) {
        myContracts.put(contract.getType(), contract);
    }

    public MyContract getMyContract(TwsContractsEnum twsContractsEnum) throws NullPointerException {
        if (myContracts.containsKey(twsContractsEnum)) {
            return myContracts.get(twsContractsEnum);
        }
        throw new NullPointerException("Contract not exist: " + twsContractsEnum);
    }

    public boolean isRequested(TwsContractsEnum id) throws Exception {
        MyContract myContract = myContracts.get(id);
        if (myContract != null) {
            return myContract.isRequested();
        } else {
            throw new Exception("No contract with this id: " + id);
        }
    }

    public boolean isExist(MyContract contract) {
        return myContracts.containsValue(contract);
    }

    public boolean isExist(TwsContractsEnum twsContractsEnum) {
        return myContracts.containsKey(twsContractsEnum);
    }


    public boolean isRequested(MyContract myContract) throws Exception {
        return isRequested(myContract.getType());
    }

    public void request(MyContract contract) {
        try {
            if (!isRequested(contract)) {
                Downloader.getInstance().reqMktData(contract.getMyId(), contract);
                contract.setRequested(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void request(TwsContractsEnum id) throws Exception {
        MyContract contract = getMyContract(id);
        request(contract);
    }

    public void requestOptions(List<Exp> optionsList) {
        for (Exp exp : optionsList) {
            requestOptions(exp.getOptions());
        }
    }

    public void requestOptions(Options options) {
        for (Strike strike : options.getStrikes()) {
            try {

                // Sleep
//                Thread.sleep( 200 );

                // ----- Call ----- //
                Call call = strike.getCall();
                request(call.getMyContract());

                // ----- Put ----- //
                Put put = strike.getPut();
                request(put.getMyContract());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        options.setRequested(true);
    }

    public Map<TwsContractsEnum, MyContract> getMyContracts() {
        return myContracts;
    }

    @Override
    public MyJson getAsJson() {
        MyJson json = new MyJson();
        for (Map.Entry<TwsContractsEnum, MyContract> entry : myContracts.entrySet()) {
            MyContract contract = entry.getValue();
            json.put(contract.getType().toString(), contract.getAsJson());
        }
        return json;
    }

    @Override
    public void loadFromJson(MyJson json) {
        for (Map.Entry<TwsContractsEnum, MyContract> entry : myContracts.entrySet()) {
            MyContract contract = entry.getValue();
            contract.loadFromJson(json.getMyJson(contract.getType().toString()));
        }
    }

    @Override
    public MyJson getResetJson() {
        return getAsJson();
    }
}

