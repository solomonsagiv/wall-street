package api.tws;

import api.Manifest;
import com.ib.client.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class TWSConnection extends Thread implements EWrapper {

    public static TWSConnection twsConnection;

    EJavaSignal m_signal;
    EClientSocket client;
    int NextOrderId;
    int contract_id = 100;
    int clientID = Manifest.CLIENT_ID;
    int port = Manifest.TEST_PORT;
    String accountCode = Manifest.TEST_ACCOUNT;

    boolean connected = false;
    ArrayList< Thread > threads = new ArrayList<>( );
    private MyTwsClient twsClient;

    // Constructor
    TWSConnection() {
        m_signal = new EJavaSignal( );
        client = new EClientSocket( this, m_signal );

        // Connet to tws
        client.eConnect( "localhost", port, clientID );
    }

    public static TWSConnection getInstance() {
        if ( twsConnection == null ) {
            twsConnection = new TWSConnection( );
        }
        return twsConnection;
    }

    public static void main( String[] args ) throws InterruptedException {

        TWSConnection connection = new TWSConnection( );
        connection.start( );

        Thread.sleep( 2000 );

        System.out.println( connection.getNextOrderId( ) );
        System.out.println( connection.getNextOrderId( ) );
    }

    @Override
    public void run() {

        final EReader reader = new EReader( client, m_signal );
        reader.start( );

        new Thread( ) {
            public void run() {
                while ( client.isConnected( ) ) {
                    m_signal.waitForSignal( );
                    try {
                        javax.swing.SwingUtilities.invokeAndWait( new Runnable( ) {
                            @Override
                            public void run() {
                                try {
                                    reader.processMsgs( );
                                } catch ( IOException e ) {
                                    error( e );
                                }
                            }
                        } );
                    } catch ( Exception e ) {
                        error( e );
                    }
                }
            }
        }.start( );
        if ( NextOrderId < -10 ) {
            try {
                sleep( 1000 );
            } catch ( InterruptedException e ) {
                e.printStackTrace( );
            }
        }

        client.reqAutoOpenOrders( true );
        client.reqPositions( );
//		client.reqAccountUpdates(true, accountCode);
        client.reqIds( 1 );
        try {
            System.in.read( );
        } catch ( IOException e ) {
            e.printStackTrace( );
        }
    }

    public void close() {
        try {
            for ( Thread thread : threads ) {
                thread.interrupt( );
            }
            client.reqGlobalCancel( );
            client.eDisconnect( );
        } finally {
            twsConnection = null;
        }
    }

    public boolean isConnected() {
        return connected;
    }

    public void myDisConnect() {
        client.eDisconnect( );
        client.reqGlobalCancel( );
    }

    // Parse double
    public double dbl( String s ) {
        return Double.parseDouble( s );
    }

    @Override
    public void nextValidId( int orderId ) {
        NextOrderId = orderId;
        System.out.println( EWrapperMsgGenerator.nextValidId( NextOrderId ) );
    }

    @Override
    public void error( Exception e ) {
        System.out.println( EWrapperMsgGenerator.error( e ) );
    }

    @Override
    public void error( int id, int errorCode, String errorMsg ) {
        System.out.println( EWrapperMsgGenerator.error( id, errorCode, errorMsg ) );
    }

    @Override
    public void connectionClosed() {
        System.out.println( EWrapperMsgGenerator.connectionClosed( ) );
        System.out.println( "Connection closed" );
        connected = false;
        close( );
    }

    @Override
    public void error( String str ) {
        System.out.println( EWrapperMsgGenerator.error( str ) );
    }

    @Override
    public void tickPrice( int tickerId, int field, double price, TickAttr attribs ) {
    }

    @Override
    public void tickSize( int tickerId, int field, int size ) {
    }

    @Override
    public void tickOptionComputation( int tickerId, int field, double impliedVol, double delta, double optPrice,
                                       double pvDividend, double gamma, double vega, double theta, double undPrice ) {
    }

    @Override
    public void tickGeneric( int tickerId, int tickType, double value ) {
    }

    @Override
    public void tickString( int tickerId, int tickType, String value ) {
    }

    @Override
    public void tickEFP( int tickerId, int tickType, double basisPoints, String formattedBasisPoints,
                         double impliedFuture, int holdDays, String futureLastTradeDate, double dividendImpact,
                         double dividendsToLastTradeDate ) {
    }

    @Override
    public void orderStatus( int orderId, String status, double filled, double remaining, double avgFillPrice,
                             int permId, int parentId, double lastFillPrice, int clientId, String whyHeld ) {
        System.out.println( );
        System.out.println( "Order status" );
        System.out.println( orderId + "\n" + status + "\n" + filled );
        System.out.println( );
    }

    @Override
    public void openOrder( int orderId, Contract contract, Order order, OrderState orderState ) {
        System.out.println( );
        System.out.println( "Open order" );
        System.out.println( "Order : " + orderId + " Contract: " + contract + "Order: " + order );
        System.out.println( );
    }

    @Override
    public void openOrderEnd() {
    }

    @Override
    public void updateAccountValue( String key, String value, String currency, String accountName ) {
        System.out.println( EWrapperMsgGenerator.updateAccountValue( key, value, currency, accountName ) );
        connected = true;
    }

    @Override
    public void updatePortfolio( Contract contract, double position, double marketPrice, double marketValue,
                                 double averageCost, double unrealizedPNL, double realizedPNL, String accountName ) {
    }

    @Override
    public void updateAccountTime( String timeStamp ) {
    }

    @Override
    public void accountDownloadEnd( String accountName ) {
    }

    @Override
    public void contractDetails( int reqId, ContractDetails contractDetails ) {
    }

    @Override
    public void bondContractDetails( int reqId, ContractDetails contractDetails ) {
    }

    @Override
    public void contractDetailsEnd( int reqId ) {
    }

    @Override
    public void execDetails( int reqId, Contract contract, Execution execution ) {
    }

    @Override
    public void execDetailsEnd( int reqId ) {
    }

    @Override
    public void updateMktDepth( int tickerId, int position, int operation, int side, double price, int size ) {
    }

    @Override
    public void updateMktDepthL2( int tickerId, int position, String marketMaker, int operation, int side, double price,
                                  int size ) {
    }

    @Override
    public void updateNewsBulletin( int msgId, int msgType, String message, String origExchange ) {
    }

    @Override
    public void managedAccounts( String accountsList ) {
    }

    @Override
    public void receiveFA( int faDataType, String xml ) {
    }

    @Override
    public void historicalData( int reqId, String date, double open, double high, double low, double close, int volume,
                                int count, double WAP, boolean hasGaps ) {
    }

    @Override
    public void scannerParameters( String xml ) {
    }

    @Override
    public void scannerData( int reqId, int rank, ContractDetails contractDetails, String distance, String benchmark,
                             String projection, String legsStr ) {
    }

    @Override
    public void scannerDataEnd( int reqId ) {
    }

    @Override
    public void realtimeBar( int reqId, long time, double open, double high, double low, double close, long volume,
                             double wap, int count ) {
    }

    @Override
    public void currentTime( long time ) {
    }

    @Override
    public void fundamentalData( int reqId, String data ) {
    }

    @Override
    public void deltaNeutralValidation( int reqId, DeltaNeutralContract underComp ) {
    }

    @Override
    public void tickSnapshotEnd( int reqId ) {
    }

    @Override
    public void marketDataType( int reqId, int marketDataType ) {
        System.out.println( EWrapperMsgGenerator.marketDataType( reqId, marketDataType ) );
    }

    @Override
    public void commissionReport( CommissionReport commissionReport ) {
        System.out.println( "Comission report " );
    }

    @Override
    public void position( String account, Contract contract, double pos, double avgCost ) {
        System.out.println( contract );
        System.out.println( pos );
    }

    @Override
    public void positionEnd() {
        System.out.println( EWrapperMsgGenerator.positionEnd( ) );
    }

    @Override
    public void accountSummary( int reqId, String account, String tag, String value, String currency ) {
        System.out.println( EWrapperMsgGenerator.accountSummary( reqId, account, tag, value, currency ) );
    }

    @Override
    public void accountSummaryEnd( int reqId ) {
        System.out.println( EWrapperMsgGenerator.accountSummaryEnd( reqId ) );
    }

    @Override
    public void verifyMessageAPI( String apiData ) {
    }

    @Override
    public void verifyCompleted( boolean isSuccessful, String errorText ) {
    }

    @Override
    public void verifyAndAuthMessageAPI( String apiData, String xyzChallenge ) {
    }

    @Override
    public void verifyAndAuthCompleted( boolean isSuccessful, String errorText ) {
    }

    @Override
    public void displayGroupList( int reqId, String groups ) {
    }

    @Override
    public void displayGroupUpdated( int reqId, String contractInfo ) {
    }

    @Override
    public void positionMulti( int reqId, String account, String modelCode, Contract contract, double pos,
                               double avgCost ) {
        System.out.println( EWrapperMsgGenerator.positionMulti( reqId, account, modelCode, contract, pos, avgCost ) );
    }

    @Override
    public void positionMultiEnd( int reqId ) {
        System.out.println( EWrapperMsgGenerator.positionMultiEnd( reqId ) );
    }

    @Override
    public void accountUpdateMulti( int reqId, String account, String modelCode, String key, String value,
                                    String currency ) {
        System.out.println( EWrapperMsgGenerator.accountUpdateMulti( reqId, account, modelCode, key, value, currency ) );
    }

    @Override
    public void accountUpdateMultiEnd( int reqId ) {
        System.out.println( EWrapperMsgGenerator.accountUpdateMultiEnd( reqId ) );
    }

    public void connectAck() {
    }

    @Override
    public void securityDefinitionOptionalParameter( int reqId, String exchange, int underlyingConId,
                                                     String tradingClass, String multiplier, Set< String > expirations, Set< Double > strikes ) {
        System.out.println( EWrapperMsgGenerator.securityDefinitionOptionalParameter( reqId, exchange, underlyingConId,
                tradingClass, multiplier, expirations, strikes ) );
    }

    @Override
    public void securityDefinitionOptionalParameterEnd( int reqId ) {
        System.out.println( EWrapperMsgGenerator.securityDefinitionOptionalParameterEnd( reqId ) );
    }

    @Override
    public void softDollarTiers( int reqId, SoftDollarTier[] tiers ) {
        System.out.println( EWrapperMsgGenerator.softDollarTiers( reqId, tiers ) );
    }

    @Override
    public void familyCodes( FamilyCode[] familyCodes ) {
        System.out.println( EWrapperMsgGenerator.familyCodes( familyCodes ) );
    }

    @Override
    public void symbolSamples( int reqId, ContractDescription[] contractDescriptions ) {
        System.out.println( EWrapperMsgGenerator.symbolSamples( reqId, contractDescriptions ) );
    }

    @Override
    public void historicalDataEnd( int reqId, String startDateStr, String endDateStr ) {
        System.out.println( EWrapperMsgGenerator.historicalDataEnd( reqId, startDateStr, endDateStr ) );
    }

    @Override
    public void mktDepthExchanges( DepthMktDataDescription[] depthMktDataDescriptions ) {
        System.out.println( EWrapperMsgGenerator.mktDepthExchanges( depthMktDataDescriptions ) );
    }

    @Override
    public void tickNews( int tickerId, long timeStamp, String providerCode, String articleId, String headline,
                          String extraData ) {
        System.out.println(
                EWrapperMsgGenerator.tickNews( tickerId, timeStamp, providerCode, articleId, headline, extraData ) );
    }

    @Override
    public void smartComponents( int reqId, Map< Integer, Entry< String, Character > > theMap ) {
        System.out.println( EWrapperMsgGenerator.smartComponents( reqId, theMap ) );
    }

    @Override
    public void tickReqParams( int tickerId, double minTick, String bboExchange, int snapshotPermissions ) {
        System.out.println( EWrapperMsgGenerator.tickReqParams( tickerId, minTick, bboExchange, snapshotPermissions ) );
    }

    @Override
    public void newsProviders( NewsProvider[] newsProviders ) {
        System.out.println( EWrapperMsgGenerator.newsProviders( newsProviders ) );
    }

    @Override
    public void newsArticle( int requestId, int articleType, String articleText ) {
        System.out.println( EWrapperMsgGenerator.newsArticle( requestId, articleType, articleText ) );
    }

    @Override
    public void historicalNews( int requestId, String time, String providerCode, String articleId, String headline ) {
        System.out.println( EWrapperMsgGenerator.historicalNews( requestId, time, providerCode, articleId, headline ) );
    }

    @Override
    public void historicalNewsEnd( int requestId, boolean hasMore ) {
        System.out.println( EWrapperMsgGenerator.historicalNewsEnd( requestId, hasMore ) );
    }

    @Override
    public void headTimestamp( int reqId, String headTimestamp ) {
        System.out.println( EWrapperMsgGenerator.headTimestamp( reqId, headTimestamp ) );
    }

    @Override
    public void histogramData( int reqId, List< Entry< Double, Long > > items ) {
        System.out.println( EWrapperMsgGenerator.histogramData( reqId, items ) );
    }

    // ---------- Getters and Setters ---------- //
    public EClientSocket getClient() {
        return client;
    }

    public void setClient( EClientSocket client ) {
        this.client = client;
    }

    public int getNextOrderId() {
        int id = NextOrderId;
        NextOrderId++;
        return id;
    }

    public void setNextOrderId( int nextOrderId ) {
        NextOrderId = nextOrderId;
    }

    public void reqMktData( int tickerID, Contract contract ) {
        client.reqMktData( tickerID, contract, null, false, false, null );
        // 100,101,104,105,106,107,165,221,225,233,236,258,293,294,295,318
    }

    public MyTwsClient getTwsClient() {
        if ( twsClient == null ) {
            twsClient = new MyTwsClient( this );
        }
        return twsClient;
    }

    public void setTwsClient( MyTwsClient twsClient ) {
        this.twsClient = twsClient;
    }

}
