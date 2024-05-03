package arik.alerts;

import java.sql.Date;

public class Transaction {

    int session_id;
    Date created_at;
    String transaction_type;
    String close_reason;
    double index_at_creation, index_at_close;

    @Override
    public String toString() {
        return "Transaction{" +
                "session_id=" + session_id +
                ", created_at=" + created_at +
                ", transaction_type='" + transaction_type + '\'' +
                ", close_reason='" + close_reason + '\'' +
                ", index_at_creation=" + index_at_creation +
                ", index_at_close=" + index_at_close +
                '}';
    }
}
