package api;

import serverObjects.stockObjects.Apple;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;

public class Test {

    public static void main(String[] args) throws IOException {

        Apple apple = Apple.getInstance();
        System.out.println(apple.getAsJson().toString(4) );

    }

    public static void writeObject(File file, Object object) {
        try {
            FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(object);
            objectOut.close();
            System.out.println("The Object  was succesfully written to a file");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}