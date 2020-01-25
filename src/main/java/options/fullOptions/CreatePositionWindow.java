package options.fullOptions;

import gui.MyGuiComps;
import locals.L;
import options.Option;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreatePositionWindow extends MyGuiComps.MyFrame {

    PositionCalculator positionCalculator;
    Option option;
    int BUY_SELL;
    private JButton submitBtn;
    private JTextField quantotyField;
    private JTextField priceField;
    private JLabel quantityLabel;
    private JLabel priceLabel;

    public CreatePositionWindow( String optionName, Option option, PositionCalculator positionCalculator, int BUY_SELL ) {
        super( optionName );
        init( );
        initListeners( );
        setBounds( 600, 200, 270, 180 );

        this.positionCalculator = positionCalculator;
        this.option = option;
        this.BUY_SELL = BUY_SELL;
    }

    public CreatePositionWindow( String optionName ) {
        super( optionName );
        init( );
        initListeners( );
        setBounds( 600, 200, 270, 180 );
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
                int pos = quantity * BUY_SELL;

                positionCalculator.addPosition( option, pos, price );
                dispose( );
            }
        } );
    }

    // ---------- Gui ---------- //
    private void init() {

        // Quantity
        quantityLabel = new MyGuiComps.MyLabel( "Quantity" );
        quantityLabel.setBounds( 50, 20, 70, 25 );
        getContentPane( ).add( quantityLabel );

        quantotyField = new MyGuiComps.MyTextField( 20 );
        quantotyField.setBounds( 130, 20, 70, 25 );
        getContentPane( ).add( quantotyField );

        // Price
        priceLabel = new MyGuiComps.MyLabel( "Price" );
        priceLabel.setBounds( 50, 50, 70, 25 );
        getContentPane( ).add( priceLabel );

        priceField = new MyGuiComps.MyTextField( 20 );
        priceField.setBounds( 130, 50, 70, 25 );
        getContentPane( ).add( priceField );

        // Submit
        submitBtn = new MyGuiComps.MyButton( "Submit" );
        submitBtn.setBounds( 50, 100, 150, 25 );
        getContentPane( ).add( submitBtn );

    }

}
