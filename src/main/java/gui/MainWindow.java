package gui;

import api.Connection;
import api.Manifest;
import locals.Themes;
import logger.MyLogger;
import serverObjects.indexObjects.NdxCLIENTObject;
import serverObjects.indexObjects.SpxCLIENTObject;
import setting.Setting;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalTime;
import java.util.Set;

public class MainWindow {

    public JFrame frame;
    JLabel connectionStatusLbl;
    Thread runner;
    SpxCLIENTObject spx;
    NdxCLIENTObject ndx;
    JTextField spxQuarterField;
    JTextField ndxQuarterField;
    JTextField spxQuarterFarField;
    JTextField ndxQuarterFarField;
    private JButton wallstreetWindowBtn;
    private JTextField spxField;
    private JTextField ndxField;
    private JTextField twsPortField;
    private JTextField spxDayOptionExpField;
    private JTextField ndxDayOptionExpField;

    /**
     * Create the application.
     */
    public MainWindow() {

        ndx = NdxCLIENTObject.getInstance();
        spx = SpxCLIENTObject.getInstance();

        initialize();

        runner = new Thread(() -> {
            while (true) {
                try {
                    Set<Thread> threads = Thread.getAllStackTraces().keySet();

                    if (LocalTime.now().isAfter(LocalTime.of(23, 59, 0))) {
                        System.exit(0);
                    }

                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });

        runner.start();
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {

                    MainWindow window = new MainWindow();
                    window.frame.setVisible(true);

                    MyLogger.getInstance();

                    loadOnStartUp();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static void loadOnStartUp() {

        SpxCLIENTObject spx = SpxCLIENTObject.getInstance();
        spx.getTablesHandler().getStatusHandler().getHandler().loadData();
        spx.getTablesHandler().getArrayHandler().getHandler().loadData();

        NdxCLIENTObject ndx = NdxCLIENTObject.getInstance();
        ndx.getTablesHandler().getStatusHandler().getHandler().loadData();
        ndx.getTablesHandler().getArrayHandler().getHandler().loadData();

    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setResizable(false);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent arg0) {
                runner.interrupt();
                frame.dispose();
            }
        });
        frame.setBounds(100, 100, 588, 384);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 0, 0));
        panel.setForeground(UIManager.getColor("Button.foreground"));
        panel.setBounds(0, 0, 588, 360);
        frame.getContentPane().add(panel);
        panel.setLayout(null);

        JPanel panel_1 = new JPanel();
        panel_1.setBorder(null);
        panel_1.setBounds(0, 0, 584, 112);
        panel.add(panel_1);
        panel_1.setBackground(Themes.BINANCE_GREY);
        panel_1.setLayout(null);

