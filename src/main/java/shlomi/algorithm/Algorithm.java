package shlomi.algorithm;

import serverObjects.BASE_CLIENT_OBJECT;

public abstract class Algorithm implements IAlgorithm {

    // Variables
    BASE_CLIENT_OBJECT client;
    int type;

    // Constructor
    public Algorithm(BASE_CLIENT_OBJECT client, int type) {
        this.client = client;
        this.type = type;
    }

    //  Getters and Setters
    public BASE_CLIENT_OBJECT getClient() {
        return client;
    }

    public void setClient(BASE_CLIENT_OBJECT client) {
        this.client = client;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
