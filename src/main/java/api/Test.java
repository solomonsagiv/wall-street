package api;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Random;

public class Test {

    public static void main(String[] args) throws IOException, InterruptedException {

        double a = 51.678;
        int b = 3;
        System.out.println( a / b);

    }

    public void Loto() {
        for ( int i = 0; i < 5; i++ ) {
            System.out.println( new Random(  ).nextInt( 49 ) + 1 );
        }

        System.out.println( );

        for ( int i = 0; i < 2; i++ ) {
            System.out.println( new Random(  ).nextInt( 11 ) + 1 );
        }
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