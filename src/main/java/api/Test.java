package api;

import dataBase.mySql.mySqlComps.TablesEnum;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.Second;
import serverObjects.stockObjects.Apple;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

public class Test {

    public static void main( String[] args ) throws InterruptedException, ParseException {

        Second second = new Second(  );
        System.out.println(second );

        Thread.sleep( 2000 );

        System.out.println(new Second(  ) );
    }

}