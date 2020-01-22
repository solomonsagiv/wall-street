package tables;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.ArrayList;

@MappedSuperclass
public class StockTableDay extends TableDayFather {

	@Column( name = "con_bid_ask_counter" )
	private int con_bid_ask_counter;

	public StockTableDay() {
		super();
	}

	public StockTableDay( String date , String exp_name , String time , double future ,
	                      double index , int future_up , int future_down , int index_up , int index_down , String options , double base ,
	                      int conBidAskCounter , double opAvg, double conDay , double conMonth , double conQuarter , double conQuarterFar ) {
		super( date , exp_name , time , future , index , future_up , future_down , index_up , index_down , options , base ,
				opAvg, conDay, conMonth, conQuarter, conQuarterFar );

		this.con_bid_ask_counter = conBidAskCounter;
	}

	// To list
	public ArrayList < Object > toList() {
		ArrayList < Object > list = new ArrayList <>();
		list.add( getTime() );
		list.add( getCon() );
		list.add( getIndex() );
		list.add( getCon_up() );
		list.add( getCon_down() );
		list.add( getIndex_up() );
		list.add( getIndex_down() );
		return list;
	}

}
