package serverObjects;

import com.ib.client.Contract;

public class TwsData {

    // Variables
    private Contract indexContract;
    private Contract futureContract;
    private Contract optionMonthContract;
    private Contract futureOptionContract;
    private Contract optionsDayContract;
    private Contract optionsQuarterContract;
    private Contract optionsQuarterFarContract;

    private int indexId;
    private int futureId;

    private int quantity = 0;

    // Getters and Setters
    public Contract getIndexContract() {
        return indexContract;
    }

    public void setIndexContract( Contract indexContract ) {
        this.indexContract = indexContract;
    }

    public Contract getFutureContract() {
        return futureContract;
    }

    public void setFutureContract( Contract futureContract ) {
        this.futureContract = futureContract;
    }

    public Contract getOptionMonthContract() {
        return optionMonthContract;
    }

    public void setOptionMonthContract( Contract optionMonthContract ) {
        this.optionMonthContract = optionMonthContract;
    }

    public Contract getFutureOptionContract() {
        return futureOptionContract;
    }

    public void setFutureOptionContract( Contract futureOptionContract ) {
        this.futureOptionContract = futureOptionContract;
    }

    public Contract getOptionsDayContract() {
        return optionsDayContract;
    }

    public void setOptionsDayContract( Contract optionsDayContract ) {
        this.optionsDayContract = optionsDayContract;
    }

    public Contract getOptionsQuarterContract() {
        return optionsQuarterContract;
    }

    public void setOptionsQuarterContract( Contract optionsQuarterContract ) {
        this.optionsQuarterContract = optionsQuarterContract;
    }

    public Contract getOptionsQuarterFarContract() {
        return optionsQuarterFarContract;
    }

    public void setOptionsQuarterFarContract( Contract optionsQuarterFarContract ) {
        this.optionsQuarterFarContract = optionsQuarterFarContract;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity( int quantity ) {
        this.quantity = quantity;
    }

    public int getIndexId() {
        return indexId;
    }

    public void setIndexId( int indexId ) {
        this.indexId = indexId;
    }

    public int getFutureId() {
        return futureId;
    }

    public void setFutureId( int futureId ) {
        this.futureId = futureId;
    }
}
