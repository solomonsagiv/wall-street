package tables.daily;

import tables.IndexTableDay;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table( name = "ndx" )
public class NdxTable extends IndexTableDay {

    public NdxTable() {
        super( );
    }

    public NdxTable( String date, String exp_name, String time, double con, double index, int future_up,
                     int future_down, int index_up, int index_down, String options, double base, double opAvg,
                     double conDay, double conMonth, double conQuarter, double conQuarterFar, int future_bid_ask_counter,
                     int con_bid_ask_counter, double equalMove, double opAvg15, double conDay1, double conMonth1, double conQuarter1,
                     double conQuarterFar1 ) {

        super( date, exp_name, time, con, index, future_up, future_down, index_up, index_down,
                options, base, opAvg, conDay, conMonth, conQuarter, conQuarterFar, future_bid_ask_counter,
                con_bid_ask_counter, equalMove, opAvg15, conDay1, conMonth1, conQuarter1, conQuarterFar1 );
    }
}
