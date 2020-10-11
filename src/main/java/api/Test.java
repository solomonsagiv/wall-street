package api;

import lists.MyDoubleList;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Test {

    public static void main( String[] args ) throws IOException, InterruptedException {
        double[] d = { 3, 4, 5, 2, 4, 4, 4, 3, 6};

        MyDoubleList doubles = new MyDoubleList();
        for ( double ds: d) {
            doubles.add( ds );
        }

        System.out.println( doubles.toStdList() );
    }

    public static void loto() {
        for ( int i = 0; i < 5; i++ ) {
            System.out.println( new Random( ).nextInt( 49 ) + 1 );
        }

        System.out.println( );

        for ( int i = 0; i < 2; i++ ) {
            System.out.println( new Random( ).nextInt( 11 ) + 1 );
        }
    }

    public static void writeObject( File file, Object object ) {
        try {
            FileOutputStream fileOut = new FileOutputStream( file );
            ObjectOutputStream objectOut = new ObjectOutputStream( fileOut );
            objectOut.writeObject( object );
            objectOut.close( );
            System.out.println( "The Object  was succesfully written to a file" );
        } catch ( Exception ex ) {
            ex.printStackTrace( );
        }
    }

}