package api;

import dataBase.mySql.myTables.TablesEnum;
import org.jfree.data.time.Second;
import serverObjects.indexObjects.Spx;
import serverObjects.stockObjects.Apple;

import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;

public class Test {

    public static void main( String[] args ) {

        Apple apple = Apple.getInstance();
//        apple.getTablesHandler().getTable( TablesEnum.STATUS ).update();
        apple.getTablesHandler().getTable( TablesEnum.STATUS ).load();

    }

}