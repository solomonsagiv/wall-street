package tables.status;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table( name = "status" )
public class StocksStatusTable extends TableStatusfather {

    public StocksStatusTable( int id, String name, String time, double con, double ind, int conUp, int conDown,
                              int indUp, int indDown, double base, double open, double high, double low, double optimiMove,
                              double pesimiMove, int futBdCounter, String options ) {

        super( id, name, time, con, ind, conUp, conDown, indUp, indDown, base, open, high, low,
                optimiMove, pesimiMove, futBdCounter, options );
    }

    public StocksStatusTable( int id, String name ) {
        super( id, name );
    }

    public StocksStatusTable() {
        super( );
    }

}
