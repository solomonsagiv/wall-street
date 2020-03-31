package api;

import org.jfree.data.time.Second;
import serverObjects.indexObjects.Spx;

import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;

public class Test {

    public static void main( String[] args ) throws SQLException {
        Properties properties = new Properties();

        properties.put( 4, "sds" );
        properties.put( Props.IND, new Random(  ).nextDouble() * 100 );

    }

}

enum Props {
    IND
}
