package serverObjects.stockObjects;

import DDE.DDECells;
import api.tws.requesters.UltaRequester;
import dataBase.mySql.mySqlComps.TablesEnum;
import serverObjects.ApiEnum;

public class Ulta extends STOCK_OBJECT {

    static Ulta client = null;

    // Constrtor
    public Ulta() {
        setName("ulta");
        setStrikeMargin(2.5);
        setDbId(6);
        setiTwsRequester(new UltaRequester());
    }

    public static void main(String[] args) {
        Ulta ulta = Ulta.getInstance();
        ulta.getTablesHandler().getTable(TablesEnum.DAY).insert();
    }

    // Get instance
    public static Ulta getInstance() {
        if (client == null) {
            client = new Ulta();
        }
        return client;
    }

    @Override
    public double getTheoAvgMargin() {
        return 0.05;
    }

    @Override
    public ApiEnum getApi() {
        return ApiEnum.TWS;
    }

    @Override
    public void initBaseId() {
        setBaseId(60000);
    }
}
