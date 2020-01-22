package tables.stocks.sum;

import tables.StockTableSum;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table( name = "netflix_sum" )
public class NetflixSum extends StockTableSum {

    public NetflixSum() {
        super( );
    }

    public NetflixSum( String date, String exp_name, double open, double high, double low, double close, int future_up,
                       int future_down, int index_up, int index_down, double op_avg, double base, String options,
                       int conBidAskCounter ) {
        super( date, exp_name, open, high, low, close, future_up, future_down, index_up, index_down, op_avg, base, options,
                conBidAskCounter );
        // TODO Auto-generated constructor stub
    }


}