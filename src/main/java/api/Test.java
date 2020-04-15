package api;

import org.jfree.data.time.Second;

import java.text.ParseException;
import java.time.LocalDateTime;

public class Test {

    public static void main( String[] args ) throws InterruptedException, ParseException {

        String d = "Tue Apr 14 15:02:18 IDT 2020";

        LocalDateTime time = LocalDateTime.parse(d);

        Second second = new Second(time.getSecond(), time.getMinute(), time.getHour(), time.getDayOfMonth(), time.getMonth().getValue(), time.getYear());


        System.out.println("Seconde: " + second);
    }

}