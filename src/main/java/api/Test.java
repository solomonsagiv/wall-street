package api;

import java.sql.SQLException;
import java.util.ArrayList;

@EnableAsync
public class Test {


    public static void main( String[] args ) throws SQLException {

//        String url = "jdbc:mysql://parisdb.chuxlqcvlex2.eu-west-3.rds.amazonaws.com:3306/";
//        String userName = "sagivMasterUser";
//        String password = "Solomonsagivawsmaster12";
//        String dbName = "stocks";
//        String driver = "com.mysql.jdbc.Driver";
//        Connection connection = ( Connection ) DriverManager.getConnection( url + dbName, userName, password );
//
//        Statement stmt = ( Statement ) connection.createStatement( );
//
//        String query = "INSERT INTO `stocks`.`spx_daily` (`id`, `date`, `exp_name`, `open`, `high`, `low`, `close`, `con_up`, `con_down`, `index_up`, `index_down`, `op_avg`, `fut_bid_ask_counter`, `base`, `options`, `con_bid_ask_counter`) " +
//                "VALUES ('349', 'ddd', 'ee', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', 'kkj', '0');";
//
//        PreparedStatement s = ( PreparedStatement ) connection.prepareStatement( query );
//
//        long startTime = System.currentTimeMillis( );
//        s.execute( );
//
//        long endTime = System.currentTimeMillis( );
//
//        double duration = ( endTime - startTime );  //divide by 1000000 to get milliseconds
//        System.out.println( duration / 1000 );


//        ExecutorService executor = Executors.newCachedThreadPool();
//
//        executor.submit(new Runnable() {
//            @Override
//            public void run() {
//
//                for (int i = 0; i < 30; i++) {
//                    try {
//                        Thread.sleep(10);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    System.out.println("3");
//                }
//
//            }
//        });
//
//        executor.submit(() -> {
//            String threadName = Thread.currentThread().getName();
//            System.out.println("Hello " + threadName);
//        });
//
//        System.out.println("None");
//        executor.shutdown();
        A a = new A("a");
        A a1 = new A( "a1" );

        ArrayList<A> list = new ArrayList<>();
        list.add(a1);

        System.out.println( list );

        list.remove( a );

        System.out.println( list );

    }

    static class A {
        String name;

        public A( String name ) {
            this.name = name;
        }
    }

}
