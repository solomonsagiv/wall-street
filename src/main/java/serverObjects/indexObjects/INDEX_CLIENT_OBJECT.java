package serverObjects.indexObjects;

import options.IndexOptions;
import options.OptionsEnum;
import options.OptionsHandler;
import serverObjects.BASE_CLIENT_OBJECT;
import tws.TwsContractsEnum;

public abstract class INDEX_CLIENT_OBJECT extends BASE_CLIENT_OBJECT {

    private double future = 0;

    public INDEX_CLIENT_OBJECT() {
        super();
    }

    public void setFuture(double future) {
        if (this.future == 0) {
            this.future = future;
            getOptionsHandler().initOptions(future);
        }
    }

    public double getFuture() {
        return future;
    }

    @Override
    public void initOptionsHandler() throws NullPointerException {

        IndexOptions optionsQuarter = new IndexOptions(getBaseId() + 3000, this, OptionsEnum.QUARTER, TwsContractsEnum.OPT_QUARTER);
        IndexOptions optionsQuarterFar = new IndexOptions(getBaseId() + 4000, this, OptionsEnum.QUARTER_FAR, TwsContractsEnum.OPT_QUARTER_FAR);

        OptionsHandler optionsHandler = new OptionsHandler(this);
        optionsHandler.addOptions(optionsQuarter);
        optionsHandler.addOptions(optionsQuarterFar);
        optionsHandler.setMainOptions(optionsQuarter);

        setOptionsHandler(optionsHandler);
    }

}
