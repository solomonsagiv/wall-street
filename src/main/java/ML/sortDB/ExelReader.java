package ML.sortDB;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class ExelReader {

    private static final String EXCEL_FILE_LOCATION = "C:/Users/user/Desktop/Work/Data history/TA35/ta35_2018-12-04.xls";

    public ExelReader() {
    }

    public ArrayList<TA35Data> readExel(int id) {
        {

            ExelReader exelReader = new ExelReader();
            System.out.println(exelReader.getFileCreatedDate(EXCEL_FILE_LOCATION));

            Workbook workbook = null;

            ArrayList<TA35Data> lines = new ArrayList<>();

            try {
                // The workbook
                workbook = Workbook.getWorkbook(new File(EXCEL_FILE_LOCATION));

                // The sheet
                Sheet sheet = workbook.getSheet(0);

                LocalDate date = getFileCreatedDate(EXCEL_FILE_LOCATION);

                // For each row in the excel
                for (int row = 0; row < sheet.getRows(); row++) {

                    Cell[] cells = sheet.getRow(row);

                    LocalTime time = LocalTime.parse(cells[0].getContents());
                    double contract = dbl(cells[1].getContents());
                    double ind = dbl(cells[2].getContents());
                    int indUp = toInt(cells[3].getContents());
                    int indDown = toInt(cells[4].getContents());
                    int conUp = toInt(cells[5].getContents());
                    int conDown = toInt(cells[6].getContents());

                    // Filters
                    // Before start
                    if (time.isBefore(LocalTime.of(9, 45, 0))) {
                        continue;
                    }

                    // After end
                    if (time.isAfter(LocalTime.of(17, 45, 0))) {
                        continue;
                    }

                    // Create line object from the data
                    TA35Data line = new TA35Data();

                    line.setId(id);
                    line.setTime(time.toString());
                    line.setDate(date.toString());
                    line.setFuture(contract);
                    line.setIndex(ind);
                    line.setFuture_up(conUp);
                    line.setFuture_down(conDown);
                    line.setIndex_up(indUp);
                    line.setIndex_down(indDown);

                    // Append line
                    lines.add(line);

                    System.out.println(id);
                    id++;
                }

                return lines;


            } catch (IOException | BiffException e) {
                e.printStackTrace();
            } finally {

                if (workbook != null) {
                    workbook.close();
                }

            }

        }
        return null;
    }

    public Double dbl(String s) {
        return Double.parseDouble(s);
    }

    public int toInt(String s) {
        return Integer.parseInt(s);
    }

    public LocalDate getFileCreatedDate(String fileLocation) {
        try {

            DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

            FileTime creationTime = (FileTime) Files.getAttribute(Paths.get(fileLocation), "creationTime");
            String dateString = df.format(creationTime.toMillis());

            String replacedString = dateString.replace('/', '-');

            return LocalDate.parse(replacedString);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }


}