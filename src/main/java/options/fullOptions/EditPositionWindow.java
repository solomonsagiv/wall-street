package options.fullOptions;

import gui.MyGuiComps;
import locals.L;
import locals.Themes;

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

    public EditPositionWindow( String title, PositionCalculator.OptionPosition position ) throws HeadlessException {
        super( title );
        this.position = position;
        orderText = position.getOption().getName();

        initialize();
        initListeners();
    }

    private void initialize() {

        setBounds( 500, 500, 270, 250 );

        // Main panel
        panel = new MyGuiComps.MyPanel();
        panel.setBounds( 0, 0, getWidth(), getHeight() );
        getContentPane().add(panel);

        // Order lbl
        orderLbl = new MyGuiComps.MyLabel( orderText.toUpperCase() );
        orderLbl.setBounds( 0, 10, panel.getWidth(), 25 );
        orderLbl.setFont( orderLbl.getFont().deriveFont( Font.BOLD ) );
        panel.add(orderLbl);

        // Quantity
        quantityLabel = new MyGuiComps.MyLabel( "Quantity" );
        quantityLabel.setBounds( 50, 40, 70, 25 );
        panel.add( quantityLabel );

        quantotyField = new MyGuiComps.MyTextField( 20 );
        quantotyField.setBounds( 130, 40, 70, 25 );
        quantotyField.colorForge( position.getPos());
        panel.add( quantotyField );

        // Price
        priceLabel = new MyGuiComps.MyLabel( "Price" );
        priceLabel.setBounds( 50, 70, 70, 25 );
        panel.add( priceLabel );

        priceField = new MyGuiComps.MyTextField( 20 );
        priceField.setBounds( 130, 70, 70, 25 );
        priceField.setText( L.str( position.getPrice() ) );
        panel.add( priceField );

        // Submit
        submitBtn = new MyGuiComps.MyButton( "Submit" );
        submitBtn.setBounds( 50, 120, 150, 25 );
        panel.add( submitBtn );

    }

    // ---------- Listeners ---------- //
    private void initListeners() {

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
    public void onClose() {}



}
