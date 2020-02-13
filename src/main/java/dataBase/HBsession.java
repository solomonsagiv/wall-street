//package dataBase;
//
//import org.hibernate.SessionFactory;
//import org.hibernate.cfg.Configuration;
//import tables.ExpData;
//import tables.SettingTable;
//import tables.bounds.BoundsTable;
//import tables.daily.*;
//import tables.status.IndexArraysTable;
//import tables.status.IndexStatusTable;
//import tables.status.StocksArraysTable;
//import tables.status.StocksStatusTable;
//import tables.stocks.sum.*;
//import tables.summery.Dax_daily;
//import tables.summery.Ndx_daily;
//import tables.summery.SpxDaily;
//
//public class HBsession {
//
//    private static SessionFactory boundsFactory = null;
//    private static SessionFactory parisFactory = null;
//    private SessionFactory factory = null;
//    private SessionFactory stocksFactory = null;
//
//    public HBsession() {
//    }
//
//    // Create the bounds session factory
//    public static SessionFactory getBoundsFactory() {
//        if ( boundsFactory == null ) {
//            boundsFactory = new Configuration( ).configure( "bounds.cfg.xml" ).addAnnotatedClass( BoundsTable.class ).buildSessionFactory( );
//        }
//        return boundsFactory;
//    }
//
//    // Create the session
//    public SessionFactory getParisFactory() {
//        if ( parisFactory == null ) {
//            parisFactory = new Configuration( ).configure( "hibernateRdsParis.cfg.xml" )
//                    .addAnnotatedClass( NdxTable.class )
//                    .addAnnotatedClass( Ndx_daily.class ).addAnnotatedClass( SpxDaily.class )
//                    .addAnnotatedClass( IndexArraysTable.class ).addAnnotatedClass( SpxTable.class )
//                    .addAnnotatedClass( IndexStatusTable.class )
//                    .buildSessionFactory( );
//        }
//        return parisFactory;
//    }
//
//
//    // Create the session
//    public SessionFactory getFactory() {
//        if ( factory == null ) {
//            factory = new Configuration( ).configure( "hibernateRds.cfg.xml" ).addAnnotatedClass( DaxTable.class )
//                    .addAnnotatedClass( NdxTable.class ).addAnnotatedClass( Dax_daily.class )
//                    .addAnnotatedClass( Ndx_daily.class ).addAnnotatedClass( SpxDaily.class )
//                    .addAnnotatedClass( IndexArraysTable.class ).addAnnotatedClass( SpxTable.class )
//                    .addAnnotatedClass( ExpData.class ).addAnnotatedClass( IndexStatusTable.class )
//                    .addAnnotatedClass( SettingTable.class ).buildSessionFactory( );
//        }
//        return factory;
//    }
//
//    // Create the session
//    public SessionFactory getStockFactory() {
//        if ( stocksFactory == null ) {
//            stocksFactory = new Configuration( ).configure( "hibernateStocksRds.cfg.xml" )
//                    .addAnnotatedClass( AmazonSum.class ).addAnnotatedClass( FacebookSum.class )
//                    .addAnnotatedClass( AppleSum.class ).addAnnotatedClass( AmazonTable.class )
//                    .addAnnotatedClass( AppleTable.class ).addAnnotatedClass( StocksArraysTable.class )
//                    .addAnnotatedClass( FacebookTable.class ).addAnnotatedClass( NetflixTable.class )
//                    .addAnnotatedClass( StocksStatusTable.class ).addAnnotatedClass( NetflixSum.class )
//                    .addAnnotatedClass( StocksExp.class ).buildSessionFactory( );
//        }
//        return stocksFactory;
//    }
//
//    // Close connection
//    public void close_connection() {
//        if ( factory != null ) {
//            factory.close( );
//        }
//
//        if ( stocksFactory != null ) {
//            stocksFactory.close( );
//        }
//    }
//}
