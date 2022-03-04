package DataUpdater;

import dataBase.mySql.MySql;
import dataBase.mySql.dataUpdaters.IDataBaseHandler;
import exp.Exp;
import exp.ExpStrings;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Ndx;
import serverObjects.indexObjects.Spx;
import service.MyBaseService;

public class DataUpdaterService extends MyBaseService {

    public DataUpdaterService(BASE_CLIENT_OBJECT client) {
        super(client);
    }

    @Override
    public void go() {

        String op_avg_5 = client.getMySqlService().getDataBaseHandler().get_table_loc(IDataBaseHandler.OP_AVG_DAY_5_TABLE);
        String op_avg_15 = client.getMySqlService().getDataBaseHandler().get_table_loc(IDataBaseHandler.OP_AVG_DAY_15_TABLE);
        String op_avg_60 = client.getMySqlService().getDataBaseHandler().get_table_loc(IDataBaseHandler.OP_AVG_DAY_60_TABLE);
        String op_avg_240_continue = client.getMySqlService().getDataBaseHandler().get_table_loc(IDataBaseHandler.OP_AVG_240_CONITNUE_TABLE);

        Exp day = getClient().getExps().getExp(ExpStrings.day);
        day.setOp_avg_5(MySql.Queries.handle_rs(MySql.Queries.get_last_record(op_avg_5)));
        day.setOp_avg_15(MySql.Queries.handle_rs(MySql.Queries.get_last_record(op_avg_15)));
        day.setOp_avg_60(MySql.Queries.handle_rs(MySql.Queries.get_last_record(op_avg_60)));
        day.setOp_avg_240_continue(MySql.Queries.handle_rs(MySql.Queries.get_last_record(op_avg_240_continue)));

        // Corr and de corr
        String corr_15_table = client.getMySqlService().getDataBaseHandler().get_table_loc(IDataBaseHandler.CORR_15);
        String corr_60_table = client.getMySqlService().getDataBaseHandler().get_table_loc(IDataBaseHandler.CORR_60);
        String de_corr_15_table = client.getMySqlService().getDataBaseHandler().get_table_loc(IDataBaseHandler.DE_CORR_15);
        String de_corr_60_table = client.getMySqlService().getDataBaseHandler().get_table_loc(IDataBaseHandler.DE_CORR_60);

        String corr_mix_cdf_table = client.getMySqlService().getDataBaseHandler().get_table_loc(IDataBaseHandler.CORR_MIX_CDF);
        String de_corr_mix_cdf_table = client.getMySqlService().getDataBaseHandler().get_table_loc(IDataBaseHandler.DE_CORR_MIX_CDF);

        try {
            double corr_15 = MySql.Queries.handle_rs(MySql.Queries.get_last_record(corr_15_table));
            double corr_60 = MySql.Queries.handle_rs(MySql.Queries.get_last_record(corr_60_table));

            double de_corr_15 = MySql.Queries.handle_rs(MySql.Queries.get_last_record(de_corr_15_table));
            double de_corr_60 = MySql.Queries.handle_rs(MySql.Queries.get_last_record(de_corr_60_table));

            double corr_mix_cdf = MySql.Queries.handle_rs(MySql.Queries.get_last_record(corr_mix_cdf_table));
            double de_corr_mix_cdf = MySql.Queries.handle_rs(MySql.Queries.get_last_record(de_corr_mix_cdf_table));

            client.setCorr_15(corr_15);
            client.setCorr_60(corr_60);
            client.setDe_corr_15(de_corr_15);
            client.setDe_corr_60(de_corr_60);
            client.setCorr_mix_cdf(corr_mix_cdf);
            client.setDe_corr_mix_cdf(de_corr_mix_cdf);
        } catch (Exception e ) {
            e.printStackTrace();
        }

        // DF N AVG
        df_n_avg();

    }

    private void df_n_avg() {
        if (client instanceof Spx) {
            String df_table = client.getMySqlService().getDataBaseHandler().get_table_loc(IDataBaseHandler.DECISION_FUNCTION_TABLE);
            double df_n_avg = MySql.Queries.handle_rs(MySql.Queries.get_sum_from_df(df_table, 504, 3));
            client.setDf_n_avg(df_n_avg);
        }
        if (client instanceof Ndx) {
            String df_table = client.getMySqlService().getDataBaseHandler().get_table_loc(IDataBaseHandler.DECISION_FUNCTION_TABLE);
            double df_n_avg = MySql.Queries.handle_rs(MySql.Queries.get_sum_from_df(df_table, 604, 4));
            client.setDf_n_avg(df_n_avg);
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
