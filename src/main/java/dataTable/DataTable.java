package dataTable;

import java.util.ArrayList;

public class DataTable {

    ArrayList< RowData > rows = new ArrayList<>( );

    public void addRow( RowData rowData ) {
        rows.add( rowData );
    }

    public ArrayList< RowData > getRows() {
        return rows;
    }
}



