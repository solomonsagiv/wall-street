package fillCounter;

import grades.GRADES;
import grades.GradesHandler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class MainFillCounter {

    public static String DATE = "2021-05-21";
    public static String TIME = "23:00:00";

    // Main
    public static void main(String[] args) {
        MainFillCounter mainFillCounter = new MainFillCounter();
        mainFillCounter.run_multy_days();
    }

    public void run_multy_days() {

        LocalDate date = LocalDate.of(2021, 1, 19);
        LocalDate end_date = LocalDate.of(2021, 5, 29);

        while (date.isBefore(end_date)) {

            // NOT SATURDAY OR SUNDAY
            if (date.getDayOfWeek().getValue() != 6 && date.getDayOfWeek().getValue() != 7) {
                System.out.println();
                System.out.println("---------- " + date + " -----------");
                run_sungle_day(date.toString(), MainFillCounter.TIME);
            }

            date = date.plusDays(1);
        }

    }

    public void run_sungle_day(String date, String time) {
        GradesHandler gradesHandler = new GradesHandler();

        // ATTRIBUTES
        GRADES move_grade_giver = gradesHandler.getMove_cumu_1();
        GRADES op_grade_giver = gradesHandler.getOp_cumu_1();

        // IMPORT DATA
        ImportData importData = new ImportData(date, time);
        // PRINT ARRAYS
        importData.print();


        // CREATE FILL COUNTER INSTANCE
        FillCounterService fillCounterService = new FillCounterService(move_grade_giver, op_grade_giver);

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
                System.out.println(fillCounterService.getOp_grade_cumu());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // PRINT RACES
        System.out.println();
        System.out.println("---------- RACES ----------");
//        fillCounterService.print_races();

        // INSERT TO DATABASE
//        importData.insertListRetro(fillCounterService.getRaces(), "data.spx500_sagiv_function_cdf");
//        importData.insertListRetro(fillCounterService.getRaces(), "data.spx500_sagiv_op_1_function_cdf");
    }


}
