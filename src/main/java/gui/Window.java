package gui;

import locals.Themes;
import java.awt.*;

public class Window extends MyGuiComps.MyFrame {

    // Main
    public static void main( String[] args ) {
        new Window( "Test big" );
    }

    // Variables


    // Constructor
    public Window( String title ) throws HeadlessException {
        super( title );
    }

    @Override
    public void onClose() {
        // TODO on close
    }

    @Override
    public void initListeners() {
        // TODO on close
    }

    @Override
    public void initialize() {

    }


}
