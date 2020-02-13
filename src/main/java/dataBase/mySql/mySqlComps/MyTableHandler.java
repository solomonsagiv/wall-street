package dataBase.mySql.mySqlComps;

import dataBase.mySql.tables.*;
import serverObjects.BASE_CLIENT_OBJECT;

public class MyTableHandler {

    // Variables
    BASE_CLIENT_OBJECT client;
    MyDayTable myDayTable;
    MyStatusTable myStatusTable;
    MyArraysTable myArraysTable;
    MySumTable mySumTable;
    MyBoundsTable myBoundsTable;

    // Constructor
    public MyTableHandler( BASE_CLIENT_OBJECT client, MyDayTable myDayTable, MySumTable mySumTable ) {

        this.client = client;
        this.myDayTable = myDayTable;
        this.mySumTable = mySumTable;

        myStatusTable= new MyStatusTable( client, "status" );
        myArraysTable = new MyArraysTable( client, "arrays" );
        myBoundsTable = new MyBoundsTable( client, "bounds" );

    }

    public MyDayTable getMyDayTable() {
        return myDayTable;
    }
    public MyStatusTable getMyStatusTable() {
        return myStatusTable;
    }
    public MyArraysTable getMyArraysTable() {
        return myArraysTable;
    }
    public MySumTable getMySumTable() {
        return mySumTable;
    }
    public MyBoundsTable getMyBoundsTable() {
        return myBoundsTable;
    }
}
