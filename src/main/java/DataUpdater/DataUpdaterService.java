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
        day.setOp_avg_15_continue(MySql.Queries.handle_rs(MySql.Queries.op_avg_by_rows(index_table, day_fut_table, 450)));
        day.setOp_avg_60_continue(MySql.Queries.handle_rs(MySql.Queries.op_avg_by_rows(index_table, day_fut_table, 1800)));
        day.setOp_avg_240_continue(MySql.Queries.handle_rs(MySql.Queries.op_avg_by_rows(index_table, day_fut_table, 7000)));


        // Curr and de curr
        String corr_mix_w_table = client.getMySqlService().getDataBaseHandler().get_table_loc(IDataBaseHandler.CORR_MIX_W_TABLE);
        String de_corr_mix_w_table = client.getMySqlService().getDataBaseHandler().get_table_loc(IDataBaseHandler.DE_CORR_MIX_W_TABLE);
        String corr_mix_table = client.getMySqlService().getDataBaseHandler().get_table_loc(IDataBaseHandler.CORR_MIX_TABLE);
        String de_corr_mix_table = client.getMySqlService().getDataBaseHandler().get_table_loc(IDataBaseHandler.DE_CORR_MIX_TABLE);

        try {
            double corr_mix_w = MySql.Queries.handle_rs(MySql.Queries.get_sum(corr_mix_w_table));
            double de_corr_mix_w = MySql.Queries.handle_rs(MySql.Queries.get_sum(de_corr_mix_w_table));
            double corr_mix = MySql.Queries.handle_rs(MySql.Queries.get_sum(corr_mix_table));
            double de_corr_mix = MySql.Queries.handle_rs(MySql.Queries.get_sum(de_corr_mix_table));

            client.setCorr_mix_w(corr_mix_w);
            client.setDe_corr_mix_w(de_corr_mix_w);
            client.setCorr_mix(corr_mix);
            client.setDe_corr_mix(de_corr_mix);
        } catch (Exception e ) {
            e.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return client.getName() + " " + "Data updater service";
    }

    @Override
    public int getSleep() {
        return 20000;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        return str.toString();
    }
}
