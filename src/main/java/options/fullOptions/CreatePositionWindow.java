package options.fullOptions;

import gui.MyGuiComps;
import locals.L;
import locals.Themes;
import options.Option;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreatePositionWindow extends MyGuiComps.MyFrame {

    public static void main( String[] args ) {
        CreatePositionWindow createPositionWindow = new CreatePositionWindow( "c1520 buy" );
        createPositionWindow.setVisible( true );
    }

    PositionCalculator positionCalculator;
    Option option;
    int BUY_SELL;
    private MyGuiComps.MyButton submitBtn;
    private MyGuiComps.MyTextField quantotyField;
    private MyGuiComps.MyTextField priceField;
    private MyGuiComps.MyLabel quantityLabel;
    private MyGuiComps.MyLabel priceLabel;
    private MyGuiComps.MyLabel orderLbl;
    private String orderText;
    private MyGuiComps.MyPanel panel;

    public CreatePositionWindow( String orderText, Option option, PositionCalculator positionCalculator, int BUY_SELL, JFrame frame ) {
        super( orderText );

        setBounds( 600, 200, 200, 203 );

        this.orderText = orderText;
        this.positionCalculator = positionCalculator;
        this.option = option;
        this.BUY_SELL = BUY_SELL;

        int x = (frame.getX() + (frame.getWidth() / 2)) - (getWidth() / 2);
        int y  = (frame.getY() + (frame.getHeight() / 2)) - (getHeight() / 2);

        setBounds( x, y, 200, 203 );

        init( );
        initListeners( );
    }

    public CreatePositionWindow( String optionName ) {
        super( optionName );

        this.orderText = optionName;

        init( );
        initListeners( );

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

        // Main panel
        panel = new MyGuiComps.MyPanel();
        panel.setLayout( null );
        panel.setBounds( 0, 0, 200, getHeight() );
        getContentPane().add(panel);

        // Order lbl
        orderLbl = new MyGuiComps.MyLabel( orderText.toUpperCase() );
        if ( BUY_SELL == PositionCalculator.OptionPosition.BUY ) {
            orderLbl.setForeground( Themes.GREEN );
        } else {
            orderLbl.setForeground( Themes.RED );
        }
        orderLbl.setBounds( 0, 10, panel.getWidth(), 25 );
        orderLbl.setFont( orderLbl.getFont().deriveFont( Font.BOLD ) );
        panel.add(orderLbl);

        // Quantity
        quantityLabel = new MyGuiComps.MyLabel( "Quantity" );
        quantityLabel.setBounds( 15, 40, 70, 25 );
        panel.add( quantityLabel );

        quantotyField = new MyGuiComps.MyTextField( );
        quantotyField.setBounds( 95, 40, 70, 25 );
        panel.add( quantotyField );

        // Price
        priceLabel = new MyGuiComps.MyLabel( "Price" );
        priceLabel.setBounds( 15, 70, 70, 25 );
        panel.add( priceLabel );

        priceField = new MyGuiComps.MyTextField( );
        priceField.setBounds( 95, 70, 70, 25 );
        panel.add( priceField );

        // Submit
        submitBtn = new MyGuiComps.MyButton( "Submit" );
        submitBtn.setBounds( 15, 120, 150, 25 );
        panel.add( submitBtn );

    }

    @Override
    public void onClose() { }

}
