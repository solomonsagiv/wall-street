package service;

import serverObjects.BASE_CLIENT_OBJECT;

public abstract class MyBaseService implements IMyService {

    public static final int LOGIC = 0;
    public static final int OP_AVG_MOVE = 1;
    public static final int EQUAL_MOVE = 2;
    public static final int REGULAR_LISTS = 3;
    public static final int MYSQL_RUNNER = 4;

    BASE_CLIENT_OBJECT client;
    String name;
    int type;
    int sleep;

    public MyBaseService( BASE_CLIENT_OBJECT client, String name, int type, int sleep ) {
        this.client = client;
        this.name = name;
        this.type = type;
        this.sleep = sleep;
    }

    public void execute( int sleepCount ) {
        if ( sleepCount % sleep == 0 ) {
            go();
        }
    }

    // --------- Getters and setters --------- //
    public BASE_CLIENT_OBJECT getClient() {
        return client;
    }
    public String getName() {
        return name;
    }
    public int getType() {
        return type;
    }
    public int getSleep() {
        return sleep;
    }
}
