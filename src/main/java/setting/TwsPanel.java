package setting;

import api.tws.TwsHandler;
import com.ib.client.Contract;
import gui.MyGuiComps;
import locals.Themes;
import options.Options;
import options.OptionsEnum;
import serverObjects.BASE_CLIENT_OBJECT;
import setting.optionsPanel.OptionsPanel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class TwsPanel extends MyGuiComps.MyPanel {

    // Variables
    BASE_CLIENT_OBJECT client;

    MyGuiComps.MyLabel dateLbl;
    MyGuiComps.MyTextField dateField;
    MyGuiComps.MyButton requestDataBtn;

    // Constructor
    public TwsPanel(BASE_CLIENT_OBJECT client ) {
        this.client = client;
        initialize();
        initListeners();
    }

    private void initListeners() {

        // Date
        dateField.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent actionEvent ) {
                // Interest
                if ( !dateField.getText().isEmpty() ) {
                    try {

                        String s = dateField.getText();

                        OptionsPanel.options.getCopyTwsContract().lastTradeDateOrContractMonth( s );

                        String year = s.substring( 0,4 );
                        String month = s.substring( 4,6 );
                        String day = s.substring( 6,8 );
                        String date = year + "-" + month + "-" + day;

                        OptionsPanel.options.setExpDate( LocalDate.parse( date ) );
                        System.out.println(OptionsPanel.options.getType() );
                        System.out.println(OptionsPanel.options.getCopyTwsContract() );
                        System.out.println(date );
                    } catch ( Exception e ) {
                        e.printStackTrace();
                    }
                }
            }
        } );


        // Request btn
        requestDataBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    client.requestApi();
                } catch (Exception e) {
                    e.printStackTrace();
                    // todo
                }
            }
        });

    }

    private void initialize() {

        // This
        setSize( 150, 125 );
        TitledBorder titledBorder = new TitledBorder( "Tws" );
        titledBorder.setTitleColor( Themes.BLUE_DARK );
        setBorder( titledBorder );

        // ----- Date ----- //
        // Lbl
        dateLbl = new MyGuiComps.MyLabel( "Date" );
        dateLbl.setXY( 10, 10 );
        dateLbl.setHorizontalAlignment( JLabel.LEFT );
        dateLbl.setFont( dateLbl.getFont( ).deriveFont( 9f ) );
        dateLbl.setLabelFor( dateLbl );
        add( dateLbl );

        // Field
        dateField = new MyGuiComps.MyTextField( );
        dateField.setXY( 10, 30 );
        dateField.setFontSize(9);
        dateField.setSize( 70, 20 );
        add( dateField );

        // Request button
        requestDataBtn = new MyGuiComps.MyButton("Request");
        requestDataBtn.setXY(10, 70);
        add(requestDataBtn);

    }


}
