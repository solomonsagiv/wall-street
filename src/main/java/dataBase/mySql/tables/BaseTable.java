package dataBase.mySql.tables;

import serverObjects.BASE_CLIENT_OBJECT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseTable implements IMYsqlTable {

    // Variables
    String name;
    List< TableComps.MyTableColumn > columns;

    BASE_CLIENT_OBJECT client;

    // Constructor
    public BaseTable( BASE_CLIENT_OBJECT client ) {
        this.client = client;
        columns = new ArrayList<>();
        initColumns();
    }

    public String getName() {
        return name;
    }
    public void setName( String name ) {
        this.name = name;
    }


    public List< TableComps.MyTableColumn > getColumns() {
        return columns;
    }
    public void setColumns( List< TableComps.MyTableColumn > columns ) {
        this.columns = columns;
    }
}
