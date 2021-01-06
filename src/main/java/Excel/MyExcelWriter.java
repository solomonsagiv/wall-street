package Excel;

import dataTable.DataTable;
import dataTable.RowData;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import locals.L;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Random;

public class MyExcelWriter {

    public final String FILE_LOCATION = "C:/Users/user/Dropbox/My PC (DESKTOP-3TD8U17)/Desktop/spx/Data/Spx_" + LocalDate.now( ) + ".xls";
    public final String FILE_LOCATION_2 = "C:/Users/user/Desktop/Spx_" + LocalDate.now( ) + ".csv";
    WritableWorkbook workbook;
    WritableSheet sheet;

    int sheetCount = 0;

    public MyExcelWriter() {
        try {
            workbook = Workbook.createWorkbook( new File( FILE_LOCATION ) );
            sheet = createNewSheet( );
        } catch ( IOException e ) {
            e.printStackTrace( );
        }
    }

    public WritableSheet createNewSheet() {
        sheet = workbook.createSheet( "Data " + sheetCount, sheetCount );
        sheetCount++;
        return sheet;
    }

    public void export( DataTable dataTable ) {
        try {

            // Headers
            sheet.addCell( new Label( 0, 0, "Time" ) );
            sheet.addCell( new Label( 1, 0, "Index" ) );
            sheet.addCell( new Label( 2, 0, "Index bid" ) );
            sheet.addCell( new Label( 3, 0, "Index ask" ) );
            sheet.addCell( new Label( 4, 0, "Day" ) );
            sheet.addCell( new Label( 5, 0, "Week" ) );
            sheet.addCell( new Label( 6, 0, "Month" ) );
            sheet.addCell( new Label( 7, 0, "E1" ) );
            sheet.addCell( new Label( 8, 0, "E2" ) );

            int rowCount = 1;

            // Data
            for ( RowData row : dataTable.getRows( ) ) {
                try {

                    Label timeLbl = new Label( 0, rowCount, row.getTime( ).toString( ) );
                    Label indLbl = new Label( 1, rowCount, L.str( row.getInd( ) ) );
                    Label indBidLbl = new Label( 2, rowCount, L.str( row.getIndBid( ) ) );
                    Label indAskLbl = new Label( 3, rowCount, L.str( row.getIndAsk( ) ) );
                    Label futDayLbl = new Label( 4, rowCount, L.str( row.getFutDay( ) ) );
                    Label futWeekLbl = new Label( 5, rowCount, L.str( row.getFutWeek( ) ) );
                    Label futMonthLbl = new Label( 6, rowCount, L.str( row.getFutMonth( ) ) );
                    Label futE1Lbl = new Label( 7, rowCount, L.str( row.getFutE1( ) ) );
                    Label futE2Lbl = new Label( 8, rowCount, L.str( row.getFutE2( ) ) );

                    sheet.addCell( timeLbl );
                    sheet.addCell( indLbl );
                    sheet.addCell( indBidLbl );
                    sheet.addCell( indAskLbl );
                    sheet.addCell( futDayLbl );
                    sheet.addCell( futWeekLbl );
                    sheet.addCell( futMonthLbl );
                    sheet.addCell( futE1Lbl );
                    sheet.addCell( futE2Lbl );

                    // Max Rows filter
                    if ( rowCount > 60000 ) {
                        sheet = createNewSheet( );
                        rowCount = 1;
                    }
                } catch ( WriteException e ) {
                    if ( e instanceof RowsExceededException ) {
                        sheet = createNewSheet( );
                        rowCount = 1;
                    }
                    e.printStackTrace( );
                }
                rowCount++;
            }

            // Write and close
            workbook.write( );
            workbook.close( );

        } catch ( IOException | WriteException e ) {
            e.printStackTrace( );
        }
    }

    public static void main( String[] args ) {
        try {
            DataTable dataTable = new DataTable( );

            Random random = new Random( );

            for ( int i = 0; i < 150000; i++ ) {
                dataTable.addRow( new RowData( random.nextDouble( ) * 100, random.nextDouble( ) * 100, random.nextDouble( ) * 100, random.nextDouble( ) * 100, random.nextDouble( ) * 100, random.nextDouble( ) * 100, random.nextDouble( ) * 100, random.nextDouble( ) * 100 ) );
            }

            new MyExcelWriter( ).export( dataTable );

        } catch ( Exception e ) {
            e.printStackTrace( );
        }
    }

}
