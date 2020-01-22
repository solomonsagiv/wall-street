package gui;

import api.Manifest;
import locals.Themes;
import serverObjects.indexObjects.INDEX_CLIENT_OBJECT;
import serverObjects.indexObjects.NdxCLIENTObject;
import serverObjects.indexObjects.SpxCLIENTObject;
import setting.Setting;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class WallStreetWindow {
	public static WallStreetWindow window;

	// Get window instance
	public static JTextField ask;
	public static JTextField bid;
	static ArrayList < INDEX_CLIENT_OBJECT > clients;
	/**
	 * @wbp.parser.entryPoint
	 */
	public int i = 1;
	public int s;
	public JFrame frame;
	public JMenuItem export;
	// JTables
	public JTable futuresTable;
	public JTable spxOptionsTable;
	NdxCLIENTObject ndxClientObject;
	SpxCLIENTObject spxClientObject;
	private JMenu mnStart;
	private JMenuItem mntmSetting;
	private JPanel panel;

	public WallStreetWindow () {

		ndxClientObject = NdxCLIENTObject.getInstance ( );
		spxClientObject = SpxCLIENTObject.getInstance ( );

		clients = new ArrayList <> ( );
		clients.add ( ndxClientObject );
		clients.add ( spxClientObject );
	}

	public static void main ( String[] args ) {
		WallStreetWindow window = new WallStreetWindow ( );
		window.start ( );
	}

	// Get date
	public static String getDate () {
		String pattern = "dd-MM-yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat ( pattern );
		String date = simpleDateFormat.format ( new Date ( ) );
		return date;
	}

	// Show on screen
	public static void showOnScreen ( int screen, JFrame frame ) {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment ( );
		GraphicsDevice[] gd = ge.getScreenDevices ( );
		if ( screen > -1 && screen < gd.length ) {
			frame.setLocation ( gd[ screen ].getDefaultConfiguration ( ).getBounds ( ).x + frame.getX ( ), frame.getY ( ) );
		} else if ( gd.length > 0 ) {
			frame.setLocation ( gd[ 0 ].getDefaultConfiguration ( ).getBounds ( ).x + frame.getX ( ), frame.getY ( ) );
		} else {
			throw new RuntimeException ( "No Screens Found" );
		}
	}

	// Popup
	public static void popup ( String message, Exception e ) {
		// Style the popup
		UIManager.put ( "OptionPane.background", new ColorUIResource ( 189, 208, 239 ) );
		UIManager.put ( "Panel.background", new ColorUIResource ( 189, 208, 239 ) );

		// Create the popup
		JOptionPane.showMessageDialog ( null, message + "\n" + e.getMessage ( ) );
	}

	public static void export () {
		try {
			// Export
			for ( INDEX_CLIENT_OBJECT client : clients ) {
				client.fullExport ( );
			}
		} catch ( Exception e2 ) {
			popup ( "Export failed: ", e2 );
			e2.printStackTrace ( );
		}
	}

	/**
	 * Launch the application.
	 */
	public void start () {
		EventQueue.invokeLater ( new Runnable ( ) {
			public void run () {
				try {
					window = new WallStreetWindow ( );
					window.initialize ( );
					window.frame.setVisible ( true );

					// Load on startup
					window.load_on_startup ( window );
				} catch ( Exception e ) {
					e.printStackTrace ( );
				}
			}
		} );
	}

	// Load on startup
	private void load_on_startup ( WallStreetWindow window ) {

		Thread thread = new Thread ( () -> {
			ndxClientObject.getBackRunner ( ).startRunner ( );
			spxClientObject.getBackRunner ( ).startRunner ( );
		} );
		thread.start ( );
	}

	/**
	 * Initialize the contents of the frame.
	 *
	 * @throws InterruptedException
	 */
	private void initialize () throws InterruptedException {
		frame = new JFrame ( );
		frame.setForeground ( new Color ( 0, 128, 0 ) );
		frame.setTitle ( "Index" );
		frame.getContentPane ( ).setFont ( new Font ( "Arial", Font.PLAIN, 12 ) );
		frame.getContentPane ( ).setBackground ( new Color ( 255, 255, 255 ) );
		frame.setBounds ( 100, 100, 436, 197 );
		frame.addWindowListener ( new java.awt.event.WindowAdapter ( ) {
			public void windowClosing ( WindowEvent e ) {

				String[] options = new String[] { "Yes", "No" };
				int res = JOptionPane.showOptionDialog ( null, "Are you sure you want to close the program ?", "Title",
						JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[ 0 ] );
				if ( res == 0 ) {

					for ( INDEX_CLIENT_OBJECT client : clients ) {
						client.closeAll ( );
					}

					( ( JFrame ) e.getSource ( ) ).setDefaultCloseOperation ( JFrame.DISPOSE_ON_CLOSE );
				} else if ( res == 1 ) {
					( ( JFrame ) e.getSource ( ) ).setDefaultCloseOperation ( JFrame.DO_NOTHING_ON_CLOSE );
				}
			}
		} );

		frame.addMouseListener ( new MouseAdapter ( ) {
			@Override
			public void mouseClicked ( MouseEvent event ) {
				if ( event.getModifiers ( ) == MouseEvent.BUTTON3_MASK ) {
					// Main menu
					JPopupMenu menu = new JPopupMenu ( );

					JMenuItem setting = new JMenuItem ( "Setting" );
					setting.addActionListener ( new ActionListener ( ) {
						@Override
						public void actionPerformed ( ActionEvent e ) {
							Setting setting = new Setting ( "index" );
							setting.frmSetting.setVisible ( true );
						}
					} );

					JMenuItem shlomi = new JMenuItem ( "Shlomi" );
					shlomi.addActionListener ( new ActionListener ( ) {
						@Override
						public void actionPerformed ( ActionEvent e ) {
							AlertWindow.Show ( "Window deleted", "", "please build shlomi window" );
						}
					} );

					menu.add ( setting );
					menu.add ( shlomi );
					// Show the menu
					menu.show ( event.getComponent ( ), event.getX ( ), event.getY ( ) );
				}
			}
		} );

		// Show on screen
		showOnScreen ( Manifest.SCREEN, frame );

		frame.getContentPane ( ).setLayout ( null );

		Color backGround = Themes.GREY_LIGHT;
		Color forgGround = Themes.BLUE;

		Font font = Themes.VEDANA_12.deriveFont ( Font.BOLD );

		JPanel racesAndRolHeaderPanel = new JPanel ( );
		racesAndRolHeaderPanel.setBounds ( 308, 0, 111, 25 );
		racesAndRolHeaderPanel.setLayout ( null );
		racesAndRolHeaderPanel.setBackground ( backGround );
		frame.getContentPane ( ).add ( racesAndRolHeaderPanel );

		JLabel conAndrollLabel = new JLabel ( "Roll" );
		conAndrollLabel.setBounds ( 0, 0, racesAndRolHeaderPanel.getWidth ( ), racesAndRolHeaderPanel.getHeight ( ) );
		conAndrollLabel.setHorizontalAlignment ( JLabel.CENTER );
		conAndrollLabel.setForeground ( forgGround );
		racesAndRolHeaderPanel.add ( conAndrollLabel );

		JPanel headersPanel = new JPanel ( );
		headersPanel.setBounds ( 0, 0, 307, 25 );
		headersPanel.setLayout ( null );
		headersPanel.setBackground ( backGround );
		frame.getContentPane ( ).add ( headersPanel );

		JLabel label_1 = new JLabel ( "Open" );
		label_1.setForeground ( forgGround );
		label_1.setFont ( font );
		label_1.setBounds ( 21, 6, 40, 14 );
		headersPanel.add ( label_1 );

		JLabel label_2 = new JLabel ( "Last" );
		label_2.setForeground ( forgGround );
		label_2.setFont ( font );
		label_2.setBounds ( 95, 6, 40, 14 );
		headersPanel.add ( label_2 );

		JLabel label_3 = new JLabel ( "Low" );
		label_3.setForeground ( forgGround );
		label_3.setFont ( font );
		label_3.setBounds ( 166, 6, 34, 14 );
		headersPanel.add ( label_3 );

		JLabel label_4 = new JLabel ( "High" );
		label_4.setForeground ( forgGround );
		label_4.setFont ( font );
		label_4.setBounds ( 233, 6, 34, 15 );
		headersPanel.add ( label_4 );

		JPanel panel_12 = new JPanel ( );
		panel_12.setBounds ( 0, 0, 115, 25 );
		panel_12.setLayout ( null );
		panel_12.setBackground ( backGround );
		frame.getContentPane ( ).add ( panel_12 );

		ndxClientObject.getPanel ( ).setBounds ( 0, 166, 500, 140 );
		spxClientObject.getPanel ( ).setBounds ( 0, 26, 500, 140 );
		frame.getContentPane ( ).add ( ndxClientObject.getPanel ( ) );
		frame.getContentPane ( ).add ( spxClientObject.getPanel ( ) );

		panel = new JPanel ( );
		panel.setBounds ( 0, 500, 463, 27 );
		frame.getContentPane ( ).add ( panel );
		panel.setLayout ( null );

		JMenuBar menuBar = new JMenuBar ( );
		menuBar.setForeground ( new Color ( 255, 255, 255 ) );
		menuBar.setBounds ( 0, 0, 463, 27 );
		panel.add ( menuBar );
		menuBar.setBackground ( new Color ( 0, 0, 51 ) );

		JMenu mnNewMenu = new JMenu ( " File    " );
		mnNewMenu.setForeground ( Color.WHITE );
		mnNewMenu.setBackground ( new Color ( 0, 0, 51 ) );
		mnNewMenu.setFont ( font );
		menuBar.add ( mnNewMenu );

		export = new JMenuItem ( "Export" );
		export.addActionListener ( new ActionListener ( ) {
			@SuppressWarnings( "unchecked" )
			public void actionPerformed ( ActionEvent e ) {
				export ( );
			}
		} );
		export.setBackground ( new Color ( 0, 0, 51 ) );
		export.setIcon ( new ImageIcon ( "C:\\Users\\yosefg\\Desktop\\Counter\\icons\\exel" ) );
		export.setForeground ( Color.WHITE );
		export.setFont ( font );
		mnNewMenu.add ( export );

		JMenuItem restart = new JMenuItem ( "Restart" );
		restart.setIcon ( new ImageIcon ( "C:\\Users\\yosefg\\Desktop\\Counter\\icons\\restart.jpg" ) );
		restart.addActionListener ( new ActionListener ( ) {
			public void actionPerformed ( ActionEvent e ) {
			}
		} );
		restart.setBackground ( new Color ( 0, 0, 51 ) );
		restart.setForeground ( Color.WHITE );
		restart.setFont ( font );
		mnNewMenu.add ( restart );

		mntmSetting = new JMenuItem ( "Setting" );
		mntmSetting.addActionListener ( new ActionListener ( ) {
			public void actionPerformed ( ActionEvent e ) {
				Setting window = new Setting ( "index" );
				window.frmSetting.setVisible ( true );
			}
		} );
		mntmSetting.setBackground ( new Color ( 0, 0, 51 ) );
		mntmSetting.setForeground ( Color.WHITE );
		mntmSetting.setFont ( font );
		mnNewMenu.add ( mntmSetting );

		mnStart = new JMenu ( "Start" );
		mnStart.setBackground ( new Color ( 0, 0, 51 ) );
		mnStart.setForeground ( Color.WHITE );
		mnStart.setFont ( font );
		menuBar.add ( mnStart );

		JPanel panel_1 = new JPanel ( );
		panel_1.setLayout ( null );
		panel_1.setBackground ( new Color ( 176, 196, 222 ) );
		panel_1.setBounds ( 760, 500, 158, 105 );
		frame.getContentPane ( ).add ( panel_1 );

		TableMaker tableMaker = new TableMaker ( new MenuMaker ( null ).futureCounterTableMenu ( ) );
		futuresTable = tableMaker.futuresTable ( new Object[ 3 ][ 2 ], new String[] { "", "" }, 29 );

		JScrollPane scrollPane = new JScrollPane ( futuresTable );
		scrollPane.setBounds ( 10, 5, 140, 91 );
		panel_1.add ( scrollPane );

		tableMaker = new TableMaker ( new MenuMaker ( spxClientObject ).spxOptionsTableMenu ( ) );
		spxOptionsTable = tableMaker.myTable ( spxClientObject, new Object[ 7 ][ 5 ], new String[] { "", "", "", "", "" },
				21 );

		ask = new JTextField ( );
		ask.setFont ( font );
		ask.setBounds ( 551, 273, 85, 20 );
		frame.getContentPane ( ).add ( ask );
		ask.setColumns ( 10 );

		bid = new JTextField ( );
		bid.setFont ( font );
		bid.setColumns ( 10 );
		bid.setBounds ( 459, 273, 85, 20 );
		frame.getContentPane ( ).add ( bid );
	}

	// Kill thread and dispose the window
	public void kill () {
		try {
			// Close db connection

		} catch ( Exception e1 ) {
			popup ( "", e1 );
			e1.printStackTrace ( );
		}
	}

	// Get trade by name
	public void killThread ( String threadName ) {
		for ( Thread t : Thread.getAllStackTraces ( ).keySet ( ) ) {
			if ( t.getName ( ).equals ( threadName ) ) {
				t.interrupt ( );
			}
		}
	}
}
