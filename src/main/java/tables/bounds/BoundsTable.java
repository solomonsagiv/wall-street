package tables.bounds;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "bounds" )
public class BoundsTable {

	@Id
	@Column( name = "id" )
	private int id;

	@Column( name = "stockName" )
	private String stockName;

	@Column( name = "name" )
	private String name;

	@Column( name = "x" )
	private int x;

	@Column( name = "y" )
	private int y;

	@Column( name = "width" )
	private int width;

	@Column( name = "height" )
	private int height;

	public BoundsTable () {
	}

	public BoundsTable ( String stockName, String name, int x, int y, int width, int height ) {
		this.stockName = stockName;
		this.name = name;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}


	public String getStockName () {
		return stockName;
	}

	public void setStockName ( String stockName ) {
		this.stockName = stockName;
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

	public int getX () {
		return x;
	}

	public void setX ( int x ) {
		this.x = x;
	}

	public int getY () {
		return y;
	}

	public void setY ( int y ) {
		this.y = y;
	}

	public int getWidth () {
		return width;
	}

	public void setWidth ( int width ) {
		this.width = width;
	}

	public int getHeight () {
		return height;
	}

	public void setHeight ( int height ) {
		this.height = height;
	}


	@Override
	public String toString () {
		return "BoundsTable{" +
				"id=" + id +
				", stockName='" + stockName + '\'' +
				", name='" + name + '\'' +
				", x=" + x +
				", y=" + y +
				", width=" + width +
				", height=" + height +
				'}';
	}

}
