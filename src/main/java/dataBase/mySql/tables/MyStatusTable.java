package dataBase.mySql.tables;

import dataBase.mySql.MySql;
import dataBase.mySql.mySqlComps.MyColumnSql;
import dataBase.mySql.mySqlComps.MyTableSql;
import options.Options;
import org.json.JSONObject;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.DaxCLIENTObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;

public class MyStatusTable extends MyTableSql {


    public static void main(String[] args) {
        MyStatusTable myStatusTable = new MyStatusTable(DaxCLIENTObject.getInstance(), "status");
        myStatusTable.update();
    }

    private MyColumnSql<String> name;
    private MyColumnSql<String> time;
    private MyColumnSql<Double> ind;
    private MyColumnSql<Integer> conUp;
    private MyColumnSql<Integer> conDown;
    private MyColumnSql<Integer> indUp;
    private MyColumnSql<Integer> indDown;
    private MyColumnSql<Double> base;
    private MyColumnSql<Double> open;
    private MyColumnSql<Double> high;
    private MyColumnSql<Double> low;
    private MyColumnSql<String> options;

    public MyStatusTable(BASE_CLIENT_OBJECT client, String name) {
        super(client, name);
    }

    @Override
    public void initColumns() {
        name = new MyColumnSql<>(this, "name") {
            @Override
            public String getObject() {
                return client.getName();
            }

            @Override
            public void setLoadedObject(String name) {
            }

            @Override
            public Class getClassType() {
                return String.class;
            }
        };

        time = new MyColumnSql<>(this, "time") {
            @Override
            public String getObject() {
                return LocalTime.now().toString();
            }

            @Override
            public void setLoadedObject(String object) {
            }

            @Override
            public Class getClassType() {
                return String.class;
            }
        };

        ind = new MyColumnSql<>(this, "ind") {
            @Override
            public Double getObject() {
                return client.getIndex();
            }

            @Override
            public void setLoadedObject(Double object) {
            }

            @Override
            public Class getClassType() {
                return Double.class;
            }
        };

        conUp = new MyColumnSql<>(this, "conUp") {
            @Override
            public Integer getObject() {
                return client.getConUp();
            }

            @Override
            public void setLoadedObject(Integer object) {
                client.setConUp(object);
            }

            @Override
            public Class getClassType() {
                return Integer.class;
            }
        };

        conDown = new MyColumnSql<>(this, "conDown") {
            @Override
            public Integer getObject() {
                return client.getConDown();
            }

            @Override
            public void setLoadedObject(Integer object) {
                client.setConDown(object);
            }

            @Override
            public Class getClassType() {
                return Integer.class;
            }
        };

        indUp = new MyColumnSql<>(this, "indUp") {
            @Override
            public Integer getObject() {
                return client.getIndexUp();
            }

            @Override
            public void setLoadedObject(Integer object) {
                client.setIndexUp(object);
            }

            @Override
            public Class getClassType() {
                return Integer.class;
            }
        };

        indDown = new MyColumnSql<>(this, "indDown") {
            @Override
            public Integer getObject() {
                return client.getIndexDown();
            }

            @Override
            public void setLoadedObject(Integer object) {
                client.setIndexDown(object);
            }

            @Override
            public Class getClassType() {
                return Integer.class;
            }
        };

        base = new MyColumnSql<>(this, "base") {
            @Override
            public Double getObject() {
                return client.getBase();
            }

            @Override
            public void setLoadedObject(Double object) {
            }

            @Override
            public Class getClassType() {
                return Double.class;
            }
        };

        open = new MyColumnSql<>(this, "open") {
            @Override
            public Double getObject() {
                return client.getOpen();
            }

            @Override
            public void setLoadedObject(Double object) {
            }

            @Override
            public Class getClassType() {
                return Double.class;
            }
        };

        high = new MyColumnSql<>(this, "high") {
            @Override
            public Double getObject() {
                return client.getHigh();
            }

            @Override
            public void setLoadedObject(Double object) {
            }

            @Override
            public Class getClassType() {
                return Double.class;
            }
        };

        low = new MyColumnSql<>(this, "low") {
            @Override
            public Double getObject() {
                return client.getLow();
            }

            @Override
            public void setLoadedObject(Double object) {
            }

            @Override
            public Class getClassType() {
                return Double.class;
            }
        };

        options = new MyColumnSql<>(this, "options") {
            @Override
            public String getObject() {
                return client.getOptionsHandler().getAllOptionsAsJson().toString();
            }

            @Override
            public void setLoadedObject(String object) {
                JSONObject optionsData = new JSONObject(object);
                for (Options options : client.getOptionsHandler().getOptionsList()) {
                    options.setDataFromJson(optionsData.getJSONObject(options.getName()));
                }
            }

            @Override
            public Class getClassType() {
                return String.class;
            }
        };
    }

    @Override
    public void insert() {
    }

    @Override
    public void load() {
        try {
            String query = String.format("SELECT * FROM stock.%s WHERE id ='%S'", name, client.getDbId());
            ResultSet rs = MySql.select(query);

            while (true) {

                if (!rs.next()) break;

                    for (MyColumnSql column: columns) {
                        if (column.getClassType().getClass() instanceof String.getc){

                        }
                    }

                String lastName = rs.getString("Lname");
                System.out.println(lastName + "\n");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void update() {
        super.updateFromSuper();
    }

    @Override
    public void reset() {

    }

    @Override
    public MyTableSql getObject() {
        return null;
    }
}
