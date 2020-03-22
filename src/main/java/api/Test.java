package api;

import serverObjects.indexObjects.Spx;

import java.sql.SQLException;
import java.util.Scanner;

@EnableAsync
public class Test {

    public static void main( String[] args ) throws SQLException {

        Spx client = Spx.getInstance();

        while (true) {
            System.out.println();
            System.out.println("Enter BID");
            double bid = new Scanner(System.in).nextDouble();
            client.setIndexBid(bid);
            System.out.println("Enter ASK");
            double ask = new Scanner(System.in).nextDouble();
            client.setIndexAsk(ask);

            System.out.println();
            System.out.println(client.getIndexBidAskCounter());
        }

    }

}
