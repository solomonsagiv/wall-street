package tws.accounts;

import dataBase.mySql.MySql;
import gui.AlertWindow;
import tws.connection.TwsConnector;
import tws.connection.TwsFactory;
import tws.handlers.AccountAndConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class ConnectionsAndAccountHandler {

    private static final int TEST_AMOUNT = 1;
    public static HashMap<String, AccountAndConnection> accountAndConnectionHashMap = new HashMap<>();

    // Accounts name
    static class AccountsName {
        public static final String SASI = "SASI";
        public static final String GURU = "GURU";
        public static final String YOSI = "YOSI";
        public static final String DANIEL = "DANIEL";
        public static final String MOTI = "MOTI";
        public static final String SAGIV = "SAGIV";
        public static final String ELIKO = "ELIKO";
    }

    public static void load_data() {

        // Clear first
        accountAndConnectionHashMap.clear();

        // Load
        ResultSet rs = MySql.Queries.get_accounts_data(MySql.JIBE_PROD_CONNECTION);
        while (true) {
            try {
                if (!rs.next()) break;
                String name = rs.getString("name");
                long telegram_id = rs.getLong("id");
                int port = rs.getInt("p");
                int client_id = rs.getInt("c");
                int quantity = rs.getInt("q");
                boolean trading_bool = rs.getBoolean("trading");

                if (trading_bool) {
                    Account account = new Account(name, client_id, port, quantity, telegram_id);
                    TwsConnector twsConnector = new TwsConnector(account);
                    AccountAndConnection accountAndConnection = new AccountAndConnection(account, twsConnector);
                    add_account(name, accountAndConnection);
                    System.out.println(account);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }


    public static void add_account(String name, AccountAndConnection accountAndConnection) {
        accountAndConnectionHashMap.put(name, accountAndConnection);
    }


    public static String get_account_names() {
        StringBuilder text = new StringBuilder();

        for (AccountAndConnection accountAndConnection : accountAndConnectionHashMap.values()) {
            text.append(accountAndConnection.account.getAccountName());
            text.append("\n");
        }
        return text.toString();
    }

    public static String to_string() {

        StringBuilder text = new StringBuilder();

        for (AccountAndConnection accountAndConnection : accountAndConnectionHashMap.values()) {
            text.append(accountAndConnection.account.toString());
            text.append("\n");
        }
        return text.toString();
    }

    public static void connect_all_clients() {
        try {
            for (AccountAndConnection accountAndConnection : ConnectionsAndAccountHandler.accountAndConnectionHashMap.values()) {
                TwsConnector twsConnector = new TwsConnector(accountAndConnection.account);
                twsConnector.connect_client();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void connect_single_client(AccountAndConnection accountAndConnection) {
        try {
            TwsConnector twsConnector = new TwsConnector(accountAndConnection.account);
            twsConnector.connect_client();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void send_order_all_accounts() {
        try {
            for (AccountAndConnection accountAndConnection : ConnectionsAndAccountHandler.accountAndConnectionHashMap.values()) {
                //todo
                accountAndConnection.twsConnector.send_order(accountAndConnection.account, TwsFactory.Contracts.nq(), TwsFactory.Orders.market(accountAndConnection.account.getClient_id(), accountAndConnection.account.getQuantity()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            AlertWindow.Show(e.getMessage(), e.getCause(), e.getStackTrace());
        }
    }

    public static void send_single_account_order(AccountAndConnection accountAndConnection) {
        try {
            //todo
            accountAndConnection.twsConnector.send_order(accountAndConnection.account, TwsFactory.Contracts.nq(), TwsFactory.Orders.market(accountAndConnection.account.getClient_id(), accountAndConnection.account.getQuantity()));
        } catch (Exception e) {
            e.printStackTrace();
            AlertWindow.Show(e.getMessage(), e.getCause(), e.getStackTrace());
        }
    }

    public static void send_test_order_all_accounts() {
        try {
            for (AccountAndConnection accountAndConnection : ConnectionsAndAccountHandler.accountAndConnectionHashMap.values()) {
                //todo
                accountAndConnection.twsConnector.send_order(accountAndConnection.account, TwsFactory.Contracts.nq(), TwsFactory.Orders.market(accountAndConnection.account.getClient_id(), TEST_AMOUNT));
            }
        } catch (Exception e) {
            e.printStackTrace();
            AlertWindow.Show(e.getMessage(), e.getCause(), e.getStackTrace());
        }
    }

    public static void dis_connect_all_clients() {
        for (AccountAndConnection accountAndConnection : ConnectionsAndAccountHandler.accountAndConnectionHashMap.values()) {
            accountAndConnection.twsConnector.connect_client();
        }
    }

}
