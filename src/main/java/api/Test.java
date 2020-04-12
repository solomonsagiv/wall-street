package api;

import dataBase.mySql.mySqlComps.TablesEnum;
import serverObjects.stockObjects.Apple;

public class Test {

    public static void main( String[] args ) {

        Apple apple = Apple.getInstance();
        apple.getTablesHandler().getTable(TablesEnum.SETTING).load();


    }

}