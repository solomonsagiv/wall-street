package charts.timeSeries;

import charts.myChart.MyProps;
import lists.MyDoubleList;
import myJson.MyJson;
import options.JsonStrings;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesDataItem;
import serverObjects.BASE_CLIENT_OBJECT;

import java.awt.*;
import java.time.LocalDateTime;

interface ITimeSeries {
    double getValue();

    void updateData();

    void load();

    void load_exp_data();
}

public abstract class MyTimeSeries extends TimeSeries implements ITimeSeries {

    private double value;
    private int serie_database_id = 0;
    private int id = 0;
    public static final int TIME = 0;
    public static final int VALUE = 1;
    protected BASE_CLIENT_OBJECT client;
    MyProps props;
    private String name;
    Second lastSeconde;
    private Color color;
    private float stokeSize;
    private boolean scaled = false;
    private boolean visible = true;
    MyDoubleList myValues;
    private String series_type;
    private boolean load = false;
    private double exp_data = 0;

    // Constructor
    public MyTimeSeries(Comparable name, BASE_CLIENT_OBJECT client) {
        super(name);
        this.name = (String) name;
        this.series_type = (String) name;
        this.client = client;
        myValues = new MyDoubleList();
    }

    public void load_data() {
        try {
            if (!isLoad()) {
                load();
            }
            setLoad(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double getScaledData(int index) {
        double data = (double) getDataItem(index).getValue();
        return myValues.scaled(data);
    }

    public MyProps getProps() {
        return props;
    }

    public String getSeries_type() {
        return series_type;
    }

    public TimeSeriesDataItem getLastItem() {
        return getDataItem(getItemCount() - 1);
    }

    public void add(LocalDateTime time) {
        Second second = new Second(time.getSecond(), time.getMinute(), time.getHour(), time.getDayOfMonth(), time.getMonthValue(), time.getYear());
        double data = getValue();
        if (data != 0) {
            getMyValues().add(data);
            if (scaled) {
                data = getMyValues().getLastValAsStd();
            }
            addOrUpdate(second, data);
        }
    }

    public MyDoubleList getMyValues() {
        return myValues;
    }


    public void add(LocalDateTime dateTime, double value) {
        try {
            if (value != 0) {
                lastSeconde = new Second(dateTime.getSecond(), dateTime.getMinute(), dateTime.getHour(), dateTime.getDayOfMonth(), dateTime.getMonthValue(), dateTime.getYear());
                myValues.add(value);
                addOrUpdate(lastSeconde, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void add(MyJson json) {
        try {
            if (!json.getString(JsonStrings.x).isEmpty()) {

                LocalDateTime dateTime = LocalDateTime.parse(json.getString(JsonStrings.x));
                lastSeconde = new Second(dateTime.getSecond(), dateTime.getMinute(), dateTime.getHour(), dateTime.getDayOfMonth(), dateTime.getMonthValue(), dateTime.getYear());

                double data = json.getDouble(JsonStrings.y);
                if (data != 0) {
                    myValues.add(data);
                    addOrUpdate(lastSeconde, data);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double add() {
        double data = 0;
        // live data
        try {
            data = getValue();
            if (data != 0) {
                myValues.add(data);
                addOrUpdate(getLastSeconde(), data);
                lastSeconde = (Second) lastSeconde.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    public void remove(int index) {
        delete(index, index);
        myValues.remove(index);
    }

    public double get_value_with_exp() {
        return getValue() + getExp_data();
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public float getStokeSize() {
        return stokeSize;
    }

    public void setStokeSize(float stokeSize) {
        this.stokeSize = stokeSize;
    }

    public boolean isScaled() {
        return scaled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Second getLastSeconde() {
        if (lastSeconde == null) {
            lastSeconde = new Second();
        }
        return lastSeconde;
    }

    public boolean isLoad() {
        return load;
    }

    public void setLoad(boolean load) {
        this.load = load;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getExp_data() {
        return exp_data;
    }

    public void setExp_data(double exp_data) {
        this.exp_data = exp_data;
    }
}