package gui;

import serverApiObjects.stockObjects.*;
import setting.Setting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class StocksWindow {
	static JFrame frame;
	StocksWindow stocksWindow;
	FuturePanelLine facebookPanel;
	FuturePanelLine applePanel;
	FuturePanelLine amazonPanel;
	FuturePanelLine vxxPanel;
	FuturePanelLine microsoftPanel;
	FuturePanelLine googlePanel;
	FuturePanelLine vixPanel;
	FuturePanelLine ciscoPanel;
	FuturePanelLine netflixPanel;

	FacebookCLIENTObject facebookClientObject;
	AppleCLIENTObject appleClientObject;
	AmazonCLIENTObject amazonClientObject;
	NetflixCLIENTObject netflixClientObject;

	ArrayList < STOCK_CLIENT_OBJECT > clients;

	private JLabel lblNewLabel;
	private JLabel lblIndex;
	private JLabel lblOpen;
	private JLabel lblLast;
	private JLabel lblLow;
	private JLabel lblHigh;
	private JPanel panel_1;
	private JButton btnExport;
	private JPanel panel_3;
	private JPanel panel_5;

	/**
	 * Create the application.
	 */
	public StocksWindow() {

		// Create the clients
		facebookClientObject = FacebookCLIENTObject.getInstance();
		appleClientObject = AppleCLIENTObject.getInstance();
		amazonClientObject = AmazonCLIENTObject.getInstance();
		netflixClientObject = NetflixCLIENTObject.getInstance();

		clients = new ArrayList <>();
		clients.add( amazonClientObject );
		clients.add( appleClientObject );
		clients.add( facebookClientObject );
		clients.add( netflixClientObject );

		// Create the session factory for data base

		// Create the gui
		initialize();

	}

	/**
	 * Launch the application.
	 */
	public static void main( String[] args ) {
		EventQueue.invokeLater( new Runnable() {
			public void run() {
				try {
					StocksWindow window = new StocksWindow();
					window.getFrame().setVisible( true );

				} catch ( Exception e ) {
					e.printStackTrace();
				}
			}
		} );
	}

	private static void addPopup( Component component , final JPopupMenu popup ) {
		component.addMouseListener( new MouseAdapter() {
			public void mousePressed( MouseEvent e ) {
				if ( e.isPopupTrigger() ) {
					showMenu( e );
				}
			}

			public void mouseReleased( MouseEvent e ) {
				if ( e.isPopupTrigger() ) {
					showMenu( e );
				}
			}

			private void showMenu( MouseEvent e ) {
				popup.show( e.getComponent() , e.getX() , e.getY() );
			}
		} );
	}

	public static void popupException( String text , Exception e ) {
		JOptionPane.showMessageDialog( frame , text + "\n" + e.getMessage() + "\n" + e.getCause() );
	}

	public static void popup( String text ) {
		JOptionPane.showMessageDialog( frame , text );
	}

	public void start() {
		EventQueue.invokeLater( new Runnable() {
			public void run() {
				try {
					stocksWindow = new StocksWindow();
					frame.setVisible( true );
				} catch ( Exception e ) {
					e.printStackTrace();
				}

				// Start backGourndRunners
				facebookClientObject.getBackRunner().startRunner();
				appleClientObject.getBackRunner().startRunner();
				amazonClientObject.getBackRunner().startRunner();
				netflixClientObject.getBackRunner().startRunner();
			}
		} );
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame();
		frame.getContentPane().setBackground( new Color( 0 , 0 , 0 ) );
		frame.setBackground( new Color( 0 , 0 , 0 ) );
		frame.addWindowListener( new WindowAdapter() {
			@Override
			public void windowClosed( WindowEvent arg0 ) {

				for ( STOCK_CLIENT_OBJECT client : clients ) {
					try {
						client.closeAll();
					} catch ( Exception e ) {
						e.printStackTrace();
						continue;
					}
				}
			}
		} );
		frame.setTitle( "Stocks" );
		frame.setBounds( 100 , 100 , 593 , 487 );
		frame.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		frame.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked( MouseEvent event ) {
				if ( event.getModifiers() == MouseEvent.BUTTON3_MASK ) {
					// Main menu
					JPopupMenu menu = new JPopupMenu();

					JMenuItem setting = new JMenuItem( "Setting" );
					setting.addActionListener( new ActionListener() {
						@Override
						public void actionPerformed( ActionEvent e ) {
							Setting setting = new Setting( "stock" );
							setting.frmSetting.setVisible( true );
						}
					} );

					JMenuItem shlomi = new JMenuItem( "Shlomi" );
					shlomi.addActionListener( new ActionListener() {
						@Override
						public void actionPerformed( ActionEvent e ) {
							AlertWindow.Show( "Window deleted" , "" , "please build shlomi window" );
						}
					} );

					menu.add( setting );
					menu.add( shlomi );

					// Show the menu
					menu.show( event.getComponent() , event.getX() , event.getY() );
				}
			}
		} );
		frame.getContentPane().setLayout( null );

		int height = 105;
		int y = 25;

		// Facebook
		facebookPanel = new FuturePanelLine( facebookClientObject );
		facebookPanel.setBounds( 0 , 25 , 732 , height );
		frame.getContentPane().add( facebookPanel );
		facebookClientObject.setPanelLine( facebookPanel );

		// Apple
		applePanel = new FuturePanelLine( appleClientObject );
		applePanel.setBounds( 0 , 131 , 732 , height );
		frame.getContentPane().add( applePanel );
		appleClientObject.setPanelLine( applePanel );

		// Amazon
		amazonPanel = new FuturePanelLine( amazonClientObject );
		amazonPanel.setBounds( 0 , 237 , 732 , height );
		frame.getContentPane().add( amazonPanel );
		amazonClientObject.setPanelLine( amazonPanel );

		// Netflix
		netflixPanel = new FuturePanelLine( netflixClientObject );
		netflixPanel.setBounds( 0 , 343 , 732 , height );
		frame.getContentPane().add( netflixPanel );
		netflixClientObject.setPanelLine( netflixPanel );

		panel_1 = new JPanel();
		panel_1.setBackground( new Color( 0 , 51 , 102 ) );
		panel_1.setBounds( 0 , 900 , 732 , 32 );
		frame.getContentPane().add( panel_1 );
		panel_1.setLayout( null );

		JButton start = new JButton( "Start" );
		start.setBounds( 0 , 0 , 100 , 30 );
		panel_1.add( start );
		start.setFont( new Font( "Arial" , Font.BOLD , 15 ) );

		btnExport = new JButton( "Export" );
		btnExport.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				exportAll();
			}

			private void exportAll() {
				for ( STOCK_CLIENT_OBJECT client : clients ) {
					client.fullExport();
				}
			}
		} );
		btnExport.setFont( new Font( "Arial" , Font.BOLD , 15 ) );
		btnExport.setBounds( 150 , 0 , 100 , 30 );
		panel_1.add( btnExport );

		JPanel panel_2 = new JPanel();
		panel_2.setBackground( new Color( 44 , 61 , 73 ) );
		panel_2.setBounds( 0 , 0 , 100 , 24 );
		frame.getContentPane().add( panel_2 );
		panel_2.setLayout( null );

		lblNewLabel = new JLabel( "Fut" );
		lblNewLabel.setBounds( 3 , 0 , 46 , 24 );
		panel_2.add( lblNewLabel );
		lblNewLabel.setHorizontalAlignment( SwingConstants.CENTER );
		lblNewLabel.setForeground( new Color( 255 , 204 , 0 ) );
		lblNewLabel.setFont( new Font( "Arial" , Font.BOLD , 14 ) );

		lblIndex = new JLabel( "Ind" );
		lblIndex.setBounds( 49 , 0 , 46 , 24 );
		panel_2.add( lblIndex );
		lblIndex.setHorizontalAlignment( SwingConstants.CENTER );
		lblIndex.setForeground( new Color( 255 , 204 , 0 ) );
		lblIndex.setFont( new Font( "Arial" , Font.BOLD , 14 ) );

		panel_3 = new JPanel();
		panel_3.setBackground( new Color( 44 , 61 , 73 ) );
		panel_3.setBounds( 100 , 0 , 306 , 24 );
		frame.getContentPane().add( panel_3 );
		panel_3.setLayout( null );

		lblOpen = new JLabel( "Open" );
		lblOpen.setBounds( 19 , 0 , 46 , 24 );
		panel_3.add( lblOpen );
		lblOpen.setHorizontalAlignment( SwingConstants.CENTER );
		lblOpen.setForeground( new Color( 255 , 204 , 0 ) );
		lblOpen.setFont( new Font( "Arial" , Font.BOLD , 14 ) );

		lblLast = new JLabel( "Last" );
		lblLast.setBounds( 89 , 0 , 46 , 24 );
		panel_3.add( lblLast );
		lblLast.setHorizontalAlignment( SwingConstants.CENTER );
		lblLast.setForeground( new Color( 255 , 204 , 0 ) );
		lblLast.setFont( new Font( "Arial" , Font.BOLD , 14 ) );

		lblLow = new JLabel( "Low" );
		lblLow.setBounds( 165 , 0 , 46 , 24 );
		panel_3.add( lblLow );
		lblLow.setHorizontalAlignment( SwingConstants.CENTER );
		lblLow.setForeground( new Color( 255 , 204 , 0 ) );
		lblLow.setFont( new Font( "Arial" , Font.BOLD , 14 ) );

		lblHigh = new JLabel( "High" );
		lblHigh.setBounds( 240 , 0 , 46 , 24 );
		panel_3.add( lblHigh );
		lblHigh.setHorizontalAlignment( SwingConstants.CENTER );
		lblHigh.setForeground( new Color( 255 , 204 , 0 ) );
		lblHigh.setFont( new Font( "Arial" , Font.BOLD , 14 ) );

		panel_5 = new JPanel();
		panel_5.setBackground( new Color( 44 , 61 , 73 ) );
		panel_5.setBounds( 407 , 0 , 170 , 24 );
		frame.getContentPane().add( panel_5 );
		panel_5.setLayout( null );
		start.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed( ActionEvent e ) {

				for ( STOCK_CLIENT_OBJECT client : clients ) {
					double last = client.getIndex();
					client.setRacesMargin( last * .0002 );
					client.startAll();
				}
			}
		} );
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame( JFrame frame ) {
		StocksWindow.frame = frame;
	}
}
