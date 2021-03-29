package file;

import java.util.logging.Logger;

public class MyFile {

    private Logger logger;
    private String fileName;
    private String location;

    // Constructor
    private MyFile(String fileName, String location) {
        this.fileName = fileName;
        this.location = location;
    }

    public void write(String text) {
    }

    public void write(Object o) {
        write(String.valueOf(o));
    }

    public void append(String text) {

    }
}