package backTestChart;

import serverObjects.indexObjects.Spx;

import java.util.ArrayList;
import java.util.Map;

public class Main {

    public static void main( String[] args ) throws CloneNotSupportedException {

        int year = 2020;
        int month = 10;
        int day = 26;

        String query = String.format("select * from jsonTables.spxJsonDay where date = '%s-%s-%s';", year, month, day);

        GetDataFromDB dataFromDB = new GetDataFromDB( query );
        Map<String, ArrayList<Double> > map = dataFromDB.getDataFromDb();

        TheChart chart = new TheChart( Spx.getInstance(), year, month, day );
        chart.appendData( map, dataFromDB.getTimeList() );
        chart.createChart();

    }

}
