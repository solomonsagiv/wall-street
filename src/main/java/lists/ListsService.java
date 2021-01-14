package lists;

import exp.E;
import exp.Exp;
import roll.Roll;
import roll.RollEnum;
import serverObjects.BASE_CLIENT_OBJECT;
import service.MyBaseService;
import service.ServiceEnum;

import java.time.LocalDateTime;
import java.util.Map;

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

        LocalDateTime time = LocalDateTime.now();

        // List for charts
        client.getIndexBidSeries().add(time);
        client.getIndexAskSeries().add(time);
        client.getIndexBidAskCounterSeries().add(time);
        client.getIndexSeries().add(time);
        client.getIndexScaledSeries().add( time );
        client.getIndBidAskMarginSeries().add(time);

        client.getDayOpList().add( client.getFutDay() - client.getIndex() );
        client.getWeekOpList().add( client.getFutWeek() - client.getIndex() );
        client.getMonthOpList().add( client.getFutMonth() - client.getIndex() );
        client.getE2OpList().add( client.getFutQuarterFar() - client.getIndex() );

        // Options lists
        for (Exp exp : client.getExps().getExpList()) {
            try {
                exp.getFutList().add(exp.getFuture());
                exp.getOpFutList().add(exp.getOpFuture());

                try {
                    exp.getOpAvgFutSeries().add(time);
                    exp.getOpAvg15FutSeries().add(time);
                } catch (Exception e) {
                    System.out.println(getClient().getName() + " OpAvgFutureList is empty");
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            exp.getFutBidAskCounterSeries().add(time);
        }

        // Roll lists
        try {
            for (Map.Entry<RollEnum, Roll> entry : getClient().getRollHandler().getRollMap().entrySet()) {
                Roll roll = entry.getValue();
                roll.addRoll();
            }
        } catch (NullPointerException e) {
        }
    }
}