package dataTable;

import java.time.LocalTime;

public class RowData {

    private LocalTime time;
    private double ind = 0;
    private double indBid = 0;
    private double indAsk = 0;
    private double futDay = 0;
    private double futWeek = 0;
    private double futMonth = 0;
    private double futE1 = 0;
    private double futE2 = 0;

    public RowData( double ind, double indBid, double indAsk, double futDay, double futWeek, double futMonth, double futE1, double futE2 ) {
        this.time = LocalTime.now();
        this.ind = ind;
        this.indBid = indBid;
        this.indAsk = indAsk;
        this.futDay = futDay;
        this.futWeek = futWeek;
        this.futMonth = futMonth;
        this.futE1 = futE1;
        this.futE2 = futE2;
    }

    public double getFutDay() {
        return futDay;
    }

    public double getFutWeek() {
        return futWeek;
    }

    public double getFutMonth() {
        return futMonth;
    }

    public double getFutE1() {
        return futE1;
    }

    public double getFutE2() {
        return futE2;
    }

    public LocalTime getTime() {
        return time;
    }

    public double getInd() {
        return ind;
    }

    public double getIndBid() {
        return indBid;
    }

    public double getIndAsk() {
        return indAsk;
    }

}
