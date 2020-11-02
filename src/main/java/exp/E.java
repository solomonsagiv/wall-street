package exp;

import charts.myChart.MyTimeSeries;
import delta.DeltaCalc;
import myJson.MyJson;
import options.JsonStrings;
import options.OptionsDDeCells;
import options.optionsCalcs.IOptionsCalcs;
import serverObjects.BASE_CLIENT_OBJECT;
import tws.TwsContractsEnum;

import java.net.UnknownHostException;

public class E extends Exp {

    protected int delta = 0;
    protected int preDelta = 0;
    protected int volumeFutForDelta = 0;
    protected double futForDelta = 0;
    protected double futBidForDelta = 0;
    protected double futAskForDelta = 0;
    protected double preFutBidForDelta = 0;
    protected double preFutAskForDelta = 0;
    private MyTimeSeries deltaSerie;
    private MyTimeSeries preDeltaSerie;
    private MyTimeSeries deltaScaledSerie;

    public E( BASE_CLIENT_OBJECT client, String expEnum, TwsContractsEnum contractsEnum, IOptionsCalcs iOptionsCalcs ) {
        super( client, expEnum, contractsEnum, iOptionsCalcs );
        initSeries( );
    }

    public E( BASE_CLIENT_OBJECT client, String expEnum, TwsContractsEnum twsContractsEnum, IOptionsCalcs iOptionsCalcs, OptionsDDeCells optionsDDeCells ) {
        super( client, expEnum, twsContractsEnum, iOptionsCalcs, optionsDDeCells );
        initSeries( );
    }

    public int getDelta() {
        return delta;
    }

    public void setDelta( int delta ) {
        this.delta = delta;
    }

    public int getVolumeFutForDelta() {
        return volumeFutForDelta;
    }

    public void setVolumeFutForDelta( int volumeFutForDelta ) {
        int quantity = 0;
        if ( this.volumeFutForDelta != 0 ) {
            quantity = volumeFutForDelta - this.volumeFutForDelta;
            calcDelta( quantity );
        }

        this.volumeFutForDelta = volumeFutForDelta;
    }

    public void calcDelta( int quantity ) {
        new Thread( () -> {
//            this.delta += DeltaCalc.calc( quantity, getFutForDelta( ), getFutBidForDelta( ), getFutAskForDelta( ) );
            this.preDelta += DeltaCalc.calc( quantity, getFutForDelta( ), getPreFutBidForDelta( ), getPreFutAskForDelta( ) );
        } ).start( );
    }

    public void initSeries() {
        super.initSeries( );

        deltaSerie = new MyTimeSeries( "Delta", client ) {
            @Override
            public double getData() throws UnknownHostException {
                return delta;
            }
        };

        preDeltaSerie = new MyTimeSeries( "Pre delta", client ) {
            @Override
            public double getData() throws UnknownHostException {
                return preDelta;
            }
        };

        deltaScaledSerie = new MyTimeSeries( "Delta scaled", client, true ) {
            @Override
            public double getData() throws UnknownHostException {
                return delta;
            }
        };
    }

    @Override
    public MyJson getAsJson() {
        MyJson json = super.getAsJson( );
        json.put( JsonStrings.delta, getDelta( ) );
        return json;
    }

    @Override
    public void loadFromJson( MyJson json ) {
        setDelta( json.getInt( JsonStrings.delta ) );
        super.loadFromJson( json );
    }

    public double getFutBidForDelta() {
        return futBidForDelta;
    }

    public void setBidForDelta( double futBidForDelta ) {

        // Set pre bid
        this.preFutBidForDelta = this.futBidForDelta;

        // Set current
        this.futBidForDelta = futBidForDelta;
    }

    public double getPreFutAskForDelta() {
        return preFutAskForDelta;
    }

    public double getPreFutBidForDelta() {
        return preFutBidForDelta;
    }

    public MyTimeSeries getDeltaSerie() {
        return deltaSerie;
    }

    public MyTimeSeries getDeltaScaledSerie() {
        return deltaScaledSerie;
    }

    public MyTimeSeries getPreDeltaSerie() {
        return preDeltaSerie;
    }

    public double getFutAskForDelta() {
        return futAskForDelta;
    }

    public void setAskForDelta( double futAskForDelta ) {

        // Set pre ask
        this.preFutAskForDelta = this.futAskForDelta;

        // Set current
        this.futAskForDelta = futAskForDelta;
    }

    public double getFutForDelta() {
        return futForDelta;
    }

    public void setFutForDelta( double futForDelta ) {
        if ( futForDelta > 1 ) {
            this.futForDelta = futForDelta;
        }
    }
}