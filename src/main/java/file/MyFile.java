package file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class MyFile {

    private Logger logger;
    private String fileName;
    private String location;

    // Constructor
    private MyFile( String fileName, String location ) {
        this.fileName = fileName;
        this.location = location;
        logger = createLogger( );
    }

    // Create the logger file
    public Logger createLogger() {

        System.out.println(Paths.get( location ) );

        Logger logger = null;
        FileHandler fh;

        if ( Files.exists( Paths.get( location ) ) ) {
            try {
                logger = Logger.getLogger( location + fileName );

                // This block configure the logger with handler and formatter
                fh = new FileHandler( location + fileName );

                logger.addHandler( fh );
                BriefFormatter briefFormatter = new BriefFormatter( );
                fh.setFormatter( briefFormatter );

                return logger;
            } catch ( SecurityException | IOException e ) {
                e.printStackTrace( );
            }
        }
        return logger;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger( Logger logger ) {
        this.logger = logger;
    }

    public StringBuilder getAllText() throws IOException {

        StringBuilder text = new StringBuilder( );

        File file = new File( location + fileName );

        BufferedReader br = new BufferedReader( new FileReader( file ) );

        String st;
        while ( ( st = br.readLine( ) ) != null ) {
            text.append( st + "\n" );
        }

        return text;

    }

}

class BriefFormatter extends Formatter {
    String lineSeparator = System.getProperty( "line.separator" );

    public BriefFormatter() {
        super( );
    }

    @Override
    public String format( final LogRecord record ) {
        return LocalTime.now( ) + " - " + record.getMessage( ) + lineSeparator;
    }
}

