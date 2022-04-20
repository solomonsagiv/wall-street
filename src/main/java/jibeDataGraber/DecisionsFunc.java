package jibeDataGraber;

public class DecisionsFunc {

    private double value = 0;
    private String name;
    private String table_location;
    private int session_id = 0;
    private int version = 0;
    private int single_dec = 0;
    private boolean is_cdf;

    public DecisionsFunc(String name, String table_location, boolean is_cdf) {
        this.name = name;
        this.table_location = table_location;
        this.is_cdf = is_cdf;
    }

    public DecisionsFunc(String name, String table_location, boolean is_cdf, int session_id, int version) {
        this(name, table_location, is_cdf);
        this.session_id = session_id;
        this.version = version;
    }

    public DecisionsFunc(String name, String table_location, boolean is_cdf, int single_dec) {
        this(name, table_location, is_cdf);
        this.single_dec = single_dec;
    }

    public double getValue() {
        return value;
    };
    
    public void setValue(double value) {
        if (value != 0) {
            this.value = value;
        }
    };

    public boolean isIs_cdf() {
        return is_cdf;
    }

    public void setIs_cdf(boolean is_cdf) {
        this.is_cdf = is_cdf;
    }

    public int getSingle_dec() {
        return single_dec;
    }

    public String getName() {
        return name;
    }

    public String getTable_location() {
        return table_location;
    }

    public int getSession_id() {
        return session_id;
    }

    public void setSession_id(int session_id) {
        this.session_id = session_id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}