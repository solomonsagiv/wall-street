package api;

import myJson.MyJson;
import options.JsonStrings;
import serverObjects.indexObjects.Spx;

public class Test {

    public static void main( String[] args ) {

        Spx spx = Spx.getInstance( );

        MyJson json = new MyJson(  );

        MyJson e1 = spx.getAsJson( ).getMyJson( JsonStrings.e1 );
        json.put( JsonStrings.tomorrowFut, "dsdsdsdsdsdsd" );

        json.put( JsonStrings.e1, e1 );

        System.out.println(json.toString(
                4
        ) );

    }

}