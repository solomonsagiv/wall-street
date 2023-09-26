package tws.connection;

import com.ib.client.Contract;
import com.ib.client.Order;

public class TwsFactory {

    // Contract
    public static class Contracts {

        // ES
        public static Contract es() {
            Contract contract = new Contract();
            contract.symbol("ESZ3");
            contract.secType("FUT");
            contract.currency("USD");
            contract.exchange("CME");
            contract.multiplier("50");
            contract.tradingClass("ES");
            contract.lastTradeDateOrContractMonth("202312");
            return contract;
        }

        // NQ
        public static Contract nq() {
            Contract contract = new Contract();
            contract.symbol("NQZ3");
            contract.secType("FUT");
            contract.currency("USD");
            contract.exchange("CME");
            contract.multiplier("20");
            contract.tradingClass("NQ");
            contract.lastTradeDateOrContractMonth("202312");
            return contract;
        }
    }

    // Orders
    public static class Orders {
        public static Order market(int order_id, int quantity) {
            Order order = new Order();
            order.orderId(order_id);
            order.action("BUY");
            order.orderType("MKT");
            order.totalQuantity(quantity);
            return order;
        }
    }


}
