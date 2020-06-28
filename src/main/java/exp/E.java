package exp;

import delta.DeltaCalc;
import options.OptionsDDeCells;
import options.optionsCalcs.IOptionsCalcs;
import serverObjects.BASE_CLIENT_OBJECT;
import tws.TwsContractsEnum;

public class E extends Exp {

    protected double delta = 0;
    protected int volumeFutForDelta = 0;
    protected double futForDelta = 0;
    protected double futBidForDelta = 0;
    protected double futAskForDelta = 0;
    protected double preFutBidForDelta = 0;
    private double preFutAskForDelta = 0;

    public E( BASE_CLIENT_OBJECT client, ExpEnum expEnum, TwsContractsEnum contractsEnum, IOptionsCalcs iOptionsCalcs ) {
        super( client, expEnum, contractsEnum, iOptionsCalcs );
    }

    public E( BASE_CLIENT_OBJECT client, ExpEnum expEnum, TwsContractsEnum twsContractsEnum, IOptionsCalcs iOptionsCalcs, OptionsDDeCells optionsDDeCells ) {
        super( client, expEnum, twsContractsEnum, iOptionsCalcs, optionsDDeCells );
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

    public double getFutBidForDelta() {
        return futBidForDelta;
    }

    public void setFutBidForDelta( double futBidForDelta ) {
        this.preFutBidForDelta = getFutBidForDelta( );
        this.futBidForDelta = futBidForDelta;
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

    public void setVolumeFutForDelta( int volumeFutForDelta ) {

        int quantity = volumeFutForDelta - this.volumeFutForDelta;

        DeltaCalc.calc( quantity, getCalcFut( ), getPreFutBidForDelta( ), getPreFutAskForDelta( ) );

        this.volumeFutForDelta = volumeFutForDelta;
    }

    public void setFutForDelta( double futForDelta ) {
        this.futForDelta = futForDelta;
    }

    public double getFutForDelta() {
        return futForDelta;
    }
}