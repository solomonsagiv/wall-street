package dataBase.mySql.myTables;

import dataBase.mySql.myBaseTables.MyTwsContractsTable;
import serverObjects.BASE_CLIENT_OBJECT;

public class TwsContractsTable extends MyTwsContractsTable {

    // Constructor
    public TwsContractsTable(BASE_CLIENT_OBJECT client) {
        super(client);
    }

    @Override
    public void initColumns() {

    }
}
