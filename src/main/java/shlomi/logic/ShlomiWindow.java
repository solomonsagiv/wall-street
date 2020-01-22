package shlomi.logic;

import api.tws.TWSConnection;
import locals.Themes;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.DaxCLIENTObject;
import serverObjects.indexObjects.SpxCLIENTObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ShlomiWindow {

	public static ShlomiWindow window;
	public static JTextField opAvgField;
	ShlomiTrader traderOne;
	ShlomiTrader traderTwo;
	ShlomiBackRunner shlomiBackRunner;
	JButton startButton;
	JLabel runStatusLabel;
	JTextArea log;
	BASE_CLIENT_OBJECT client;
	TWSConnection connection;
	private JFrame frame;
	private JTextField quantityField;

	/**
	 * Create the application.
	 *
	 * @throws InterruptedException
	 */
	public ShlomiWindow() throws InterruptedException {
		initialize();
	}

	/**
	 * Launch the application.
	 */
	public static void main( String[] args ) {
		EventQueue.invokeLater( new Runnable() {
			public void run() {
				try {
					window = new ShlomiWindow();
					window.frame.setVisible( true );
				} catch ( Exception e ) {
					e.printStackTrace();
				}
			}
		} );
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setFont( new Font( "Dubai Medium" , Font.PLAIN , 15 ) );
		frame.setBounds( 100 , 100 , 602 , 538 );
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.getContentPane().setLayout( null );

		JLabel lblNewLabel = new JLabel( "Shlomi" );
		lblNewLabel.setFont( new Font( "Dubai Medium" , Font.PLAIN , 18 ) );
		lblNewLabel.setBounds( 10 , 11 , 50 , 14 );
		frame.getContentPane().add( lblNewLabel );

		JLabel lblTradingData = new JLabel( "Trading data" );
		lblTradingData.setFont( new Font( "Dubai Medium" , Font.PLAIN , 15 ) );
		lblTradingData.setBounds( 10 , 40 , 81 , 14 );
		frame.getContentPane().add( lblTradingData );

		JLabel lblNewLabel_1 = new JLabel( "Quantity:" );
		lblNewLabel_1.setFont( new Font( "Dubai Medium" , Font.PLAIN , 14 ) );
		lblNewLabel_1.setBounds( 14 , 80 , 58 , 14 );
		frame.getContentPane().add( lblNewLabel_1 );

		quantityField = new JTextField();
		quantityField.setFont( new Font( "Dubai Medium" , Font.PLAIN , 15 ) );
		quantityField.setHorizontalAlignment( SwingConstants.CENTER );
		quantityField.setBounds( 82 , 77 , 46 , 20 );
		frame.getContentPane().add( quantityField );
		quantityField.setColumns( 10 );

		JLabel lblPlay = new JLabel( "Play" );
		lblPlay.setFont( new Font( "Dubai Medium" , Font.PLAIN , 15 ) );
		lblPlay.setBounds( 10 , 273 , 27 , 14 );
		frame.getContentPane().add( lblPlay );

		startButton = new JButton( "Connect" );
		startButton.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent arg0 ) {

				try {
					connection = TWSConnection.getInstance();
					connection.start();

					runStatusLabel.setForeground( Themes.GREEN );
					runStatusLabel.setText( "Connected" );
				} catch ( Exception e ) {
					JOptionPane.showMessageDialog( frame , e.getStackTrace() );
				}

			}
		} );
		startButton.setFont( new Font( "Dubai Medium" , Font.PLAIN , 15 ) );
		startButton.setBounds( 10 , 294 , 89 , 23 );
		frame.getContentPane().add( startButton );

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds( 222 , 23 , 354 , 235 );
		frame.getContentPane().add( scrollPane );

		log = new JTextArea();
		scrollPane.setViewportView( log );

		JLabel lblRun = new JLabel( "Run:" );
		lblRun.setFont( new Font( "Dubai Medium" , Font.PLAIN , 14 ) );
		lblRun.setBounds( 117 , 297 , 27 , 14 );
		frame.getContentPane().add( lblRun );

		runStatusLabel = new JLabel( "Not connected" );
		runStatusLabel.setForeground( Color.RED );
		runStatusLabel.setFont( new Font( "Dubai Medium" , Font.PLAIN , 14 ) );
		runStatusLabel.setBounds( 151 , 298 , 89 , 14 );
		frame.getContentPane().add( runStatusLabel );

		JLabel lblOpavg = new JLabel( "opAvg:" );
		lblOpavg.setFont( new Font( "Dubai Medium" , Font.PLAIN , 14 ) );
		lblOpavg.setBounds( 14 , 108 , 58 , 14 );
		frame.getContentPane().add( lblOpavg );

		opAvgField = new JTextField();
		opAvgField.setHorizontalAlignment( SwingConstants.CENTER );
		opAvgField.setFont( new Font( "Dubai Medium" , Font.PLAIN , 15 ) );
		opAvgField.setColumns( 10 );
		opAvgField.setBounds( 82 , 105 , 46 , 20 );
		frame.getContentPane().add( opAvgField );

		JButton btnTraderOne = new JButton( "Trader one" );
		btnTraderOne.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {

				try {
					traderOne = new ShlomiTrader( client , new AlgoritemOne( client , connection.getTwsClient() ) );

					TraderWindow traderWindow = new TraderWindow( traderOne );
					traderWindow.frame.setVisible( true );
				} catch ( Exception e2 ) {
					JOptionPane.showMessageDialog( frame , e2.getStackTrace() );
					e2.printStackTrace();
				}

			}
		} );
		btnTraderOne.setFont( new Font( "Dubai Medium" , Font.PLAIN , 15 ) );
		btnTraderOne.setBounds( 10 , 385 , 103 , 23 );
		frame.getContentPane().add( btnTraderOne );

		JButton btnTraderTwo = new JButton( "Trader two" );
		btnTraderTwo.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {

				try {
					traderTwo = new ShlomiTrader( client , new AlgoritemTwo( client , connection.getTwsClient() ) );

					TraderWindow traderWindow = new TraderWindow( traderTwo );
					traderWindow.frame.setVisible( true );
				} catch ( Exception e2 ) {
					JOptionPane.showMessageDialog( frame , e2.getStackTrace() );
					e2.printStackTrace();
				}
			}
		} );
		btnTraderTwo.setFont( new Font( "Dubai Medium" , Font.PLAIN , 15 ) );
		btnTraderTwo.setBounds( 10 , 419 , 103 , 23 );
		frame.getContentPane().add( btnTraderTwo );

		JRadioButton rdbtnNewRadioButton = new JRadioButton( "Spx" );
		rdbtnNewRadioButton.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked( MouseEvent arg0 ) {
				client = SpxCLIENTObject.getInstance();
			}
		} );
		rdbtnNewRadioButton.setFont( new Font( "Dubai Medium" , Font.PLAIN , 15 ) );
		rdbtnNewRadioButton.setBounds( 14 , 163 , 109 , 23 );
		frame.getContentPane().add( rdbtnNewRadioButton );

		JRadioButton rdbtnDax = new JRadioButton( "Dax" );
		rdbtnDax.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked( MouseEvent e ) {
				client = DaxCLIENTObject.getInstance();
			}
		} );
		rdbtnDax.setFont( new Font( "Dubai Medium" , Font.PLAIN , 15 ) );
		rdbtnDax.setBounds( 14 , 189 , 109 , 23 );
		frame.getContentPane().add( rdbtnDax );

		ButtonGroup group = new ButtonGroup();
		group.add( rdbtnNewRadioButton );
		group.add( rdbtnDax );


	}

}
