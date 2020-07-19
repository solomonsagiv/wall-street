package shlomi.dataBase;

import dataBase.mySql.MySql;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Spx;

import java.sql.ResultSet;

public class DB {

    // Variables
    BASE_CLIENT_OBJECT client;

    // Constructor
    public DB(BASE_CLIENT_OBJECT client) {
        this.client = client;
    }

    public static void main(String[] args) {
        DB db = new DB(Spx.getInstance());
        db.loadData();
    }

    public boolean loadData() {
        try {
            ResultSet rs = MySql.select("select * from stocks.spx where date = '2020-02-07';");

            while (rs.next()) {
                System.out.println(rs.getInt("id"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
