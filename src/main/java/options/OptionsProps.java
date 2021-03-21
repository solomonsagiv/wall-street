package options;

import charts.myChart.MyProps;

import java.time.LocalDate;

public class OptionsProps extends MyProps {

    private double interestZero = 0;
    private double interest = 1;
    private double devidend = 0;
    private double days = 0;
    private LocalDate date;

    public void setInterestWithCalc(double interest) {
        setInterest(1 + (interest * 0.01));
        setInterestZero(interest * 0.01);

    }

    public double getInterestZero() {
        return interestZero;
    }

    private void setInterestZero(double interestZero) {
        this.interestZero = interestZero;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public double getDevidend() {
        return devidend;
    }

    public void setDevidend(double devidend) {
        this.devidend = devidend;
    }

    public double getDays() {
        return days;
    }

    public void setDays(double days) {
        this.days = days;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}


