package basketFinder.window;

import gui.MyGuiComps;
import serverObjects.BASE_CLIENT_OBJECT;

import java.awt.*;

public class BasketWindow extends MyGuiComps.MyFrame {

    // Variables


    // Constructor
    public BasketWindow(String title, BASE_CLIENT_OBJECT client) throws HeadlessException {
        super(title, client);
    }

    @Override
    public void onClose() {

    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initialize() {
        // This
        setBounds(200, 200, 300, 300);




    }
}
