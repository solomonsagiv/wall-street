package tables;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class TableSumFather {

	@Id
	@Column( name = "id" )
	private int id;
	@Column( name = "date" )
	private String date;
	@Column( name = "exp_name" )
	private String exp_name;
	@Column( name = "open" )
	private double open;
	@Column( name = "high" )
	private double high;
	@Column( name = "low" )
	private double low;
	@Column( name = "close" )
	private double close;
	@Column( name = "con_up" )
	private int con_up;
	@Column( name = "con_down" )
	private int con_down;
	@Column( name = "index_up" )
	private int index_up;
	@Column( name = "index_down" )
	private int index_down;
	@Column( name = "op_avg" )
	private double op_avg;
	@Column( name = "base" )
	private double base;
	@Column( name = "options" )
	private String options;
	@Column( name = "con_bid_ask_counter" )
	private int conBidAskCounter;


	public TableSumFather () {
	}

	public TableSumFather ( String date, String exp_name, double open, double high, double low, double close,
	                        int future_up, int future_down, int index_up, int index_down, double op_avg, double base, String options,
	                        int conBidAskCounter ) {
		this.date = date;
		this.exp_name = exp_name;
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
		this.con_up = future_up;
		this.con_down = future_down;
		this.index_up = index_up;
		this.index_down = index_down;
		this.op_avg = op_avg;
		this.base = base;
		this.options = options;
		this.conBidAskCounter = conBidAskCounter;
	}

	public int getId () {
		return id;
	}

	public void setId ( int id ) {
		this.id = id;
	}

	public String getDate () {
		return date;
	}

	public void setDate ( String date ) {
		this.date = date;
	}

	public String getExp_name () {
		return exp_name;
	}

	public void setExp_name ( String exp_name ) {
		this.exp_name = exp_name;
	}

	public double getOpen () {
		return open;
	}

	public void setOpen ( double open ) {
		this.open = open;
	}

	public double getHigh () {
		return high;
	}

	public void setHigh ( double high ) {
		this.high = high;
	}

	public double getLow () {
		return low;
	}

	public void setLow ( double low ) {
		this.low = low;
	}

	public double getClose () {
		return close;
	}

	public void setClose ( double close ) {
		this.close = close;
	}

	public int getCon_up () {
		return con_up;
	}

	public void setCon_up ( int con_up ) {
		this.con_up = con_up;
	}

	public int getCon_down () {
		return con_down;
	}

	public void setCon_down ( int con_down ) {
		this.con_down = con_down;
	}

	public int getIndex_up () {
		return index_up;
	}

	public void setIndex_up ( int index_up ) {
		this.index_up = index_up;
	}

	public int getIndex_down () {
		return index_down;
	}

	public void setIndex_down ( int index_down ) {
		this.index_down = index_down;
	}

	public double getOp_avg () {
		return op_avg;
	}

	public void setOp_avg ( double op_avg ) {
		this.op_avg = op_avg;
	}

	public double getBase () {
		return base;
	}

	public void setBase ( double base ) {
		this.base = base;
	}

	public String getOptions () {
		return options;
	}

	public void setOptions ( String options ) {
		this.options = options;
	}

	public int getConBidAskCounter () {
		return conBidAskCounter;
	}

	public void setConBidAskCounter ( int conBidAskCounter ) {
		this.conBidAskCounter = conBidAskCounter;
	}

	@Override
	public String toString () {
		return "TableSumFather [id=" + id + ", date=" + date + ", exp_name=" + exp_name + ", open=" + open + ", high="
				+ high + ", low=" + low + ", close=" + close + ", future_up=" + con_up + ", future_down=" + con_down
				+ ", index_up=" + index_up + ", index_down=" + index_down + ", op_avg=" + op_avg + ", base=" + base
				+ ", options=" + options + "]";
	}

}