package tables;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class StockTableSum extends TableSumFather {

	public StockTableSum () {
	}

	public StockTableSum ( String date, String exp_name, double open, double high, double low, double close,
	                       int future_up, int future_down, int index_up, int index_down, double op_avg, double base, String options, int conBidAskCounter ) {
		super ( date, exp_name, open, high, low, close, future_up, future_down, index_up, index_down, op_avg, base, options, conBidAskCounter );
	}

}
