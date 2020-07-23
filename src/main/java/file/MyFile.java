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
    }

    public void write( String text ) {

    }

    public void write(Object o) {
        write( String.valueOf( o ) );
    }

    public void append( String text ) {

    }
}

