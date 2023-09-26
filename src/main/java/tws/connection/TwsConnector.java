package tws.connection;

import com.ib.client.*;
import gui.AlertWindow;
import tws.accounts.Account;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class TwsConnector {

    private Account account;
    EJavaSignal signal;
    EClientSocket client;

    public TwsConnector(Account account) {
        this.account = account;
        signal = new EJavaSignal();
        client = new EClientSocket(new EWrapperImpl(), signal);
    }

    public boolean connect_client() {
        try {
            // Connect to IB TWS or IB Gateway
            client.eConnect("127.0.0.1", account.getPort(), account.getClient_id());
            account.seteClient(client);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            AlertWindow.Show(e.getMessage());
            return false;
        }
    }

    public void dis_connect_account(Account account) {
        // todo disconnect account
        try {
            client.eDisconnect();
        } catch (Exception e) {
            e.printStackTrace();
            AlertWindow.Show(e.getMessage(), e.getCause(), e.getStackTrace());
        }
    }

    public void send_order(Account account, Contract contract, Order order) {
        account.geteClient().placeOrder(account.getOrder_id(), contract, order);
    }


    // ---------------------------- Tws wrapper class ---------------------------- //
    class EWrapperImpl implements EWrapper {

        @Override
        public void tickPrice(int tickerId, int field, double price, TickAttr attrib) {
            System.out.println(tickerId + " " + field + " " + price);
        }

        @Override
        public void tickSize(int tickerId, int field, int size) {
            System.out.println(tickerId + " " + field + " " + size);
        }

        @Override
        public void tickOptionComputation(int tickerId, int field, double impliedVol, double delta, double optPrice, double pvDividend, double gamma, double vega, double theta, double undPrice) {

        }

        @Override
        public void tickGeneric(int tickerId, int tickType, double value) {

        }

        @Override
        public void tickString(int tickerId, int tickType, String value) {

        }

        @Override
        public void tickEFP(int tickerId, int tickType, double basisPoints, String formattedBasisPoints, double impliedFuture, int holdDays, String futureLastTradeDate, double dividendImpact, double dividendsToLastTradeDate) {

        }

        @Override
        public void orderStatus(int orderId, String status, double filled, double remaining, double avgFillPrice, int permId, int parentId, double lastFillPrice, int clientId, String whyHeld) {
            System.out.println("orderId = " + orderId + ", status = " + status + ", filled = " + filled + ", remaining = " + remaining + ", avgFillPrice = " + avgFillPrice + ", permId = " + permId + ", parentId = " + parentId + ", lastFillPrice = " + lastFillPrice + ", clientId = " + clientId + ", whyHeld = " + whyHeld);
        }

        @Override
        public void openOrder(int orderId, Contract contract, Order order, OrderState orderState) {
            System.out.println(orderId);
            System.out.println();
            System.out.println(contract);
            System.out.println();
            System.out.println(order);
        }

        @Override
        public void openOrderEnd() {

        }

        @Override
        public void updateAccountValue(String key, String value, String currency, String accountName) {

        }

        @Override
        public void updatePortfolio(Contract contract, double position, double marketPrice, double marketValue, double averageCost, double unrealizedPNL, double realizedPNL, String accountName) {

        }

        @Override
        public void updateAccountTime(String timeStamp) {

        }

        @Override
        public void accountDownloadEnd(String accountName) {

        }

        @Override
        public void nextValidId(int orderId) {
            System.out.println("----------------------------");
            System.out.println("Next valide id= " + orderId);
            System.out.println("----------------------------");

            account.setOrder_id(orderId);

        }

        @Override
        public void contractDetails(int reqId, ContractDetails contractDetails) {

        }

        @Override
        public void bondContractDetails(int reqId, ContractDetails contractDetails) {

        }

        @Override
        public void contractDetailsEnd(int reqId) {

        }

        @Override
        public void execDetails(int reqId, Contract contract, Execution execution) {

        }

        @Override
        public void execDetailsEnd(int reqId) {

        }

        @Override
        public void updateMktDepth(int tickerId, int position, int operation, int side, double price, int size) {

        }

        @Override
        public void updateMktDepthL2(int tickerId, int position, String marketMaker, int operation, int side, double price, int size) {

        }

        @Override
        public void updateNewsBulletin(int msgId, int msgType, String message, String origExchange) {

        }

        @Override
        public void managedAccounts(String accountsList) {

        }

        @Override
        public void receiveFA(int faDataType, String xml) {

        }

        @Override
        public void historicalData(int reqId, String date, double open, double high, double low, double close, int volume, int count, double WAP, boolean hasGaps) {

        }

        @Override
        public void scannerParameters(String xml) {

        }

        @Override
        public void scannerData(int reqId, int rank, ContractDetails contractDetails, String distance, String benchmark, String projection, String legsStr) {

        }

        @Override
        public void scannerDataEnd(int reqId) {

        }

        @Override
        public void realtimeBar(int reqId, long time, double open, double high, double low, double close, long volume, double wap, int count) {

        }

        @Override
        public void currentTime(long time) {

        }

        @Override
        public void fundamentalData(int reqId, String data) {

        }

        @Override
        public void deltaNeutralValidation(int reqId, DeltaNeutralContract underComp) {

        }

        @Override
        public void tickSnapshotEnd(int reqId) {

        }

        @Override
        public void marketDataType(int reqId, int marketDataType) {

        }

        @Override
        public void commissionReport(CommissionReport commissionReport) {

        }

        @Override
        public void position(String account, Contract contract, double pos, double avgCost) {

        }

        @Override
        public void positionEnd() {

        }

        @Override
        public void accountSummary(int reqId, String account, String tag, String value, String currency) {

        }

        @Override
        public void accountSummaryEnd(int reqId) {

        }

        @Override
        public void verifyMessageAPI(String apiData) {

        }

        @Override
        public void verifyCompleted(boolean isSuccessful, String errorText) {

        }

        @Override
        public void verifyAndAuthMessageAPI(String apiData, String xyzChallange) {

        }

        @Override
        public void verifyAndAuthCompleted(boolean isSuccessful, String errorText) {

        }

        @Override
        public void displayGroupList(int reqId, String groups) {

        }

        @Override
        public void displayGroupUpdated(int reqId, String contractInfo) {

        }

        @Override
        public void error(Exception e) {
            System.err.println(e);
        }

        @Override
        public void error(String str) {
            System.err.println(str);
        }

        @Override
        public void error(int id, int errorCode, String errorMsg) {
            System.err.println(id + " " + errorCode + " " + errorMsg);
        }

        @Override
        public void connectionClosed() {

        }

        @Override
        public void connectAck() {

        }

        @Override
        public void positionMulti(int reqId, String account, String modelCode, Contract contract, double pos, double avgCost) {

        }

        @Override
        public void positionMultiEnd(int reqId) {

        }

        @Override
        public void accountUpdateMulti(int reqId, String account, String modelCode, String key, String value, String currency) {

        }

        @Override
        public void accountUpdateMultiEnd(int reqId) {

        }

        @Override
        public void securityDefinitionOptionalParameter(int reqId, String exchange, int underlyingConId, String tradingClass, String multiplier, Set<String> expirations, Set<Double> strikes) {

        }

        @Override
        public void securityDefinitionOptionalParameterEnd(int reqId) {

        }

        @Override
        public void softDollarTiers(int reqId, SoftDollarTier[] tiers) {

        }

        @Override
        public void familyCodes(FamilyCode[] familyCodes) {

        }

        @Override
        public void symbolSamples(int reqId, ContractDescription[] contractDescriptions) {

        }

        @Override
        public void historicalDataEnd(int reqId, String startDateStr, String endDateStr) {

        }

        @Override
        public void mktDepthExchanges(DepthMktDataDescription[] depthMktDataDescriptions) {

        }

        @Override
        public void tickNews(int tickerId, long timeStamp, String providerCode, String articleId, String headline, String extraData) {

        }

        @Override
        public void smartComponents(int reqId, Map<Integer, Map.Entry<String, Character>> theMap) {

        }

        @Override
        public void tickReqParams(int tickerId, double minTick, String bboExchange, int snapshotPermissions) {

        }

        @Override
        public void newsProviders(NewsProvider[] newsProviders) {

        }

        @Override
        public void newsArticle(int requestId, int articleType, String articleText) {

        }

        @Override
        public void historicalNews(int requestId, String time, String providerCode, String articleId, String headline) {

        }

        @Override
        public void historicalNewsEnd(int requestId, boolean hasMore) {

        }

        @Override
        public void headTimestamp(int reqId, String headTimestamp) {

        }

        @Override
        public void histogramData(int reqId, List<Map.Entry<Double, Long>> items) {

        }
        // Implement the required methods here...
        // ...


    }
}



