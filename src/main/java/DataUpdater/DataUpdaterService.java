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

        String op_avg_15_continue = client.getMySqlService().getDataBaseHandler().get_table_loc(IDataBaseHandler.OP_AVG_15_CONITNUE_TABLE);
        String op_avg_60_continue = client.getMySqlService().getDataBaseHandler().get_table_loc(IDataBaseHandler.OP_AVG_15_CONITNUE_TABLE);
        String op_avg_240_continue = client.getMySqlService().getDataBaseHandler().get_table_loc(IDataBaseHandler.OP_AVG_15_CONITNUE_TABLE);

        Exp day = getClient().getExps().getExp(ExpStrings.day);
        day.setOp_avg_15_continue(MySql.Queries.handle_rs(MySql.Queries.get_last_record(op_avg_15_continue)));
        day.setOp_avg_60_continue(MySql.Queries.handle_rs(MySql.Queries.get_last_record(op_avg_60_continue)));
        day.setOp_avg_240_continue(MySql.Queries.handle_rs(MySql.Queries.get_last_record(op_avg_240_continue)));

        // Corr and de corr
        String corr_mix_table = client.getMySqlService().getDataBaseHandler().get_table_loc(IDataBaseHandler.CORR_MIX_TABLE);
        String de_corr_mix_table = client.getMySqlService().getDataBaseHandler().get_table_loc(IDataBaseHandler.DE_CORR_MIX_TABLE);

        try {
            double corr_mix_cdf = MySql.Queries.handle_rs(MySql.Queries.get_sum(corr_mix_table));
            double de_corr_mix_cdf = MySql.Queries.handle_rs(MySql.Queries.get_sum(de_corr_mix_table));

            double corr_mix = MySql.Queries.handle_rs(MySql.Queries.get_last_record(corr_mix_table));
            double de_corr_mix = MySql.Queries.handle_rs(MySql.Queries.get_last_record(de_corr_mix_table));

            client.setCorr_mix_cdf(corr_mix_cdf);
            client.setDe_corr_mix_cdf(de_corr_mix_cdf);
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
