package gui.index.newP;

import gui.MyGuiComps;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.INDEX_CLIENT_OBJECT;
import serverObjects.indexObjects.Spx;

import java.awt.*;

public class NewIndexWindow extends MyGuiComps.MyFrame {

    // Variables
    NewPanel indexPanel;

    // Constructor
    public NewIndexWindow(String title, INDEX_CLIENT_OBJECT client) throws HeadlessException {
        super(title, client);
        load_data(client);
    }

    public static void main(String[] args) {
        Spx dax = Spx.getInstance();
        new NewIndexWindow("Dax", dax);
    }

    private static void load_data(BASE_CLIENT_OBJECT client) {
        new Thread(()->{
//            client.getTimeSeriesHandler().get(TimeSeriesFactory.BASKETS_CDF).load();
//            client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_2_CDF).load();
//            client.getTimeSeriesHandler().get(TimeSeriesFactory.DF_9_CDF).load();
        }).start();
    }

    @Override
    public void onClose() {
        super.onClose();
        indexPanel.close();
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initialize() {

        // This
        setPreferredSize(new Dimension(750, 222));
        setLayout(null);

        // Index panel
        indexPanel = new NewPanel((INDEX_CLIENT_OBJECT) client);
        indexPanel.setBounds(0, 0, 750, 500);
        getContentPane().add(indexPanel);

    }
}