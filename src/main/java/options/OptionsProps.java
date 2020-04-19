package options;

import charts.myChart.MyProps;
import locals.IJsonDataBase;
import myJson.MyJson;

import java.time.LocalDate;

public class OptionsProps extends MyProps implements IJsonDataBase {

    private double interestZero = 0;
    private double interest = 1;
    private double devidend = 0;
    private double days = 0;
    private LocalDate date;

    public MyJson getAsJson() {
        MyJson object = new MyJson();
        object.put( JsonEnum.INTEREST.toString(), getInterest() );
        object.put( JsonEnum.DEVIDEND.toString(), getDevidend() );
        object.put( JsonEnum.DATE.toString(), getDate() );
        object.put( JsonEnum.DAYS.toString(), getDays() );
        return object;
    }

    @Override
    public void loadFromJson( MyJson object ) {
        setInterestWithCalc( object.getDouble( JsonEnum.INTEREST.toString() ) );
        setDevidend( object.getDouble( JsonEnum.DEVIDEND.toString() ) );
        setDate( object.getDate( JsonEnum.DATE.toString() ) );
        setDays( object.getDouble( JsonEnum.DAYS.toString() ) );
    }

    @Override
    public MyJson getResetObject() {
        MyJson object = new MyJson();
        object.put( JsonEnum.INTEREST.toString(), 1 );
        object.put( JsonEnum.DEVIDEND.toString(), 0 );
        object.put( JsonEnum.DATE.toString(), getDate() );
        object.put( JsonEnum.DAYS.toString(), 0 );
        return object;
    }


    public void setInterestWithCalc( double interest ) {
        this.interestZero = interest * 0.01;
        this.interest = 1 + ( interest * 0.01 );
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

