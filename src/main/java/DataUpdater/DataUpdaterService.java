package DataUpdater;

import exp.Exp;
import serverObjects.BASE_CLIENT_OBJECT;
import service.MyBaseService;

public class DataUpdaterService extends MyBaseService {

    public DataUpdaterService(BASE_CLIENT_OBJECT client) {
        super(client);
    }

    @Override
    public void go() {
        for (Exp exp: client.getExps().getExpList()) {
            exp.setOp_avg_15(exp.get_op_avg(900));
            exp.setOp_avg_60(exp.get_op_avg(3600));
        }
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
