package tables.daily;

import tables.StockTableDay;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table( name = "netflix" )
public class NetflixTable extends StockTableDay {

    public NetflixTable( String date, String exp_name, String time, double future, double index, int future_up,
                         int future_down, int index_up, int index_down, String options, double base, int futureOptionBidAskCounter, double opAvg, double conDay, double conMonth, double conQuarter, double conQuarterFar ) {
        super( date, exp_name, time, future, index, future_up, future_down, index_up, index_down, options, base, futureOptionBidAskCounter, opAvg, conDay, conMonth, conQuarter, conQuarterFar );
    }

    // Empty Constructor
    public NetflixTable() {
        super( );
    }

}
