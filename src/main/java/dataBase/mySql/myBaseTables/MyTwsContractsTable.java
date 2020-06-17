package dataBase.mySql.myBaseTables;

import api.tws.TwsHandler;
import arik.Arik;
import dataBase.mySql.MySql;
import dataBase.mySql.mySqlComps.MySqlTable;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Ndx;
import tws.MyContract;
import tws.TwsContractsEnum;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public abstract class MyTwsContractsTable extends MySqlTable {


    // Constructor
    public MyTwsContractsTable(BASE_CLIENT_OBJECT client ) {
        super(client, "twsContracts");
    }

    @Override
    public void insert() {
    }

    public boolean isExist(int id) throws SQLException {
        System.out.println(id);
        boolean exist = false;

        String sql = "SELECT * FROM stocks.twsContracts;";

        ResultSet rs = MySql.select(sql);

        while (rs.next()) {
            if (rs.getInt("id") == id) {
                exist = true;
                break;
            }
        }

        return exist;

    }

    public void insertOrUpdate(MyContract contract) throws SQLException {
        if (isExist(contract.getMyId())) {
            update(contract);
        } else {
            insert(contract);
        }
    }

    private void insert(MyContract contract) throws SQLException {

        // Values
        int id = contract.getMyId();
        String stockName = getClient().getName();
        String contractName = contract.getType().toString();
        String secType = contract.getSecType();
        String currency = contract.currency();
        String exchange = contract.exchange();
        String tradingClass = contract.tradingClass();
        String multiplier = contract.multiplier();
        String primaryExchange = contract.primaryExch();
        String symbol = contract.symbol();
        boolean includExpired = contract.includeExpired();
        String lastTradingDayOrContractMonth = contract.lastTradeDateOrContractMonth();

        // the mysql insert statement
        String query = " INSERT INTO stocks.twsContracts (id, stockName, contractName, secType, currency, exchange, tradingClass, multiplier, primaryExchange, symbol, includExpired, lastTradingDayOrContractMonth)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        // create the mysql insert preparedstatement
        PreparedStatement stmt = MySql.getPool().getConnection().prepareStatement(query);
        stmt.setInt (1, id);
        stmt.setString (2, stockName);
        stmt.setString (3, contractName);
        stmt.setString (4, secType);
        stmt.setString (5, currency);
        stmt.setString (6, exchange);
        stmt.setString (7, tradingClass);
        stmt.setString (8, multiplier);
        stmt.setString (9, primaryExchange);
        stmt.setString (10, symbol);
        stmt.setBoolean (11, includExpired);
        stmt.setString (12, lastTradingDayOrContractMonth);

        stmt.execute();
    }


    private void update( MyContract contract ) throws SQLException {

        String query = "UPDATE stocks.twsContracts SET stockName = ?, contractName = ?, secType = ?, currency = ?, exchange = ?, tradingClass = ?, multiplier = ?, primaryExchange = ?, symbol = ?, includExpired = ?, lastTradingDayOrContractMonth = ? WHERE id = ?";

        // Values
        int id = contract.getMyId();
        String stockName = getClient().getName();
        String contractName = contract.getType().toString();
        String secType = contract.getSecType();
        String currency = contract.currency();
        String exchange = contract.exchange();
        String tradingClass = contract.tradingClass();
        String multiplier = contract.multiplier();
        String primaryExchange = contract.primaryExch();
        String symbol = contract.symbol();
        int includExpired = contract.includeExpired() ? 1 : 0;
        String lastTradingDayOrContractMonth = contract.lastTradeDateOrContractMonth();

        PreparedStatement stmt = MySql.getPool().getConnection().prepareStatement(query);
        stmt.setString(1, stockName);
        stmt.setString(2, contractName);
        stmt.setString(3, secType);
        stmt.setString(4, currency);
        stmt.setString(5, exchange);
        stmt.setString(6, tradingClass);
        stmt.setString(7, multiplier);
        stmt.setString(8, primaryExchange);
        stmt.setString(9, symbol);
        stmt.setInt(10, includExpired);
        stmt.setString(11, lastTradingDayOrContractMonth);
        stmt.setInt(12, id);

        stmt.executeUpdate();

    }

    @Override
    public void load() {
        try {
            TwsHandler twsHandler = client.getTwsHandler();

            String query = String.format("SELECT * FROM stocks.%s WHERE stockName ='%s'", getName(), client.getName());

            System.out.println(query);
            ResultSet rs = MySql.select(query);

            while (rs.next()) {
                MyContract contract = new MyContract();
                contract.setMyId(rs.getInt("id"));
                contract.setType( rs.getString( "contractName" ));
                contract.secType(rs.getString("secType"));
                contract.currency(rs.getString("currency"));
                contract.exchange(rs.getString("exchange"));
                contract.tradingClass(rs.getString("tradingClass"));
                contract.primaryExch(rs.getString("primaryExchange"));
                contract.symbol(rs.getString("symbol"));
                contract.includeExpired(rs.getBoolean("includExpired"));
                contract.lastTradeDateOrContractMonth(rs.getString("lastTradingDayOrContractMonth"));

                if (!twsHandler.isExist(contract) ) {
                    twsHandler.addContract(contract);
                } else {
                    twsHandler.getMyContracts().put(contract.getType(), contract);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Arik.getInstance().sendErrorMessage(e);
        } finally {
            setLoad(true);
        }
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void reset() {

    }

}
