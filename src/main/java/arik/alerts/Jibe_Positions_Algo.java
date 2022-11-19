package arik.alerts;

import arik.Arik;
import dataBase.mySql.MySql;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Jibe_Positions_Algo extends ArikAlgoAlert {

    public static Transaction transaction;
    final int session_id = 1224;

    // Constructor

    public Jibe_Positions_Algo(double target_price_for_position) {
        super(target_price_for_position);
    }

    @Override
    public void go() {

        transaction = read_transaction(MySql.Queries.get_transaction(session_id));

        System.out.println();
        System.out.println(transaction);
        System.out.println();

        // No position
        if (!POSITION) {
            // New transaction
            if ((transaction.close_reason == null || transaction.close_reason == "") && transaction.created_at != null) {
                POSITION = true;
                send_enter_transaction_alert(transaction.transaction_type, transaction.index_at_creation);
            }
        }

        // In position
        if (POSITION) {
            if (transaction.close_reason != null && transaction.close_reason != "") {
                POSITION = false;
                send_close_transaction_alert(transaction.transaction_type, transaction.index_at_close);
            }
        }

    }

    private Transaction read_transaction(ResultSet rs) {

        Transaction transaction = new Transaction();

        while (true) {
            try {
                if (!rs.next()) break;
                transaction.transaction_type = rs.getString("position_type");
                transaction.created_at = rs.getDate("created_at");
                transaction.close_reason = rs.getString("close_reason");
                transaction.index_at_creation = rs.getDouble("index_value_at_creation");
                transaction.index_at_close = rs.getDouble("index_value_at_close");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
        return transaction;

    }

    public static String positions_text() {
        return transaction.toString();
    }

    private void send_enter_transaction_alert(String position_type, double index_at_created) {
        String text = "SPX Enter %s\n" +
                "%s";
        Arik.getInstance().sendMessageToSlo(String.format(text, position_type, index_at_created));
    }

    private void send_close_transaction_alert(String position_type, double index_at_close) {
        String text = "SPX Exit %s\n" +
                "%s";
        Arik.getInstance().sendMessageToSlo(String.format(text, position_type, index_at_close));
    }
}

class Transaction {

    Date created_at;
    String transaction_type;
    String close_reason;
    double index_at_creation, index_at_close;

    @Override
    public String toString() {
        return "Transaction " + "\n\n" +
                "  created_at:        " + created_at + "\n" +
                "  transaction_type:  " + transaction_type + '\'' + "\n" +
                "  close_reason:      " + close_reason + '\'' + "\n" +
                "  index_at_creation: " + index_at_creation + "\n" +
                "  index_at_close:    " + index_at_close + "\n";
    }
}