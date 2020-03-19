package gui;

import options.Options;
import options.OptionsEnum;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Spx;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class DetailsWindow {

    public static DetailsWindow window;
    public JFrame frame;
    JLabel stockNameField;
    JTextArea textArea;
    JTextArea optionsArea;
    JComboBox optionsCombo;
    BASE_CLIENT_OBJECT client;
    Runner runner;
    String[] optionsTypes;
    Options options;

    /**
     * Create the application.
     */
    public DetailsWindow( BASE_CLIENT_OBJECT client ) {
        this.client = client;
        this.options = client.getOptionsHandler().getMainOptions();

        onStartUp();

        initialize();

        // Start Runner thread
        startRunner();
    }
    /**
     * Launch the application.
     */
    public static void main( String[] args ) {
        EventQueue.invokeLater( new Runnable( ) {
            public void run() {
                try {
                    window = new DetailsWindow( Spx.getInstance( ) );
                    window.frame.setVisible( true );
                } catch ( Exception e ) {
                    e.printStackTrace( );
                }
            }
        } );
    }

    private void onStartUp() {
        optionsTypes = new String[ client.getOptionsHandler( ).getOptionsList( ).size( ) ];
        int i = 0;
        for ( Options options : client.getOptionsHandler( ).getOptionsList( ) ) {
            optionsTypes[ i ] = options.getType( ).toString();
            i++;
        }
    }

    public void startRunner() {
        if ( runner == null ) {
            runner = new Runner( );
        }
        runner.start( );
    }

    public void closeRunner() {
        if ( runner != null ) {
            runner.close( );
        }
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame( );
        frame.addWindowListener( new WindowAdapter( ) {
            @Override
            public void windowClosed( WindowEvent arg0 ) {
                runner.close( );
            }
        } );
        frame.setBounds( 100, 100, 900, 454 );
        frame.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        frame.getContentPane( ).setLayout( null );

        optionsCombo = new JComboBox( optionsTypes );
        optionsCombo.setBounds( 750, 50, 120, 30 );
        optionsCombo.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent actionEvent ) {
                switch ( optionsCombo.getSelectedItem( ).toString( ).toLowerCase( ) ) {
                    case "day":
                        options = client.getOptionsHandler( ).getOptions( OptionsEnum.WEEK );
                        break;
                    case "month":
                        options = client.getOptionsHandler( ).getOptions( OptionsEnum.MONTH );
                        break;
                    case "quarter":
                        options = client.getOptionsHandler( ).getOptions( OptionsEnum.QUARTER );
                        break;
                    case "main":
                        options = client.getOptionsHandler( ).getMainOptions( );
                        break;
                    default:
                        break;
                }
            }
        } );

        JPanel panel = new JPanel( );
        panel.setBackground( new Color( 255, 255, 255 ) );
        panel.setBounds( 0, 0, 900, 415 );
        frame.getContentPane( ).add( panel );
        panel.setLayout( null );
        panel.add( optionsCombo );

        JPanel panel_1 = new JPanel( );
        panel_1.setBorder( null );
        panel_1.setBackground( SystemColor.inactiveCaption );
        panel_1.setBounds( 0, 53, 300, 351 );
        panel.add( panel_1 );
        panel_1.setLayout( null );

        textArea = new JTextArea( );
        textArea.setBackground( Color.WHITE );
        textArea.setFont( new Font( "Dubai Medium", Font.PLAIN, 14 ) );
        textArea.setBorder( new EmptyBorder( 7, 7, 7, 7 ) );
        textArea.setBounds( 0, 0, 6, 15 );

        JScrollPane scrollPane = new JScrollPane( textArea );
        scrollPane.setBorder( null );
        scrollPane.setBounds( 0, 0, panel_1.getWidth( ), panel_1.getHeight( ) );
        panel_1.add( scrollPane );

        stockNameField = new JLabel( client.getName( ).toUpperCase( ) );
        stockNameField.setFont( new Font( "Dubai Medium", Font.PLAIN, 16 ) );
        stockNameField.setBounds( 10, 11, 128, 20 );
        panel.add( stockNameField );

        JSeparator separator = new JSeparator( );
        separator.setForeground( Color.BLACK );
        separator.setBackground( Color.BLACK );
        separator.setBounds( 10, 42, 230, 11 );
        panel.add( separator );

        JPanel panel_2 = new JPanel( );
        panel_2.setLayout( null );
        panel_2.setBorder( null );
        panel_2.setBackground( SystemColor.inactiveCaption );
        panel_2.setBounds( 300, 53, 406, 351 );
        panel.add( panel_2 );

        optionsArea = new JTextArea( );
        optionsArea.setFont( new Font( "Dubai Medium", Font.PLAIN, 14 ) );
        optionsArea.setBorder( new EmptyBorder( 7, 7, 7, 7 ) );
        optionsArea.setBackground( Color.WHITE );
        optionsArea.setBounds( 0, 0, 283, 351 );

        JScrollPane optionsScrollPane = new JScrollPane( optionsArea );
        optionsScrollPane.setBorder( null );
        optionsScrollPane.setBounds( 0, 0, 406, 351 );
        panel_2.add( optionsScrollPane );

        JSeparator separator_1 = new JSeparator( );
        separator_1.setForeground( Color.BLACK );
        separator_1.setBackground( Color.BLACK );
        separator_1.setBounds( 300, 42, 430, 11 );
        panel.add( separator_1 );

        JLabel lblOptions = new JLabel( "Options" );
        lblOptions.setFont( new Font( "Dubai Medium", Font.PLAIN, 16 ) );
        lblOptions.setBounds( 300, 11, 128, 20 );
        panel.add( lblOptions );

    }

    class Runner extends Thread {

        boolean run = true;

        @Override
        public void run() {

            while ( run ) {
                try {

                    // Write data
                    writeData( );

                    // Sleep
                    sleep( 1000 );
                } catch ( InterruptedException e ) {
                    close( );
                }
            }
        }

        private void close() {
            run = false;
        }

        private void writeData() {

            String text = convertListToString( );

            textArea.setText( text );
            optionsArea.setText( options.toStringVertical( ) );
        }


        private ArrayList< String > getToStringList() {
            ArrayList< String > list = new ArrayList<>( );
            list.add( "Started: " + client.isStarted( ) );
            list.add( "Contract: " + options.getContract() );
            list.add( "Index: " + client.getIndex( ) );
            list.add( "Base: " + client.getBase( ) );
            list.add( "\n" );
            list.add( "DB: " + client.isDbRunning( ) );
            list.add( "MySql: " + client.getMyServiceHandler().isExist( client.getMySqlService() ) );
            list.add( "\n" );
            list.add( "Exp date: " + options.getExpDate( ) );
            list.add( "Days: " + options.getDays( ) );
            list.add( "Start strike: " + client.getStartStrike( ) );
            list.add( "End strike: " + client.getEndStrike( ) );
            list.add( "Got options: " + options.isGotData( ) );
            list.add( "Interest: " + options.getInterest( ) );
            list.add( "Devidend: " + options.getDevidend( ) );
            list.add( "Calc Devidend: " + options.getCalcDevidend( ) );
            list.add( "Borrow: " + options.getCalcBorrow( ) );
            list.add("");
            list.add("Tws Contract");
            list.add(options.getTwsContract().toString());
            list.add("");
            list.add("All details");
            list.add(client.toStringPretty());
            return list;
        }

        private String convertListToString() {

            StringBuilder text = new StringBuilder( );

            for ( String string : getToStringList( ) ) {

                if ( string.equals( "\n" ) ) {
                    text.append( string );
                } else {
                    text.append( string ).append( "\n" );
                }

            }

            return text.toString( );

        }
    }
}


