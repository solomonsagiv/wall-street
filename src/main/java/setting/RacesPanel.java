package setting;

import gui.MyGuiComps;
import locals.L;
import locals.Themes;
import serverObjects.BASE_CLIENT_OBJECT;

import javax.swing.border.TitledBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RacesPanel extends MyGuiComps.MyPanel {

    // Variables
    BASE_CLIENT_OBJECT client;
    MyGuiComps.MyTextField conUpField;
    MyGuiComps.MyTextField conDownField;
    MyGuiComps.MyTextField indUpField;
    MyGuiComps.MyTextField indDownField;
    MyGuiComps.MyButton submitBtn;

    // Constructor
    public RacesPanel( BASE_CLIENT_OBJECT client ) {
        this.client = client;
        initialize( );
        initListeners( );
    }

    private void initListeners() {

        // Submit btn
        submitBtn.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent actionEvent ) {

                if ( !conUpField.getText( ).isEmpty( ) ) {
                    try {
                        int i = L.INT( conUpField.getText( ) );
                        client.setConUp( i );
                    } catch ( Exception e ) {}
                }

                if ( !conDownField.getText( ).isEmpty( ) ) {
                    try {
                        int i = L.INT( conDownField.getText( ) );
                        client.setConDown( i );
                    } catch ( Exception e ) {}
                }

                if ( !indUpField.getText( ).isEmpty( ) ) {
                    try {
                        int i = L.INT( indUpField.getText( ) );
                        client.setIndexUp( i );
                    } catch ( Exception e ){}
                }

                if ( !indDownField.getText( ).isEmpty( ) ) {
                    try {
                        int i = L.INT( indDownField.getText( ) );
                        client.setIndexDown( i );
                    } catch ( Exception e ){}
                }
            }
        } );

    }

    private void initialize() {

        // This
        setSize( 130, 125 );

        TitledBorder titledBorder = new TitledBorder( "Races" );
        titledBorder.setTitleColor( Themes.BLUE_DARK );
        setBorder( titledBorder );

        // Con up
        conUpField = new MyGuiComps.MyTextField( );
        conUpField.setEnabled( true );
        conUpField.setXY( 10, 20 );
        conUpField.setWidth( 50 );
        conUpField.setFontSize(9);
        add( conUpField );

        // Con down
        conDownField = new MyGuiComps.MyTextField( );
        conDownField.setEnabled( true );
        conDownField.setXY( 10, 50 );
        conDownField.setFontSize(9);
        conDownField.setWidth( 50 );
        add( conDownField );

        // Ind up
        indUpField = new MyGuiComps.MyTextField( );
        indUpField.setEnabled( true );
        indUpField.setXY( 65, 20 );
        indUpField.setFontSize(9);
        indUpField.setWidth( 50 );
        add( indUpField );

        // Ind down
        indDownField = new MyGuiComps.MyTextField( );
        indDownField.setEnabled( true );
        indDownField.setXY( 65, 50 );
        indDownField.setFontSize(9);
        indDownField.setWidth( 50 );
        add( indDownField );

        // Submit
        submitBtn = new MyGuiComps.MyButton( "Submit" );
        submitBtn.setXY( 10, 90 );
        submitBtn.setWidth( 70 );
        submitBtn.setFont( submitBtn.getFont().deriveFont( 9f ) );
        submitBtn.setBackground( Themes.BLUE );
        submitBtn.setForeground( Themes.GREY_VERY_LIGHT );
        add( submitBtn );


    }


}
