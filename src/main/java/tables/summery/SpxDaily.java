package tables.summery;

import tables.IndexTableSum;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table( name = "spx_daily" )
public class SpxDaily extends IndexTableSum {

    public SpxDaily() {
        super( );
    }

    public SpxDaily( String date, String exp_name, double open, double high, double low, double close, int future_up,
                     int future_down, int index_up, int index_down, double op_avg, int fut_bid_ask_counter, double base,
                     String options, int conBidAskCounter, double equalMove ) {
        super( date, exp_name, open, high, low, close, future_up, future_down, index_up, index_down, op_avg, fut_bid_ask_counter,
                base, options, conBidAskCounter, equalMove );
    }

}
