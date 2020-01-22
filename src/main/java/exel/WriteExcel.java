package exel;

import serverObjects.BASE_CLIENT_OBJECT;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;

public class WriteExcel {

    // Create today name xls file
    @SuppressWarnings( "deprecation" )
    public static FileOutputStream get_today_file_xls( BASE_CLIENT_OBJECT client ) throws FileNotFoundException {

        FileOutputStream out = new FileOutputStream(
                new File( client.getExportLocation( ) + client.getName( ) + "-" + LocalDate.now( ).toString( ) + ".xls" ) );
        return out;
    }
}