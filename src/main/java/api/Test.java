package api;

import org.jfree.data.time.Second;

import java.sql.SQLException;

public class Test {

    public static void main( String[] args ) throws SQLException {
        System.out.println(new Second().getSecond());
    }

}
