package tables;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class TableDayFather {

    @Id
    @GenericGenerator( name = "sagiv", strategy = "increment" )
    @GeneratedValue( generator = "sagiv" )
    @Column( name = "id" )
    private int id;
    @Column( name = "date" )
    private String date;
    @Column( name = "exp_name" )
    private String exp_name;
    @Column( name = "time" )
    private String time;
    @Column( name = "con" )
    private double con = 0;
    @Column( name = "conDay" )
    private double conDay = 0;
    @Column( name = "conMonth" )
    private double conMonth = 0;
    @Column( name = "conQuarter" )
    private double conQuarter = 0;
    @Column( name = "conQuarterFar" )
    private double conQuarterFar = 0;
    @Column( name = "ind" )
    private double index = 0;
    @Column( name = "con_up" )
    private int con_up = 0;
    @Column( name = "con_down" )
    private int con_down = 0;
    @Column( name = "index_up" )
    private int index_up = 0;
    @Column( name = "index_down" )
    private int index_down = 0;
    @Column( name = "options" )
    private String options;
    @Column( name = "base" )
    private double base = 0;
    @Column( name = "op_avg" )
    private double opAvg = 0;

    public TableDayFather( String date, String exp_name, String time, double con, double index, int future_up,
                           int future_down, int index_up, int index_down, String options, double base, double opAvg,
                           double conDay, double conMonth, double conQuarter, double conQuarterFar ) {
        this.date = date;
        this.exp_name = exp_name;
        this.time = time;
        this.con = con;
        this.index = index;
        this.con_up = future_up;
        this.con_down = future_down;
        this.index_up = index_up;
        this.index_down = index_down;
        this.options = options;
        this.base = base;
        this.opAvg = opAvg;
        this.conDay = conDay;
        this.conMonth = conMonth;
        this.conQuarter = conQuarter;
        this.conQuarterFar = conQuarterFar;
    }

    public TableDayFather() {
    }

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate( String date ) {
        this.date = date;
    }

    public String getExp_name() {
        return exp_name;
    }

    public void setExp_name( String exp_name ) {
        this.exp_name = exp_name;
    }

    public String getTime() {
        return time;
    }

    public void setTime( String time ) {
        this.time = time;
    }

    public double getCon() {
        return con;
    }

    public void setCon( double con ) {
        this.con = con;
    }

    public double getIndex() {
        return index;
    }

    public void setIndex( double index ) {
        this.index = index;
    }

    public int getCon_up() {
        return con_up;
    }

    public void setCon_up( int con_up ) {
        this.con_up = con_up;
    }

    public int getCon_down() {
        return con_down;
    }

    public void setCon_down( int con_down ) {
        this.con_down = con_down;
    }

    public int getIndex_up() {
        return index_up;
    }

    public void setIndex_up( int index_up ) {
        this.index_up = index_up;
    }

    public int getIndex_down() {
        return index_down;
    }

    public void setIndex_down( int index_down ) {
        this.index_down = index_down;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions( String options ) {
        this.options = options;
    }

    public double getBase() {
        return base;
    }

    public void setBase( double base ) {
        this.base = base;
    }

    public double getOpAvg() {
        return opAvg;
    }

    public void setOpAvg( double opAvg ) {
        this.opAvg = opAvg;
    }

    public double getConDay() {
        return conDay;
    }

    public void setConDay( double conDay ) {
        this.conDay = conDay;
    }

    public double getConMonth() {
        return conMonth;
    }

    public void setConMonth( double conMonth ) {
        this.conMonth = conMonth;
    }

    public double getConQuarter() {
        return conQuarter;
    }

    public void setConQuarter( double conQuarter ) {
        this.conQuarter = conQuarter;
    }

    public double getConQuarterFar() {
        return conQuarterFar;
    }

    public void setConQuarterFar( double conQuarterFar ) {
        this.conQuarterFar = conQuarterFar;
    }

    @Override
    public String toString() {
        return "TableDayFather [date=" + date + ", exp_name=" + exp_name + ", time=" + time + ", future=" + con
                + ", index=" + index + ", future_up=" + con_up + ", future_down=" + con_down + ", index_up="
                + index_up + ", index_down=" + index_down + ", options=" + options + ", base=" + base + "]";
    }

}
