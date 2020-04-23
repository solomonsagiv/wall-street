package setting.clientSetting;

import gui.MyGuiComps;
import locals.L;
import locals.Themes;
import serverObjects.BASE_CLIENT_OBJECT;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TickerPanel extends MyGuiComps.MyPanel {

    // Variables
    BASE_CLIENT_OBJECT client;

    MyGuiComps.MyLabel openLbl;
    MyGuiComps.MyLabel baseLbl;
    MyGuiComps.MyTextField openField;
    MyGuiComps.MyTextField baseField;
    MyGuiComps.MyLabel indexBidAskCounterLbl;
    MyGuiComps.MyTextField indexBidAskCounterField;
    MyGuiComps.MyButton startBtn;
    MyGuiComps.MyButton stopBtn;

    // Constructor
    public TickerPanel(BASE_CLIENT_OBJECT client) {
        this.client = client;
        initialize();
        initListeners();
    }

    private void initListeners() {

        // Open
        openField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    double d = L.dbl( openField.getText() );
                    client.setOpen( d );
                } catch ( Exception e ) {
                    JOptionPane.showMessageDialog( null, e.getMessage() );
                    e.printStackTrace();
                }
            }
        });

        // Base
        baseField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    double d = L.dbl( baseField.getText() );
                    client.setBase( d );
                } catch ( Exception e ) {
                    JOptionPane.showMessageDialog( null, e.getMessage() );
                    e.printStackTrace();
                }
            }
        });

        // Index bid ask counter
        indexBidAskCounterField.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent actionEvent ) {
                try {
                    int counter = L.INT( indexBidAskCounterField.getText() );
                    client.setIndexBidAskCounter( counter );
                } catch ( Exception e ) {
                    JOptionPane.showMessageDialog( null, e.getMessage() );
                    e.printStackTrace();
                }
            }
        } );

        // Start
        startBtn.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent actionEvent ) {
                client.startAll();
            }
        } );

        // Start
        stopBtn.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent actionEvent ) {
                client.closeAll();
            }
        } );
    }

    private void initialize() {

        // This
        setSize(200, 125);

        TitledBorder titledBorder = BorderFactory.createTitledBorder("Ticker");
        titledBorder.setTitleColor(Themes.BLUE_DARK);
        setBorder(titledBorder);

        // ----- Open ----- //
        // Lbl
        openLbl = new MyGuiComps.MyLabel("Open");
        openLbl.setXY(10, 10);
        openLbl.setHorizontalAlignment(JLabel.LEFT);
        openLbl.setFont(openLbl.getFont().deriveFont(9f));
        openLbl.setLabelFor(openField);
        add(openLbl);

        // Field
        openField = new MyGuiComps.MyTextField();
        openField.setXY(10, 30);
        openField.setSize(50, 20);
        openField.setFontSize(9);
        add(openField);

        // ----- Base ----- //
        // Lbl
        baseLbl = new MyGuiComps.MyLabel("Base");
        baseLbl.setXY(65, 10);
        baseLbl.setHorizontalAlignment(JLabel.LEFT);
        baseLbl.setFont(baseLbl.getFont().deriveFont(9f));
        baseLbl.setLabelFor(baseField);
        add(baseLbl);

        // Field
        baseField = new MyGuiComps.MyTextField();
        baseField.setXY(65, 30);
        baseField.setFontSize(9);
        baseField.setSize(50, 20);
        add(baseField);

        // ----- Index bid ask counter ----- //
        // Lbl
        indexBidAskCounterLbl = new MyGuiComps.MyLabel("Ind B/A counter");
        indexBidAskCounterLbl.setXY(120, 10);
        indexBidAskCounterLbl.setWidth( 100 );
        indexBidAskCounterLbl.setHorizontalAlignment(JLabel.LEFT);
        indexBidAskCounterLbl.setFont(indexBidAskCounterLbl.getFont().deriveFont(9f));
        indexBidAskCounterLbl.setLabelFor(indexBidAskCounterField);
        add(indexBidAskCounterLbl);

        // Field
        indexBidAskCounterField = new MyGuiComps.MyTextField();
        indexBidAskCounterField.setXY(120, 30);
        indexBidAskCounterField.setFontSize(9);
        indexBidAskCounterField.setSize(70, 20);
        add(indexBidAskCounterField);

        // ----- Start ----- //
        startBtn = new MyGuiComps.MyButton( "Start" );
        startBtn.setXY( 10, 60 );
        startBtn.setWidth(70);
        startBtn.setFont( startBtn.getFont( ).deriveFont( 9f ) );
        startBtn.setBackground(Themes.BLUE);
        startBtn.setForeground(Themes.GREY_VERY_LIGHT);
        add(startBtn);

        // ----- Stop ----- //
        stopBtn = new MyGuiComps.MyButton( "Stop" );
        stopBtn.setXY( 85, 60 );
        stopBtn.setWidth(70);
        stopBtn.setFont( stopBtn.getFont( ).deriveFont( 9f ) );
        stopBtn.setBackground(Themes.BLUE);
        stopBtn.setForeground(Themes.GREY_VERY_LIGHT);
        add(stopBtn);

    }


}
