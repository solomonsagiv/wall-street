package tws.handlers;

import tws.accounts.Account;
import tws.connection.TwsConnector;

public class AccountAndConnection {

    public Account account;
    public TwsConnector twsConnector;

    public AccountAndConnection(Account account, TwsConnector twsConnector) {
        this.account = account;
        this.twsConnector = twsConnector;
    }
}
