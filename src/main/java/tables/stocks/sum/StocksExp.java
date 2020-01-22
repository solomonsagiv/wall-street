package tables.stocks.sum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "exp_data" )
public class StocksExp {

    @Id
    @Column( name = "stock_name" )
    private String stockName;
    @Column( name = "start_price" )
    private double startPrice;
    @Column( name = "future_races" )
    private int futureRaces;
    @Column( name = "index_races" )
    private int indexRaces;


    // Empty constructor
    public StocksExp() {
    }

    // Constructor
    public StocksExp( String stockName, double startPrice, int futureRaces, int indexRaces ) {
        this.stockName = stockName;
        this.startPrice = startPrice;
        this.futureRaces = futureRaces;
        this.indexRaces = indexRaces;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName( String stockName ) {
        this.stockName = stockName;
    }

    public double getStartPrice() {
        return startPrice;
    }

    public void setStartPrice( double startPrice ) {
        this.startPrice = startPrice;
    }

    public int getFutureRaces() {
        return futureRaces;
    }

    public void setFutureRaces( int futureRaces ) {
        this.futureRaces = futureRaces;
    }

    public int getIndexRaces() {
        return indexRaces;
    }

    public void setIndexRaces( int indexRaces ) {
        this.indexRaces = indexRaces;
    }

    @Override
    public String toString() {
        return "StocksExp [stockName=" + stockName + ", startPrice=" + startPrice + ", futureRaces=" + futureRaces
                + ", indexRaces=" + indexRaces + "]";
    }

}