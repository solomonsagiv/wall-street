package tables.status;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class StocksArraysTable extends TablesArraysFather {

	public StocksArraysTable ( int id, String name ) {
		super ( id, name );
	}

	public StocksArraysTable () {
	}


}
