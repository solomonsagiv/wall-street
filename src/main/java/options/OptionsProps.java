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
        object.put( JsonStrings.interest.toString(), getInterest() );
        object.put( JsonStrings.devidend.toString(), getDevidend() );
        object.put( JsonStrings.date.toString(), getDate() );
        object.put( JsonStrings.days.toString(), getDays() );
        return object;
    }

    @Override
    public void loadFromJson( MyJson json ) {
        setInterest( json.getDouble( JsonStrings.interest.toString() ) );
        setInterestZero( json.getDouble( JsonStrings.interest.toString() ) - 1 );
        setDevidend( json.getDouble( JsonStrings.devidend.toString() ) );
        setDate( json.getDate( JsonStrings.date.toString() ) );
        setDays( json.getDouble( JsonStrings.days.toString() ) );
    }

    @Override
    public MyJson getResetJson() {
        MyJson object = new MyJson();
        object.put( JsonStrings.interest.toString(), 1 );
        object.put( JsonStrings.devidend.toString(), 0 );
        object.put( JsonStrings.date.toString(), getDate() );
        object.put( JsonStrings.days.toString(), 0 );
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


