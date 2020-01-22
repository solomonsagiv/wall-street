package tables.stocks.sum;

import tables.StockTableSum;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table( name = "facebook_sum" )
public class FacebookSum extends StockTableSum {

    public FacebookSum() {
        super( );
    }

    public FacebookSum( String date, String exp_name, double open, double high, double low, double close, int future_up,
                        int future_down, int index_up, int index_down, double op_avg, double base, String options,
                        int conBidAskCounter ) {
        super( date, exp_name, open, high, low, close, future_up, future_down, index_up, index_down, op_avg, base, options,
                conBidAskCounter );
        // TODO Auto-generated constructor stub
    }


}