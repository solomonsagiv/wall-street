package jibeDataGraber;

public class DecisionsFunc {

    private double value = 0;
    private String name;
    private String table_location;

    public DecisionsFunc(String name, String table_location) {
        this.name = name;
        this.table_location = table_location;
    }

    public double getValue() {
        return value;
    };

    public void setValue(double value) {
        this.value = value;
    };

    public String getName() {
        return name;
    }

    public String getTable_location() {
        return table_location;
    }
}