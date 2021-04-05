package lists;

import exp.Exp;
import serverObjects.BASE_CLIENT_OBJECT;
import service.MyBaseService;
import service.ServiceEnum;

import java.time.LocalDateTime;

// Regular list updater
public class ListsService extends MyBaseService {

    // Variables
    BASE_CLIENT_OBJECT client;

    // Constructor
    public ListsService(BASE_CLIENT_OBJECT client) {
        super(client);
        this.client = client;
    }

    @Override
    public void go() {
        insert();
    }

    @Override
    public String getName() {
        return "lists";
    }

    @Override
    public int getSleep() {
        return 1000;
    }

    @Override
    public ServiceEnum getType() {
        return ServiceEnum.REGULAR_LISTS;
    }

    private void insert() {

        // List for charts
        for (Exp exp : client.getExps().getExpList()) {
            try {
                exp.getOpFutList().add(exp.get_op());
                exp.add_op();

                if (exp.get_future() > 1) {
                    exp.getOpFutList().add(exp.get_future() - client.getIndex());
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }
}