package tables;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "exp_data" )
public class ExpData {

	@Id
	@Column( name = "id" )
	private int id;
	@Column( name = "name" )
	private String name;
	@Column( name = "start_price" )
	private double startPrice;
	@Column( name = "future_races" )
	private int futureRaces;
	@Column( name = "index_races" )
	private int indexRaces;
	@Column( name = "start_price_week" )
	private double startPriceWeek;
	@Column( name = "future_races_week" )
	private int futureRacesWeek;
	@Column( name = "index_races_week" )
	private int indexRacesWeek;
	@Column( name = "week_exp_date" )
	private int weekExpDate;

	public ExpData () {
	}


	public ExpData ( String name, double startPrice, int futureRaces, int indexRaces, double startPriceWeek,
	                 int futureRacesWeek, int indexRacesWeek, int weekExpDate ) {
		this.name = name;
		this.startPrice = startPrice;
		this.futureRaces = futureRaces;
		this.indexRaces = indexRaces;
		this.startPriceWeek = startPriceWeek;
		this.futureRacesWeek = futureRacesWeek;
		this.indexRacesWeek = indexRacesWeek;
		this.weekExpDate = weekExpDate;
	}


	public int getWeekExpDate () {
		return weekExpDate;
	}

	public void setWeekExpDate ( int weekExpDate ) {
		this.weekExpDate = weekExpDate;
	}

	public double getStartPriceWeek () {
		return startPriceWeek;
	}

	public void setStartPriceWeek ( double startPriceWeek ) {
		this.startPriceWeek = startPriceWeek;
	}

	public int getFutureRacesWeek () {
		return futureRacesWeek;
	}

	public void setFutureRacesWeek ( int futureRacesWeek ) {
		this.futureRacesWeek = futureRacesWeek;
	}

	public int getIndexRacesWeek () {
		return indexRacesWeek;
	}

	public void setIndexRacesWeek ( int indexRacesWeek ) {
		this.indexRacesWeek = indexRacesWeek;
	}

	public int getId () {
		return id;
	}

	public void setId ( int id ) {
		this.id = id;
	}

	public String getName () {
		return name;
	}

	public void setName ( String name ) {
		this.name = name;
	}

	public double getStartPrice () {
		return startPrice;
	}

	public void setStartPrice ( double startPrice ) {
		this.startPrice = startPrice;
	}

	public int getFutureRaces () {
		return futureRaces;
	}

	public void setFutureRaces ( int futureRaces ) {
		this.futureRaces = futureRaces;
	}

	public int getIndexRaces () {
		return indexRaces;
	}

	public void setIndexRaces ( int indexRaces ) {
		this.indexRaces = indexRaces;
	}

}
