package DataUpdater;

import dataBase.mySql.MySql;
import dataBase.mySql.dataUpdaters.IDataBaseHandler;
import exp.Exp;
import exp.ExpStrings;
import jibeDataGraber.DecisionsFunc;
import jibeDataGraber.DecisionsFuncFactory;
import serverObjects.BASE_CLIENT_OBJECT;
import service.MyBaseService;

import java.util.ArrayList;

public class DataUpdaterService extends MyBaseService {

    ArrayList<DecisionsFunc> df_list;

    public DataUpdaterService(BASE_CLIENT_OBJECT client) {
        super(client);
        df_list = new ArrayList<>();

        df_list.add(client.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_2));
        df_list.add(client.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_2_ROUND));

        df_list.add(client.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_7));
        df_list.add(client.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_7_ROUND));

        df_list.add(client.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_7_300));
        df_list.add(client.getDecisionsFuncHandler().get_decision_func(DecisionsFuncFactory.DF_7_900));
    }
    
    @Override
    public void go() {

        String op_avg_5 = client.getMySqlService().getDataBaseHandler().get_table_loc(IDataBaseHandler.OP_AVG_DAY_5_TABLE);
        String op_avg_60 = client.getMySqlService().getDataBaseHandler().get_table_loc(IDataBaseHandler.OP_AVG_DAY_60_TABLE);
        String op_avg_240_continue = client.getMySqlService().getDataBaseHandler().get_table_loc(IDataBaseHandler.OP_AVG_240_CONITNUE_TABLE);

        Exp day = getClient().getExps().getExp(ExpStrings.day);
        day.setOp_avg_5(MySql.Queries.handle_rs(MySql.Queries.get_last_record(op_avg_5)));
        day.setOp_avg_60(MySql.Queries.handle_rs(MySql.Queries.get_last_record(op_avg_60)));
        day.setOp_avg_240_continue(MySql.Queries.handle_rs(MySql.Queries.get_last_record(op_avg_240_continue)));

        // DF N AVG
        df_n_avg();
    }
    
    private void df_n_avg() {
        for (DecisionsFunc df : df_list) {
            try {
                if (df.isIs_cdf()) {
                    double value = MySql.Queries.handle_rs(MySql.Queries.get_sum_from_df(df.getTable_location(), df.getVersion(), df.getSession_id()));
                    df.setValue(value);
                } else {
                    double value = MySql.Queries.handle_rs(MySql.Queries.get_last_record(df.getTable_location(), df.getVersion(), df.getSession_id()));
                    df.setValue(value);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
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
