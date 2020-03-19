package rollService;

import options.Options;
import serverObjects.BASE_CLIENT_OBJECT;
import service.MyBaseService;
import service.ServiceEnum;

public class RollService extends MyBaseService {

    RollRunner runner_1;
    RollRunner runner_2;
    double avg = 0;

    public RollService( BASE_CLIENT_OBJECT client, Options o1, Options o2 ) {
        super( client );
        runner_1 = new RollRunner( o1 );
        runner_2 = new RollRunner( o2 );
    }

    @Override
    public void go() {

        // ----- Logic ----- //
        logic();

        // Update data
        updateData();
    }

    private void updateData() {
        // Avg
        avg = (runner_1.getContract() + runner_2.getContract()) / 2;

        // Runners
        runner_1.updateData( avg );
        runner_2.updateData( avg );
    }

    private void logic() {

        // ----- Runner 1 ----- //
        // Got bigger
        if ( runner_1.isMarginFromAvgGotBigger( avg ) && !runner_2.isContractChanged() ) {
            runner_1.plusCounter();
        }

        // Got smaller
        if ( runner_1.isMarginFromAvgGotSmaller( avg ) && !runner_2.isContractChanged() ) {
            runner_1.minusCounter();
        }

        // ----- Runner 2 ----- //
        // Got bigger
        if ( runner_2.isMarginFromAvgGotBigger( avg ) && !runner_1.isContractChanged() ) {
            runner_2.plusCounter();
        }

        // Got smaller
        if ( runner_2.isMarginFromAvgGotSmaller( avg ) && !runner_1.isContractChanged() ) {
            runner_2.minusCounter();
        }

    }

    @Override
    public String getName() {
        return "RollService";
    }

    @Override
    public int getSleep() {
        return 200;
    }

    @Override
    public ServiceEnum getType() {
        return ServiceEnum.ROLL_COUNTER;
    }
}

// Roll runner class
class RollRunner {

    // Variables
    private Options options;
    private double contract = 0;
    private double marginFromAvg = 0;
    private int counter = 0;

    // Constructor
    public RollRunner( Options options ) {
        this.options = options;
    }

    public void updateData( double marginFromAvg ) {
        this.contract = options.getContract();
        this.marginFromAvg = Math.abs( marginFromAvg );
    }

    public double getContract() {
        return contract;
    }

    public double getMarginFromAvg() {
        return marginFromAvg;
    }

    public void plusCounter() {
        counter++;
    }

    public void minusCounter() {
        counter--;
    }

    public double getCurrentContract() {
        return options.getContract();
    }

    public boolean isContractChanged() {
        return getCurrentContract() != contract;
    }

    public double getCurrentMarginFromAvg( double avg ) {
        return Math.abs( getCurrentContract() - avg );
    }

    public boolean isMarginFromAvgGotBigger( double avg ) {
        return getCurrentMarginFromAvg( avg ) > getMarginFromAvg();
    }

    public boolean isMarginFromAvgGotSmaller( double avg ) {
        return getCurrentMarginFromAvg( avg ) < getMarginFromAvg();
    }

}