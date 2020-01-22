package options.fullOptions;

import gui.MyGuiComps;
import locals.L;
import options.Option;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreatePositionWindow extends MyGuiComps.MyFrame {

	private JButton submitBtn;
	private JTextField quantotyField;
	private JTextField priceField;
	private JLabel quantityLabel;
	private JLabel priceLabel;
	PositionCalculator positionCalculator;
	Option option;
	int BUY_SELL;

	public CreatePositionWindow( String optionName , Option option , PositionCalculator positionCalculator , int BUY_SELL ) {
		super( optionName );
		init();
		initListeners();
		setBounds( 600 , 200 , 270 , 180 );

		this.positionCalculator = positionCalculator;
		this.option = option;
		this.BUY_SELL = BUY_SELL;
	}

	public CreatePositionWindow( String optionName ) {
		super( optionName );
		init();
		initListeners();
		setBounds( 600 , 200 , 270 , 180 );
	}

	// ---------- Listeners ---------- //
	private void initListeners() {

		// Quantity
		quantotyField.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed( ActionEvent actionEvent ) {

				if ( !quantotyField.getText().isEmpty() && !priceField.getText().isEmpty() ) {
					submitBtn.doClick();
				}
			}
		} );

		// Price
		priceField.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed( ActionEvent actionEvent ) {
				if ( !quantotyField.getText().isEmpty() && !priceField.getText().isEmpty() ) {
					submitBtn.doClick();
				}
			}
		} );

		// Submit
		submitBtn.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed( ActionEvent actionEvent ) {

				int quantity = L.INT( quantotyField.getText() );
				double price = L.dbl( priceField.getText() );
				int pos = quantity * BUY_SELL;

				positionCalculator.addPosition( option , pos , price );
				dispose();

			}
		} );
	}

	// ---------- Gui ---------- //
	private void init() {

		// Quantity
		quantityLabel = new MyGuiComps.MyLabel( "Quantity" );
		quantityLabel.setBounds( 50 , 20 , 70 , 25 );
		getContentPane().add( quantityLabel );

		quantotyField = new MyGuiComps.MyTextField( 20 );
		quantotyField.setBounds( 130 , 20 , 70 , 25 );
		getContentPane().add( quantotyField );

		// Price
		priceLabel = new MyGuiComps.MyLabel( "Price" );
		priceLabel.setBounds( 50 , 50 , 70 , 25 );
		getContentPane().add( priceLabel );

		priceField = new MyGuiComps.MyTextField( 20 );
		priceField.setBounds( 130 , 50 , 70 , 25 );
		getContentPane().add( priceField );

		// Submit
		submitBtn = new MyGuiComps.MyButton( "Submit" );
		submitBtn.setBounds( 50 , 100 , 150 , 25 );
		getContentPane().add( submitBtn );

	}

	{
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
		$$$setupUI$$$();
	}

	/**
	 * Method generated by IntelliJ IDEA GUI Designer
	 * >>> IMPORTANT!! <<<
	 * DO NOT edit this method OR call it in your code!
	 *
	 * @noinspection ALL
	 */
	private void $$$setupUI$$$() {
		final JPanel panel1 = new JPanel();
		panel1.setLayout( new com.intellij.uiDesigner.core.GridLayoutManager( 3 , 6 , new Insets( 0 , 0 , 0 , 0 ) , -1 , -1 ) );
		submitBtn = new JButton();
		submitBtn.setText( "Button" );
		panel1.add( submitBtn , new com.intellij.uiDesigner.core.GridConstraints( 2 , 2 , 1 , 3 , com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER , com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL , com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW , com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED , null , null , null , 0 , false ) );
		quantityLabel = new JLabel();
		quantityLabel.setText( "Quantity" );
		panel1.add( quantityLabel , new com.intellij.uiDesigner.core.GridConstraints( 0 , 2 , 1 , 1 , com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER , com.intellij.uiDesigner.core.GridConstraints.FILL_NONE , com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED , com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED , null , null , null , 0 , false ) );
		quantotyField = new JTextField();
		quantotyField.setText( "" );
		panel1.add( quantotyField , new com.intellij.uiDesigner.core.GridConstraints( 0 , 4 , 1 , 1 , com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST , com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL , com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW , com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED , null , new Dimension( 130 , 30 ) , null , 0 , false ) );
		priceLabel = new JLabel();
		priceLabel.setText( "Price" );
		panel1.add( priceLabel , new com.intellij.uiDesigner.core.GridConstraints( 1 , 2 , 1 , 1 , com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER , com.intellij.uiDesigner.core.GridConstraints.FILL_NONE , com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED , com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED , null , null , null , 0 , false ) );
		priceField = new JTextField();
		panel1.add( priceField , new com.intellij.uiDesigner.core.GridConstraints( 1 , 4 , 1 , 1 , com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST , com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL , com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW , com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED , null , new Dimension( 130 , 30 ) , null , 0 , false ) );
		quantityLabel.setLabelFor( quantotyField );
		priceLabel.setLabelFor( priceField );
	}
}
