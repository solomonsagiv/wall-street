package tables.status;

import org.json.JSONObject;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalTime;

@MappedSuperclass
public abstract class TableStatusfather {

	@Id
	@Column( name = "id" )
	private int id;
	@Column( name = "name" )
	private String name = "";
	@Column( name = "time" )
	private String time = LocalTime.now().toString();
	@Column( name = "ind" )
	private double ind = 0;
	@Column( name = "conUp" )
	private int conUp = 0;
	@Column( name = "conDown" )
	private int conDown = 0;
	@Column( name = "indUp" )
	private int indUp = 0;
	@Column( name = "indDown" )
	private int indDown = 0;

	@Column( name = "base" )
	private double base = 0;
	@Column( name = "open" )
	private double open = 0;
	@Column( name = "high" )
	private double high = 0;
	@Column( name = "low" )
	private double low = 0;

	@Column( name = "optimiMove" )
	private double optimiMove = 0;
	@Column( name = "pesimiMove" )
	private double pesimiMove = 0;

	@Column( name = "futBdCounter" )
	private int futBdCounter = 0;
	@Column( name = "options" )
	private String options = new JSONObject().toString();

	public TableStatusfather( int id , String name , String time , double con , double ind , int conUp , int conDown ,
	                          int indUp , int indDown , double base , double open , double high , double low , double optimiMove ,
	                          double pesimiMove , int futBdCounter , String options ) {
		this.id = id;
		this.name = name;
		this.time = time;
		this.ind = ind;
		this.conUp = conUp;
		this.conDown = conDown;
		this.indUp = indUp;
		this.indDown = indDown;
		this.base = base;
		this.open = open;
		this.high = high;
		this.low = low;
		this.optimiMove = optimiMove;
		this.pesimiMove = pesimiMove;
		this.futBdCounter = futBdCounter;
		this.options = options;
	}

	public TableStatusfather() {
	}

	public TableStatusfather( int id , String name ) {
		this.id = id;
		this.name = name;
		this.time = LocalTime.now().toString();
	}

	public int getId() {
		return id;
	}

	public void setId( int id ) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}

	public double getInd() {
		return ind;
	}

	public void setInd( double ind ) {
		this.ind = ind;
	}

	public int getConUp() {
		return conUp;
	}

	public void setConUp( int conUp ) {
		this.conUp = conUp;
	}

	public int getConDown() {
		return conDown;
	}

	public void setConDown( int conDown ) {
		this.conDown = conDown;
	}

	public int getIndUp() {
		return indUp;
	}

	public void setIndUp( int indUp ) {
		this.indUp = indUp;
	}

	public int getIndDown() {
		return indDown;
	}

	public void setIndDown( int indDown ) {
		this.indDown = indDown;
	}

	public double getBase() {
		return base;
	}

	public void setBase( double base ) {
		this.base = base;
	}

	public double getOpen() {
		return open;
	}

	public void setOpen( double open ) {
		this.open = open;
	}

	public double getHigh() {
		return high;
	}

	public void setHigh( double high ) {
		this.high = high;
	}

	public double getLow() {
		return low;
	}

	public void setLow( double low ) {
		this.low = low;
	}

	public double getOptimiMove() {
		return optimiMove;
	}

	public void setOptimiMove( double optimiMove ) {
		this.optimiMove = optimiMove;
	}

	public double getPesimiMove() {
		return pesimiMove;
	}

	public void setPesimiMove( double pesimiMove ) {
		this.pesimiMove = pesimiMove;
	}

	public int getFutBdCounter() {
		return futBdCounter;
	}

	public void setFutBdCounter( int futBdCounter ) {
		this.futBdCounter = futBdCounter;
	}

	public String getTime() {
		return time;
	}

	public void setTime( String time ) {
		this.time = time;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions( String options ) {
		this.options = options;
	}

}
