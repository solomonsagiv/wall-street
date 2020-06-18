package options;

import charts.myChart.MyProps;
import locals.IJson;
import myJson.MyJson;

import java.time.LocalDate;

public class OptionsProps extends MyProps implements IJson {

    private double interestZero = 0;
    private double interest = 1;
    private double devidend = 0;
    private double days = 0;
    private LocalDate date;

    public MyJson getAsJson() {
        MyJson object = new MyJson();
        object.put( JsonStrings.interest, getInterest() );
        object.put( JsonStrings.devidend, getDevidend() );
        object.put( JsonStrings.date, getDate() );
        object.put( JsonStrings.days, getDays() );
        return object;
    }

    @Override
    public void loadFromJson( MyJson json ) {
        setInterest( json.getDouble( JsonStrings.interest ) );
        setInterestZero( json.getDouble( JsonStrings.interest ) - 1 );
        setDevidend( json.getDouble( JsonStrings.devidend ) );
        setDate( json.getDate( JsonStrings.date ) );
        setDays( json.getDouble( JsonStrings.days ) );
    }

    @Override
    public MyJson getResetJson() {
        MyJson object = new MyJson();
        object.put( JsonStrings.interest, 1 );
        object.put( JsonStrings.devidend, 0 );
        object.put( JsonStrings.date, getDate() );
        object.put( JsonStrings.days, 0 );
        return object;
    }

    public void setInterestWithCalc( double interest ) {
        setInterest( 1 + ( interest * 0.01 ) );
        setInterestZero( interest * 0.01 );

    }

    private void setInterestZero( double interestZero ) {
        this.interestZero = interestZero;
    }

    public double getInterestZero() {
        return interestZero;
    }
    public double getInterest() {
        return interest;
    }
    public void setInterest( double interest ) {
        this.interest = interest;
    }
    public double getDevidend() {
        return devidend;
    }
    public void setDevidend( double devidend ) {
        this.devidend = devidend;
    }
    public double getDays() {
        return days;
    }
    public void setDays( double days ) {
        this.days = days;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate( LocalDate date ) {
        this.date = date;
    }
}


