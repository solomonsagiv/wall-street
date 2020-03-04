package tws;

import com.ib.client.Contract;

public class MyContract extends Contract {

    private int myId;
    private boolean requested;
    private TwsContractsEnum type;

    public MyContract( int myId, TwsContractsEnum type ) {
        this.myId = myId;
        this.type = type;
    }

    public int getMyId() {
        return myId;
    }

    public void setMyId( int myId ) {
        this.myId = myId;
    }

    public boolean isRequested() {
        return requested;
    }

    public void setRequested( boolean requested ) {
        this.requested = requested;
    }

    public TwsContractsEnum getType() {
        return type;
    }
}
