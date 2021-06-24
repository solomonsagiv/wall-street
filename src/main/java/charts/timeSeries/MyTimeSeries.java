package charts.timeSeries;

import charts.myChart.MyProps;
import lists.MyDoubleList;
import myJson.MyJson;
import options.JsonStrings;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesDataItem;
import serverObjects.BASE_CLIENT_OBJECT;

import java.awt.*;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.text.ParseException;
import java.time.LocalDateTime;

interface ITimeSeries {
    double getData() throws UnknownHostException;
    void load_data();
}

public abstract class MyTimeSeries extends TimeSeries implements ITimeSeries {

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


    // Constructor
    public MyTimeSeries(Comparable name, BASE_CLIENT_OBJECT client) {
        super(name);
        this.name = (String) name;
        this.series_type = (String) name;
        this.client = client;
        myValues = new MyDoubleList();
    }

    // Constructor
    public MyTimeSeries(Comparable name, BASE_CLIENT_OBJECT client, boolean scaled) {
        this(name, client);
        this.scaled = scaled;
    }

    public double getScaledData() {
        return getMyValues().getLastValAsStd();
    }

    public double getScaledData(RegularTimePeriod timePeriod) {
        double data = (double) getDataItem(timePeriod).getValue();
        return myValues.scaled(data);
    }

    public double getScaledData(int index) {
        double data = (double) getDataItem(index).getValue();
        return myValues.scaled(data);
    }

    public abstract ResultSet load_last_x_time(int minuts);

    public MyJson getColorJson() {
        MyJson myJson = new MyJson();
        myJson.put("r", color.getRed());
        myJson.put("g", color.getGreen());
        myJson.put("b", color.getBlue());
        return myJson;
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
        try {

            Second second = new Second(time.getSecond(), time.getMinute(), time.getHour(), time.getDayOfMonth(), time.getMonthValue(), time.getYear());
            double data = getData();
            getMyValues().add(data);

            if (scaled) {
                data = getMyValues().getLastValAsStd();
            }

            addOrUpdate(second, data);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public MyDoubleList getMyValues() {
        return myValues;
    }

    public MyJson getLastJson() throws ParseException {
        TimeSeriesDataItem item = getLastItem();
        LocalDateTime localDateTime = LocalDateTime.now();
        MyJson json = new MyJson();
        json.put(JsonStrings.x, localDateTime);
        json.put(JsonStrings.y, item.getValue());
        return json;
    }

    public void clear_data() {
        if (myValues.size()  > 2) {
            data.clear();
            myValues.clear();
        }
    }

    public void add(LocalDateTime dateTime, double value) {
        try {
            lastSeconde = new Second(dateTime.getSecond(), dateTime.getMinute(), dateTime.getHour(), dateTime.getDayOfMonth(), dateTime.getMonthValue(), dateTime.getYear());

            myValues.add(value);
            addOrUpdate(lastSeconde, value);
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
                myValues.add(data);
                addOrUpdate(lastSeconde, data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double add() {
        double data = 0;
        // live data
        try {
            data = getData();
            myValues.add(data);
            addOrUpdate(getLastSeconde(), data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        lastSeconde = (Second) lastSeconde.next();
        return data;
    }

    public void remove(int index) {
        delete(index, index);
        myValues.remove(index);
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
}