package fillCounter;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class MainFillCounter {

    // Main
    public static void main(String[] args) {
        MainFillCounter mainFillCounter = new MainFillCounter();
        mainFillCounter.run();
    }

    public void run() {
        ImportData importData = new ImportData();
        FillCounterService fillCounterService = new FillCounterService();

        // Get the arrays of data
        ArrayList<ArrayList<MySerie>> arrays = importData.getArrays();

        // Run the fillCounter in loop
        for (int i = 0; i < arrays.get(0).size(); i++) {


            LocalDateTime dateTime = arrays.get(0).get(0).dateTime;
            double index = arrays.get(ImportData.INDEX_I).get(i).value;
            double index_bid = arrays.get(ImportData.INDEX_BID_I).get(i).value;
            double index_ask = arrays.get(ImportData.INDEX_ASK_I).get(i).value;
            double future = arrays.get(ImportData.FUT_I).get(i).value;

            fillCounterService.run(dateTime, index, index_bid, index_ask, future);

            System.out.println(fillCounterService.getMove_cumu());
        }
    }

}
