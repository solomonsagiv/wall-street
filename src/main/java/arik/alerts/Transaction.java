package arik.alerts;

import java.sql.Date;

public class Transaction {

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
