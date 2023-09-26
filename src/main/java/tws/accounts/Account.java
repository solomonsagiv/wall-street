package tws.accounts;

import com.ib.client.EClientSocket;

public class Account {

    private EClientSocket eClient;
    private String accountName;
    private int quantity;
    private int port;
    private int client_id;
    private long telegram_id;
    private int order_id = 0;

    public Account(String accountName, int client_id , int port, int quantity, long telegram_id) {
        this.accountName = accountName;
        this.client_id = client_id;
        this.quantity = quantity;
        this.port = port;
        this.telegram_id = telegram_id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public long getTelegram_id() {
        return telegram_id;
    }

    public void setTelegram_id(long telegram_id) {
        this.telegram_id = telegram_id;
    }

    public EClientSocket geteClient() {
        return eClient;
    }

    public void seteClient(EClientSocket eClient) {
        this.eClient = eClient;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public void increment_order_id() {
        order_id++;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountName='" + accountName + '\'' +
                ", contractAmount=" + quantity +
                ", port=" + port +
                ", client_id=" + client_id +
                ", telegram_id=" + telegram_id +
                '}';
    }
}
