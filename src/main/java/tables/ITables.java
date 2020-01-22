package tables;

import tables.status.TableStatusfather;
import tables.status.TablesArraysFather;

public interface ITables {

    TableDayFather getTableDay();

    TableSumFather getTableSum();

    TablesArraysFather getTableArrays();

    TableStatusfather getTableStatus();

}
