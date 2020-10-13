package api;

import lists.MyDoubleList;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Random;

public class Test {

    public static void main( String[] args ) throws IOException, InterruptedException {

        MyDoubleList doubles = new MyDoubleList( );

        for ( int i = 0; i < 20; i++ ) {
            doubles.add( ( double ) i );
            System.out.println( doubles.getLastValAsStd() );
        }

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