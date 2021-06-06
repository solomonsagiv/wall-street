package Tests;

import dataBase.mySql.MySql;
import java.time.LocalDate;

public class JibeTest {

    public static void main(String[] args) {

        LocalDate date = LocalDate.of(2021, 1, 20);
        LocalDate end_date = LocalDate.now();

        while (date.isBefore(end_date)) {
            // NOT SATURDAY OR SUNDAY
            if (date.getDayOfWeek().getValue() != 6 && date.getDayOfWeek().getValue() != 7) {
                System.out.println();
                System.out.println("---------- " + date + " -----------");

                String base_query = "insert into data.spx500_op_avg_day\n" +
                        "select dd.time, round(avg(dd.value) over (ORDER BY dd.time ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW) * 100) / 100\n" +
                        "from (\n" +
                        "         select i.time, f.value - i.value as value\n" +
                        "         from data.spx500_index i\n" +
                        "                  inner join data.spx500_fut_day f on i.time = f.time\n" +
                        "     ) dd\n" +
                        "where dd.time::date = date'%s';";
                String query = String.format(base_query, date);


                MySql.insert(query);
            }
            date = date.plusDays(1);
        }

    }
}
