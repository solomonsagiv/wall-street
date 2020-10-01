package exp;

import charts.myChart.MyTimeSeries;
import delta.DeltaCalc;
import options.OptionsDDeCells;
import options.optionsCalcs.IOptionsCalcs;
import serverObjects.BASE_CLIENT_OBJECT;
import tws.TwsContractsEnum;

import java.net.UnknownHostException;

public class E extends Exp {

    protected double delta = 0;
    protected int volumeFutForDelta = 0;
    protected double futForDelta = 0;
    protected double futBidForDelta = 0;
    protected double futAskForDelta = 0;
    protected double preFutBidForDelta = 0;
    private double preFutAskForDelta = 0;
    private MyTimeSeries deltaSerie;

    public E( BASE_CLIENT_OBJECT client, String expEnum, TwsContractsEnum contractsEnum, IOptionsCalcs iOptionsCalcs ) {
        super( client, expEnum, contractsEnum, iOptionsCalcs );
        initSeries();
    }

    public E( BASE_CLIENT_OBJECT client, String expEnum, TwsContractsEnum twsContractsEnum, IOptionsCalcs iOptionsCalcs, OptionsDDeCells optionsDDeCells ) {
        super( client, expEnum, twsContractsEnum, iOptionsCalcs, optionsDDeCells );
        initSeries();
    }

    public double getDelta() {
        return delta;
    }

    public void setDelta( double delta ) {
        this.delta = delta;
    }

    public int getVolumeFutForDelta() {
        return volumeFutForDelta;
    }

    public void setVolumeFutForDelta( int volumeFutForDelta ) {
        int quantity = volumeFutForDelta - this.volumeFutForDelta;
        this.delta += DeltaCalc.calc(quantity, getFutForDelta(), getPreFutBidForDelta(), getPreFutAskForDelta());
        this.volumeFutForDelta = volumeFutForDelta;
        System.out.println( delta + " " + getName() );
    }

    public void initSeries() {
        super.initSeries();
        deltaSerie = new MyTimeSeries("Delta", client) {
            @Override
            public double getData() throws UnknownHostException {
                return delta;
            }
        };
    }

    public double getFutBidForDelta() {
        return futBidForDelta;
    }

    public void setFutBidForDelta( double futBidForDelta ) {
        this.preFutBidForDelta = getFutBidForDelta( );
        this.futBidForDelta = futBidForDelta;
    }

    public MyTimeSeries getDeltaSerie() {
        return deltaSerie;
    }

    public double getFutAskForDelta() {
        return futAskForDelta;
    }

    public void setFutAskForDelta( double futAskForDelta ) {
        this.preFutAskForDelta = getFutAskForDelta( );
        this.futAskForDelta = futAskForDelta;
    }

    public double getPreFutBidForDelta() {
        return preFutBidForDelta;
    }

    public double getPreFutAskForDelta() {
        return preFutAskForDelta;
    }

    public double getFutForDelta() {
        return futForDelta;
    }

    public void setFutForDelta( double futForDelta ) {
        this.futForDelta = futForDelta;
    }
}