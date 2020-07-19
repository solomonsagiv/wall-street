package logger;

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

public class MyLogger {

    static String locationMac = "/Users/sagivsolomon/Desktop/Development/Loggs/";
    static String locationWindows = "C:/Users/user/Desktop/Work/Development/Loggers/";

    static String location;

    static String name = "WallStreet.txt";
    static MyLogger myLogger;
    private Logger logger;

    // Constructor
    public MyLogger() {
        logger = createLogger();
    }

    // Get instance
    public static synchronized MyLogger getInstance() {
        if (myLogger == null) {
            myLogger = new MyLogger();
        }
        return myLogger;
    }

    // Create the logger file
    public static Logger createLogger() {

        Logger logger = Logger.getLogger(name);
        FileHandler fh;

        if (Files.exists(Paths.get(locationMac))) {
            location = locationMac;
        } else if (Files.exists(Paths.get(locationWindows))) {
            location = locationWindows;
        }

        try {

            // This block configure the logger with handler and formatter
            fh = new FileHandler(location + name);

            logger.addHandler(fh);
            BriefFormatter briefFormatter = new BriefFormatter();
            fh.setFormatter(briefFormatter);

            return logger;
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
        return logger;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public StringBuilder getAllText() throws IOException {

        StringBuilder text = new StringBuilder();

        File file = new File(location + name);

        BufferedReader br = new BufferedReader(new FileReader(file));

        String st;
        while ((st = br.readLine()) != null) {
            text.append(st + "\n");
        }

        return text;

    }


}

class BriefFormatter extends Formatter {
    String lineSeparator = System.getProperty("line.separator");

    public BriefFormatter() {
        super();
    }

    @Override
    public String format(final LogRecord record) {
        return LocalTime.now() + " - " + record.getMessage() + lineSeparator;
    }
}