        JButton btnDisconnect = new JButton("Disconnect");
        btnDisconnect.setBorder(new TitledBorder(null, "", TitledBorder.LEFT, TitledBorder.TOP, null, null));
        btnDisconnect.setForeground(new Color(255, 255, 255));
        btnDisconnect.setBackground(new Color(51, 51, 51));
        btnDisconnect.setBounds(83, 38, 122, 27);
        panel_1.add(btnDisconnect);
        btnDisconnect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                new Thread(() -> {
                    try {
                        Connection.disConnect();
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } finally {
                        String connectionStatus = Connection.isConnect();
                        connectionStatusLbl.setText(String.valueOf(connectionStatus));
                    }
                }).start();
            }
        });
        btnDisconnect.setFont(new Font("Arial", Font.BOLD, 15));

        JButton connectBtn = new JButton("Connect");
        connectBtn.setBorder(new TitledBorder(null, "", TitledBorder.LEFT, TitledBorder.TOP, null, null));
        connectBtn.setForeground(new Color(255, 255, 255));
        connectBtn.setBackground(new Color(51, 51, 51));
        connectBtn.setBounds(83, 76, 122, 27);
        panel_1.add(connectBtn);
        connectBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                new Thread(() -> {
                    try {

                        // ----- Spx ----- //
                        spx.getTwsData().getOptionMonthContract().lastTradeDateOrContractMonth(spxField.getText());
                        spx.getOptionsHandler().getOptionsMonth().setExpDate(spx.convertStringToDate(spxField.getText()));
                        // Day
                        spx.getTwsData().getOptionsDayContract().lastTradeDateOrContractMonth(spxDayOptionExpField.getText());
                        spx.getOptionsHandler().getOptionsDay().setExpDate(spx.convertStringToDate(spxDayOptionExpField.getText()));

                        // Quarter
                        spx.getTwsData().getOptionsQuarterContract().lastTradeDateOrContractMonth(spxQuarterField.getText());
                        spx.getOptionsHandler().getOptionsQuarter().setExpDate(spx.convertStringToDate(spxQuarterField.getText()));

                        // Quarter far
//						spx.getTwsData ( ).getOptionsQuarterFarContract ( ).lastTradeDateOrContractMonth ( spxQuarterFarField.getText ( ) );
//						spx.getOptionsQuarterFar ( ).setExpDate ( spx.convertStringToDate ( spxQuarterFarField.getText ( ) ) );

                        // ----- Ndx ----- //
                        // Day
                        ndx.getTwsData().getOptionsDayContract().lastTradeDateOrContractMonth(ndxDayOptionExpField.getText());
                        ndx.getOptionsHandler().getOptionsDay().setExpDate(ndx.convertStringToDate(ndxDayOptionExpField.getText()));

                        // Month
                        ndx.getTwsData().getOptionMonthContract().lastTradeDateOrContractMonth(ndxField.getText());
                        ndx.getOptionsHandler().getOptionsMonth().setExpDate(ndx.convertStringToDate(ndxField.getText()));

                        // Quarter
                        ndx.getTwsData().getOptionsQuarterContract().lastTradeDateOrContractMonth(ndxQuarterField.getText());
                        ndx.getOptionsHandler().getOptionsQuarter().setExpDate(ndx.convertStringToDate(ndxQuarterField.getText()));

                        // Quarter far
//						ndx.getTwsData ( ).getOptionsQuarterFarContract ( ).lastTradeDateOrContractMonth ( ndxQuarterFarField.getText ( ) );
//						ndx.getOptionsQuarterFar ( ).setExpDate ( ndx.convertStringToDate ( ndxQuarterFarField.getText ( ) ) );

                        Connection.connect();
                        LogWindow logWindow = new LogWindow();
                        logWindow.frame.setVisible(true);
                        Thread.sleep(3000);

                        spx.getTwsRequestHandler().requestFutreAndIndex();
                        ndx.getTwsRequestHandler().requestFutreAndIndex();

                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } finally {
                        String connectionStatus = Connection.isConnect();
                        connectionStatusLbl.setText(String.valueOf(connectionStatus));
                    }
                }).start();
            }
        });
        connectBtn.setFont(new Font("Arial", Font.BOLD, 15));

        connectionStatusLbl = new JLabel("False");
        connectionStatusLbl.setForeground(new Color(255, 250, 250));
        connectionStatusLbl.setBounds(214, 73, 192, 34);
        panel_1.add(connectionStatusLbl);
        connectionStatusLbl.setHorizontalAlignment(SwingConstants.CENTER);
        connectionStatusLbl.setFont(new Font("Dubai Medium", Font.PLAIN, 15));

        twsPortField = new JTextField("56");
        twsPortField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {

                if (!twsPortField.getText().isEmpty()) {
                    try {
                        Manifest.CLIENT_ID = Integer.parseInt(twsPortField.getText());
                    } catch (Exception e2) {
                        // TODO: handle exception
                    }
                }
            }
        });
        twsPortField.setBorder(new CompoundBorder());
        twsPortField.setFont(new Font("Dubai Medium", Font.PLAIN, 12));
        twsPortField.setBackground(new Color(204, 204, 204));
        twsPortField.setHorizontalAlignment(SwingConstants.CENTER);
        twsPortField.setColumns(10);
        twsPortField.setBounds(10, 80, 63, 20);
        panel_1.add(twsPortField);

        JLabel lblPort = new JLabel("Port");
        lblPort.setHorizontalAlignment(SwingConstants.CENTER);
        lblPort.setForeground(new Color(255, 250, 250));
        lblPort.setFont(new Font("Dubai Medium", Font.PLAIN, 15));
        lblPort.setBounds(10, 38, 65, 25);
        panel_1.add(lblPort);

        JLabel lblTwsConnection = new JLabel("Tws connection");
        lblTwsConnection.setBackground(new Color(255, 165, 0));
        lblTwsConnection.setHorizontalAlignment(SwingConstants.LEFT);
        lblTwsConnection.setForeground(new Color(255, 165, 0));
        lblTwsConnection.setFont(new Font("Dubai Medium", Font.PLAIN, 15));
        lblTwsConnection.setBounds(10, 0, 130, 25);
        panel_1.add(lblTwsConnection);

        JButton btnLogWindow = new JButton("Log");
        btnLogWindow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                LogWindow logWindow = new LogWindow();
                logWindow.frame.setVisible(true);

            }
        });
        btnLogWindow.setForeground(Color.WHITE);
        btnLogWindow.setFont(new Font("Arial", Font.BOLD, 12));
        btnLogWindow.setBorder(new TitledBorder(null, "", TitledBorder.LEFT, TitledBorder.TOP, null, null));
        btnLogWindow.setBackground(new Color(51, 51, 51));
        btnLogWindow.setBounds(452, 11, 122, 27);
        panel_1.add(btnLogWindow);

        JButton btnSetting = new JButton("Setting");
        btnSetting.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                Setting setting = new Setting("index");
                setting.frmSetting.setVisible(true);

            }
        });
        btnSetting.setForeground(Color.WHITE);
        btnSetting.setFont(new Font("Arial", Font.BOLD, 12));
        btnSetting.setBorder(new TitledBorder(null, "", TitledBorder.LEFT, TitledBorder.TOP, null, null));
        btnSetting.setBackground(new Color(51, 51, 51));
        btnSetting.setBounds(452, 54, 122, 27);
        panel_1.add(btnSetting);

        JButton btnStocksSetiing = new JButton("Stocks setiing");
        btnStocksSetiing.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                Setting setting = new Setting("stock");
                setting.frmSetting.setVisible(true);

            }
        });
        btnStocksSetiing.setForeground(Color.WHITE);
        btnStocksSetiing.setFont(new Font("Arial", Font.BOLD, 12));
        btnStocksSetiing.setBorder(new TitledBorder(null, "", TitledBorder.LEFT, TitledBorder.TOP, null, null));
        btnStocksSetiing.setBackground(new Color(51, 51, 51));
        btnStocksSetiing.setBounds(322, 54, 122, 27);
        panel_1.add(btnStocksSetiing);

        JPanel panel_2 = new JPanel();
        panel_2.setBorder(null);
        panel_2.setBounds(0, 114, 159, 245);
        panel.add(panel_2);
        panel_2.setBackground(Themes.BINANCE_GREY);
        panel_2.setLayout(null);

        JLabel lblOpenCounterWindow = new JLabel("Counter");
        lblOpenCounterWindow.setForeground(new Color(255, 250, 250));
        lblOpenCounterWindow.setFont(new Font("Dubai Medium", Font.PLAIN, 15));
        lblOpenCounterWindow.setBounds(10, 53, 61, 34);
        panel_2.add(lblOpenCounterWindow);

        wallstreetWindowBtn = new JButton("Wall street");
        wallstreetWindowBtn.setBorder(new TitledBorder(null, "", TitledBorder.LEFT, TitledBorder.TOP, null, null));
        wallstreetWindowBtn.setForeground(new Color(255, 255, 255));
        wallstreetWindowBtn.setBackground(new Color(51, 51, 51));
        wallstreetWindowBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                WallStreetWindow window = new WallStreetWindow();
                window.start();
            }
        });
        wallstreetWindowBtn.setFont(new Font("Arial", Font.BOLD, 12));
        wallstreetWindowBtn.setBounds(10, 85, 104, 27);
        panel_2.add(wallstreetWindowBtn);

        JButton ndxStocksWindowBtn = new JButton("Stocks");
        ndxStocksWindowBtn.setBounds(10, 123, 104, 27);
        panel_2.add(ndxStocksWindowBtn);
        ndxStocksWindowBtn.setBorder(new TitledBorder(null, "", TitledBorder.LEFT, TitledBorder.TOP, null, null));
        ndxStocksWindowBtn.setForeground(new Color(255, 255, 255));
        ndxStocksWindowBtn.setBackground(new Color(51, 51, 51));
        ndxStocksWindowBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StocksWindow stocksWindow = new StocksWindow();
                stocksWindow.start();
            }
        });
        ndxStocksWindowBtn.setFont(new Font("Arial", Font.BOLD, 12));

        JLabel lblWindow = new JLabel("Windows");
        lblWindow.setHorizontalAlignment(SwingConstants.LEFT);
        lblWindow.setForeground(new Color(255, 165, 0));
        lblWindow.setFont(new Font("Dubai Medium", Font.PLAIN, 15));
        lblWindow.setBackground(new Color(255, 165, 0));
        lblWindow.setBounds(10, 0, 130, 25);
        panel_2.add(lblWindow);

        JPanel panel_4 = new JPanel();
        panel_4.setBorder(null);
        panel_4.setBounds(161, 114, 423, 245);
        panel.add(panel_4);
        panel_4.setBackground(Themes.BINANCE_GREY);
        panel_4.setLayout(null);

        JLabel lblUsa = new JLabel("Spx");
        lblUsa.setForeground(new Color(255, 250, 250));
        lblUsa.setBounds(10, 52, 55, 25);
        panel_4.add(lblUsa);
        lblUsa.setHorizontalAlignment(SwingConstants.CENTER);
        lblUsa.setFont(new Font("Dubai Medium", Font.PLAIN, 15));

        JLabel lblNdx = new JLabel("Ndx");
        lblNdx.setForeground(new Color(255, 250, 250));
        lblNdx.setBounds(10, 82, 55, 25);
        panel_4.add(lblNdx);
        lblNdx.setHorizontalAlignment(SwingConstants.CENTER);
        lblNdx.setFont(new Font("Dubai Medium", Font.PLAIN, 15));

        spxField = new JTextField();
        spxField.setText("20200220");
        spxField.setBorder(new CompoundBorder());
        spxField.setFont(new Font("Dubai Medium", Font.PLAIN, 12));
        spxField.setBackground(new Color(204, 204, 204));
        spxField.setHorizontalAlignment(SwingConstants.CENTER);
        spxField.setBounds(142, 52, 70, 20);
        panel_4.add(spxField);
        spxField.setColumns(10);

        ndxField = new JTextField();
        ndxField.setText("20200220");
        ndxField.setBorder(new CompoundBorder());
        ndxField.setFont(new Font("Dubai Medium", Font.PLAIN, 12));
        ndxField.setBackground(new Color(204, 204, 204));
        ndxField.setHorizontalAlignment(SwingConstants.CENTER);
        ndxField.setBounds(142, 83, 70, 20);
        panel_4.add(ndxField);
        ndxField.setColumns(10);

        ndxDayOptionExpField = new JTextField();
        ndxDayOptionExpField.setText("20200124");
        ndxDayOptionExpField.setBorder(new CompoundBorder());
        ndxDayOptionExpField.setFont(new Font("Dubai Medium", Font.PLAIN, 12));
        ndxDayOptionExpField.setBackground(new Color(204, 204, 204));
        ndxDayOptionExpField.setHorizontalAlignment(SwingConstants.CENTER);
        ndxDayOptionExpField.setBounds(69, 83, 70, 20);
        panel_4.add(ndxDayOptionExpField);
        ndxDayOptionExpField.setColumns(10);

        ndxQuarterField = new JTextField();
        ndxQuarterField.setText("20200319");
        ndxQuarterField.setBorder(new CompoundBorder());
        ndxQuarterField.setColumns(10);
        ndxQuarterField.setFont(new Font("Dubai Medium", Font.PLAIN, 12));
        ndxQuarterField.setBackground(new Color(204, 204, 204));
        ndxQuarterField.setHorizontalAlignment(SwingConstants.CENTER);
        ndxQuarterField.setBounds(215, 83, 70, 20);
        panel_4.add(ndxQuarterField);

        JLabel lblContractsDetails = new JLabel("Contracts details");
        lblContractsDetails.setHorizontalAlignment(SwingConstants.LEFT);
        lblContractsDetails.setForeground(new Color(255, 165, 0));
        lblContractsDetails.setFont(new Font("Dubai Medium", Font.PLAIN, 15));
        lblContractsDetails.setBackground(new Color(255, 165, 0));
        lblContractsDetails.setBounds(10, 0, 130, 25);
        panel_4.add(lblContractsDetails);

        spxDayOptionExpField = new JTextField();
        spxDayOptionExpField.setText("20200124");
        spxDayOptionExpField.setHorizontalAlignment(SwingConstants.CENTER);
        spxDayOptionExpField.setFont(new Font("Dubai Medium", Font.PLAIN, 12));
        spxDayOptionExpField.setColumns(10);
        spxDayOptionExpField.setBorder(new CompoundBorder());
        spxDayOptionExpField.setBackground(new Color(204, 204, 204));
        spxDayOptionExpField.setBounds(69, 52, 70, 20);
        panel_4.add(spxDayOptionExpField);

        spxQuarterField = new JTextField("20200319");
        spxQuarterField.setHorizontalAlignment(SwingConstants.CENTER);
        spxQuarterField.setFont(new Font("Dubai Medium", Font.PLAIN, 12));
        spxQuarterField.setColumns(10);
        spxQuarterField.setBorder(new CompoundBorder());
        spxQuarterField.setBackground(new Color(204, 204, 204));
        spxQuarterField.setBounds(215, 52, 70, 20);
        panel_4.add(spxQuarterField);

        spxQuarterFarField = new JTextField("20200618");
        spxQuarterFarField.setHorizontalAlignment(SwingConstants.CENTER);
        spxQuarterFarField.setFont(new Font("Dubai Medium", Font.PLAIN, 12));
        spxQuarterFarField.setColumns(10);
        spxQuarterFarField.setBorder(new CompoundBorder());
        spxQuarterFarField.setBackground(new Color(204, 204, 204));
        spxQuarterFarField.setBounds(288, 52, 70, 20);
        panel_4.add(spxQuarterFarField);

        ndxQuarterFarField = new JTextField();
        ndxQuarterFarField.setText("20200618");
        ndxQuarterFarField.setBorder(new CompoundBorder());
        ndxQuarterFarField.setColumns(10);
        ndxQuarterFarField.setFont(new Font("Dubai Medium", Font.PLAIN, 12));
        ndxQuarterFarField.setBackground(new Color(204, 204, 204));
        ndxQuarterFarField.setHorizontalAlignment(SwingConstants.CENTER);
        ndxQuarterFarField.setBounds(288, 83, 70, 20);
        panel_4.add(ndxQuarterFarField);

    }

    // JOptionpane popup
    public void popUpMessage(String message, Exception e) {
        JOptionPane.showMessageDialog(frame, message + "\n" + e.getMessage() + "\n" + e.getCause());
    }
}
