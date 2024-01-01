package arik.alerts;

import arik.Arik;
import dataBase.mySql.MySql;
import tws.accounts.ConnectionsAndAccountHandler;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Jibe_Positions_Algo extends ArikAlgoAlert {

    public static Transaction transaction;
    int session_id;

    // Constructor

    public Jibe_Positions_Algo(double target_price_for_position, int session_id) {
        super(target_price_for_position);
        this.session_id = session_id;
    }

    @Override
    public void go() {

        transaction = read_transaction(MySql.Queries.get_transaction(session_id, MySql.JIBE_PROD_CONNECTION));

        System.out.println();
        System.out.println(transaction);
        System.out.println();

        // No position
        if (!POSITION) {
            // New transaction
            if ((transaction.close_reason == null || transaction.close_reason == "") && transaction.created_at != null) {
                POSITION = true;
                send_enter_transaction_alert(transaction.transaction_type, transaction.index_at_creation);
                send_order();
            }
        }

        // In position
        if (POSITION) {
            if (transaction.close_reason != null && transaction.close_reason != "") {
                POSITION = false;
                send_close_transaction_alert(transaction.transaction_type, transaction.index_at_close);
                send_order();
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

    private void send_order() {
        if (Arik.allow_trading) {
            ConnectionsAndAccountHandler.send_order_all_accounts();
        }
    }
}


