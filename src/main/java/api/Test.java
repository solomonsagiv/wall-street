package api;

import charts.myChart.MyTimeSeries;
import org.jfree.data.time.Second;
import serverObjects.indexObjects.Spx;

import java.io.*;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Test {

    public static void main( String[] args ) throws IOException {
//        JSONArray array = new JSONArray( );
//        JSONArray array2 = new JSONArray( );
//
//        Map< String, JSONArray > map = new HashMap<>( );
//
//        map.put( "Array1", array );
//        map.put( "Array2", array2 );
//
//        for ( int i = 0; i < 20; i++ ) {
//            JSONArray arr = new JSONArray( );
//            arr.put( "x" );
//            arr.put( i );
//            array.put( arr );
//            array2.put( arr );
//        }
//
//      JSONArray arrayLists = map.get( "Array1" );

        File file = new File( "files/arrays/SpxArrays.txt" );
        FileWriter fr = new FileWriter( file, true );

        ArrayList< Integer > list = new ArrayList<>( );


//        System.out.println( LocalTime.now( ) );

        MyTimeSeries myTimeSeries = new MyTimeSeries( "Test", Spx.getInstance( ) ) {
            @Override
            public double getData() throws UnknownHostException {
                return 0;
            }
        };

        Second second = new Second( );
        for ( int i = 0; i < 70000; i++ ) {
            myTimeSeries.add( second, myTimeSeries.getData( ) );
            second = ( Second ) second.next( );
        }

        Map< Integer, MyTimeSeries > map = new HashMap<>( );
        map.put( 1, myTimeSeries );
        map.put( 2, myTimeSeries );
        map.put( 3, myTimeSeries );
        map.put( 4, myTimeSeries );
        map.put( 5, myTimeSeries );

        System.out.println( LocalTime.now( ) );
        Test.writeObject( file, map );
        System.out.println( LocalTime.now( ) );

//        File file = new File("files/arrays/SpxArrays.txt");
//        FileWriter fr = new FileWriter(file, true);
//        BufferedWriter br = new BufferedWriter(fr);
//        br.write("data");
//
//        br.close();
//        fr.close();

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