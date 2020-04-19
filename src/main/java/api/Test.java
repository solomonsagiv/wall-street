package api;

import java.text.DecimalFormat;

public class Test {

    public static void main( String[] args ) {

        DecimalFormat df = new DecimalFormat( "#0000.00" );

        System.out.println(df.format(-1));

    }

}