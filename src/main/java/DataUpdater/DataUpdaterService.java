package DataUpdater;

import dataBase.mySql.MySql;
import dataBase.mySql.dataUpdaters.IDataBaseHandler;
import exp.Exp;
import exp.ExpStrings;
import serverObjects.BASE_CLIENT_OBJECT;
import service.MyBaseService;

public class DataUpdaterService extends MyBaseService {

    public DataUpdaterService(BASE_CLIENT_OBJECT client) {
        super(client);
    }

    @Override
    public void go() {

        String index_table = client.getMySqlService().getDataBaseHandler().get_table_loc(IDataBaseHandler.INDEX_TABLE);
        String day_fut_table = client.getMySqlService().getDataBaseHandler().get_table_loc(IDataBaseHandler.FUT_DAY_TABLE);

        Exp day = getClient().getExps().getExp(ExpStrings.day);
        day.setOp_avg_1(MySql.Queries.handle_rs(MySql.Queries.op_avg_by_rows(index_table, day_fut_table, 30)));
        day.setOp_avg_5_continue(MySql.Queries.handle_rs(MySql.Queries.op_avg_by_rows(index_table, day_fut_table, 150)));
        day.setOp_avg_15_continue(MySql.Queries.handle_rs(MySql.Queries.op_avg_by_rows(index_table, day_fut_table, 450)));
        day.setOp_avg_60_continue(MySql.Queries.handle_rs(MySql.Queries.op_avg_by_rows(index_table, day_fut_table, 1800)));
        day.setOp_avg_240_continue(MySql.Queries.handle_rs(MySql.Queries.op_avg_by_rows(index_table, day_fut_table, 7000)));
    }

    @Override
    public String getName() {
        return client.getName() + " " + "Data updater service";
    }

    @Override
    public int getSleep() {
        return 10000;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        return str.toString();
    }
}
