package setting;

import gui.MyGuiComps;
import locals.Themes;
import serverObjects.BASE_CLIENT_OBJECT;
import javax.swing.border.TitledBorder;

public class DataBasePanel extends MyGuiComps.MyPanel {

    // Variables
    BASE_CLIENT_OBJECT client;

    MyGuiComps.MyButton stopBtn;
    MyGuiComps.MyButton startBtn;
    MyGuiComps.MyButton resetBtn;
    MyGuiComps.MyButton updateBtn;
    MyGuiComps.MyButton loadBtn;
    MyGuiComps.MyButton sumBtn;

    // Constructor
    public DataBasePanel( BASE_CLIENT_OBJECT client ) {
        this.client = client;
        initialize();
        initListeners();
    }

    private void initListeners() {
    }

    private void initialize() {

        // This
        setSize( 150, 150 );
        TitledBorder titledBorder = new TitledBorder( "Data base" );
        titledBorder.setTitleColor( Themes.BLUE_DARK );
        setBorder( titledBorder );

    }
}
