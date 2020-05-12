package options.fullOptions;

import gui.MyGuiComps;
import locals.L;
import locals.Themes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditPositionWindow extends MyGuiComps.MyFrame {

    PositionCalculator.OptionPosition position;
    private MyGuiComps.MyButton submitBtn;
    private MyGuiComps.MyTextField quantotyField;
    private MyGuiComps.MyTextField priceField;
    private MyGuiComps.MyLabel quantityLabel;
    private MyGuiComps.MyLabel priceLabel;
    private MyGuiComps.MyLabel orderLbl;
    private String orderText;
    private MyGuiComps.MyPanel panel;

    public EditPositionWindow( String title, PositionCalculator.OptionPosition position, JFrame frame ) throws HeadlessException {
        super( title );

        setBounds( frame.getX(), frame.getY(), 200, 203 );

        this.position = position;
        orderText = position.getOption().getName();

        int x = (frame.getX() + (frame.getWidth() / 2)) - (getWidth() / 2);
        int y  = (frame.getY() + (frame.getHeight() / 2)) - (getHeight() / 2);

        setBounds( x, y, 200, 203 );

    }

    @Override
    public void initialize() {

        // Main panel
        panel = new MyGuiComps.MyPanel();
        panel.setLayout( null );
        panel.setBounds( 0, 0, getWidth(), getHeight() );
        getContentPane().add(panel);

        // Order lbl
        orderLbl = new MyGuiComps.MyLabel( orderText.toUpperCase() );
        orderLbl.setBounds( 0, 10, panel.getWidth(), 25 );
        orderLbl.setFont( orderLbl.getFont().deriveFont( Font.BOLD ) );
        panel.add(orderLbl);

        // Quantity
        quantityLabel = new MyGuiComps.MyLabel( "Quantity" );
        quantityLabel.setBounds( 15, 40, 70, 25 );
        panel.add( quantityLabel );

        quantotyField = new MyGuiComps.MyTextField( );
        quantotyField.setBounds( 95, 40, 70, 25 );
        quantotyField.colorForge( position.getPos());
        panel.add( quantotyField );

        // Price
        priceLabel = new MyGuiComps.MyLabel( "Price" );
        priceLabel.setBounds( 15, 70, 70, 25 );
        panel.add( priceLabel );

        priceField = new MyGuiComps.MyTextField( );
        priceField.setBounds( 95, 70, 70, 25 );
        priceField.setText( L.str( position.getPrice() ) );
        panel.add( priceField );

        // Submit
        submitBtn = new MyGuiComps.MyButton( "Submit" );
        submitBtn.setBounds( 15, 120, 150, 25 );
        panel.add( submitBtn );

    }

    // ---------- Listeners ---------- //
    @Override
    public void initListeners() {

        // Quantity
        quantotyField.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent actionEvent ) {

                if ( !quantotyField.getText( ).isEmpty( ) && !priceField.getText( ).isEmpty( ) ) {
                    submitBtn.doClick( );
                }
            }
        } );

        // Price
        priceField.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent actionEvent ) {
                if ( !quantotyField.getText( ).isEmpty( ) && !priceField.getText( ).isEmpty( ) ) {
                    submitBtn.doClick( );
                }
            }
        } );

        // Submit
        submitBtn.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent actionEvent ) {

                int quantity = L.INT( quantotyField.getText( ) );
                double price = L.dbl( priceField.getText( ) );
                
                position.setPos( quantity );
                position.setPrice( price );
                dispose( );
            }
        } );
    }

    @Override
    public void initOnClose() {}



}
