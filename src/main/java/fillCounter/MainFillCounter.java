package fillCounter;

import grades.GRADES;
import grades.GradesHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class MainFillCounter {

    // Main
    public static void main(String[] args) {
        MainFillCounter mainFillCounter = new MainFillCounter();
        mainFillCounter.run();
    }

    public void run() {

        // ATTRIBUTES
        GRADES grades = new GradesHandler().getMove_cumu_1();

        // IMPORT DATA
        ImportData importData = new ImportData();
        // PRINT ARRAYS
        importData.print();


        // CREATE FILL COUNTER INSTANCE
        FillCounterService fillCounterService = new FillCounterService(grades);

        // GET ARRAYS DATA
        ArrayList<ArrayList<MySerie>> arrays = importData.getArrays();

        // RUN FILL COUNTER IN LOOP
        for (int i = 0; i < arrays.get(0).size(); i++) {
            try {
                LocalDateTime dateTime = arrays.get(0).get(i).dateTime;
                double index = arrays.get(ImportData.INDEX_I).get(i).value;
                double index_bid = arrays.get(ImportData.INDEX_BID_I).get(i).value;
                double index_ask = arrays.get(ImportData.INDEX_ASK_I).get(i).value;
                double future = arrays.get(ImportData.FUT_I).get(i).value;

                fillCounterService.run(dateTime, index, index_bid, index_ask, future);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // PRINT RACES
        System.out.println();
        System.out.println("---------- RACES ----------");
        fillCounterService.print_races();

        // INSERT TO DATABASE
        importData.insertListRetro(fillCounterService.getRaces(), "data.spx500_sagiv_function_cdf");

    }

}
