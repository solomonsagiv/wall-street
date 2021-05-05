package gui.mainWindow;

import DDE.DDEReader;
import DDE.DDEWriter;
import api.Manifest;
import gui.LogWindow;
import gui.MyGuiComps;
import locals.LocalHandler;
import locals.Themes;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Dax;
import serverObjects.indexObjects.Ndx;
import serverObjects.indexObjects.Spx;
import serverObjects.indexObjects.StockX;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class ConnectionPanel extends MyGuiComps.MyPanel {

    // Variables
    JComboBox clientComboBox;
    MyGuiComps.MyLabel connecionLbl = new MyGuiComps.MyLabel("Connection ");
    MyGuiComps.MyButton connectionBtn = new MyGuiComps.MyButton("Connect");
    MyGuiComps.MyButton disConnectBtn = new MyGuiComps.MyButton("Disconnect");
    MyGuiComps.MyButton logBtn = new MyGuiComps.MyButton("Log");
    MyGuiComps.MyLabel ddeStatusLbl = new MyGuiComps.MyLabel("DDE");
    MyGuiComps.MyLabel twsStatusLbl = new MyGuiComps.MyLabel("TWS");
    MyGuiComps.MyLabel portLbl = new MyGuiComps.MyLabel("Port");
    MyGuiComps.MyTextField portField = new MyGuiComps.MyTextField();
    public static MyGuiComps.MyTextField excelLocationField = new MyGuiComps.MyTextField();

    Map<String, DDEReader> ddeReaders = new HashMap<>();
    Map<String, DDEWriter> ddeWriters = new HashMap<>();

    BASE_CLIENT_OBJECT client;

    // Constructor
    public ConnectionPanel() {
        super();
        initialize();
        initListeners();
    }

    private void initListeners() {


        // Connection btn
        connectionBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                connectDDE();
            }
        });


        // Disconnect btn
        disConnectBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                for (Map.Entry<String, DDEReader> entry : ddeReaders.entrySet()) {
                    DDEReader val = entry.getValue();
                    val.getHandler().close();
                }

                for (Map.Entry<String, DDEWriter> entry : ddeWriters.entrySet()) {
                    DDEWriter val = entry.getValue();
                    val.getHandler().close();
                }

                ddeStatusLbl.setForeground(Themes.RED);
            }
        });

        // Log btn
        logBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Log window
                LogWindow logWindow = new LogWindow();
                logWindow.frame.setVisible(true);
            }
        });

        // Client combo
        clientComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                switch (clientComboBox.getSelectedItem().toString()) {
                    case "SPX":
                        client = Spx.getInstance();
                        break;
                    case "DAX":
                        client = Dax.getInstance();
                        break;
                    case "NDX":
                        client = Ndx.getInstance();
                        break;
                    case "STOCKX":
                        client = StockX.getInstance();
                        break;
                    default:
                        client = Spx.getInstance();
                        break;
                }
                excelLocationField.setText(client.getDdeHandler().getPath());
            }
        });

        excelLocationField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    client.getDdeHandler().setPath(excelLocationField.getText());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getStackTrace());
                }
            }
        });
    }

    public void connectAll() {
        connectDDE();
    }

    public void connectDDE() {
        try {
            // All
            if (clientComboBox.getSelectedItem().equals("ALL")) {
                for (BASE_CLIENT_OBJECT client : LocalHandler.clients) {
                    registerClient(client);
                }
                // Spx
            } else if (clientComboBox.getSelectedItem().equals("SPX")) {
                registerClient(Spx.getInstance());
                // Dax
            } else if (clientComboBox.getSelectedItem().equals("DAX")) {
                registerClient(Dax.getInstance());
            }  // Ndx
            else if (clientComboBox.getSelectedItem().equals("NDX")) {
                registerClient(Ndx.getInstance());
            }

            ddeStatusLbl.setForeground(Themes.GREEN);
        } catch (
                Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
        }

    }

    private void registerClient(BASE_CLIENT_OBJECT client) {

        // Reader
        DDEReader ddeReader = new DDEReader(client);
        ddeReader.getHandler().start();
        ddeReaders.put(client.getName(), ddeReader);

        // Writer
        DDEWriter ddeWriter = new DDEWriter(client);
        ddeWriter.getHandler().start();
        ddeWriters.put(client.getName(), ddeWriter);

    }

    private void initialize() {

        // This
        setWidth(500);
        setHeight(170);
        setBackground(Color.WHITE);

        // Connection
        connecionLbl.setXY(10, 10);
        connecionLbl.setWidth(150);
        connecionLbl.setFont(connectionBtn.getFont().deriveFont(Font.PLAIN));
        connecionLbl.setFont(connectionBtn.getFont().deriveFont(14f));
        connecionLbl.setHorizontalAlignment(JLabel.LEFT);
        connecionLbl.setForeground(Color.BLACK);
        add(connecionLbl);

        // Port lbl
        portLbl.setXY(20, 50);
        portLbl.setForeground(Themes.BLUE_DARK);
        portLbl.setWidth(80);
        portLbl.setHorizontalAlignment(JLabel.LEFT);
        portLbl.setFont(portLbl.getFont().deriveFont(12f).deriveFont(Font.BOLD));
        portLbl.setLabelFor(portField);
        add(portLbl);

        portField.setXY(20, 80);
        portField.setWidth(80);
        portField.setOpaque(false);
        portField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Themes.BLUE_DARK));
        portField.setForeground(Themes.BLUE_DARK);
        portField.setText(String.valueOf(Manifest.CLIENT_ID));
        portField.setEnabled(true);
        portField.setEditable(true);
        add(portField);

        // Connect btn
        connectionBtn.setXY(portField.getX() + portField.getWidth() + 20, 50);
        connectionBtn.setForeground(Color.WHITE);
        connectionBtn.setBorder(BorderFactory.createLineBorder(Themes.GREEN.brighter()));
        connectionBtn.setBackground(Themes.GREEN);
        add(connectionBtn);

        // Disconnect btn
        disConnectBtn.setXY(portField.getX() + portField.getWidth() + 20, 80);
        disConnectBtn.setForeground(Color.WHITE);
        disConnectBtn.setBackground(Themes.RED);
        disConnectBtn.setBorder(BorderFactory.createLineBorder(Themes.RED.brighter()));
        add(disConnectBtn);

        // Item comboBox
        String[] clientItems = getClientComboItems();

        clientComboBox = new JComboBox(clientItems);
        clientComboBox.setBounds(connectionBtn.getX() + connectionBtn.getWidth() + 5, connectionBtn.getY(), 80, 25);
        clientComboBox.setBackground(Themes.BLUE_DARK);
        clientComboBox.setForeground(Color.WHITE);
        add(clientComboBox);

        // Status lbl
        ddeStatusLbl.setXY(disConnectBtn.getX() + disConnectBtn.getWidth() + 20, disConnectBtn.getY());
        ddeStatusLbl.setWidth(60);
        ddeStatusLbl.setHorizontalAlignment(JLabel.LEFT);
        ddeStatusLbl.setForeground(Themes.RED);
        add(ddeStatusLbl);

        // Status lbl
        twsStatusLbl.setXY(ddeStatusLbl.getX() + ddeStatusLbl.getWidth() + 1, ddeStatusLbl.getY());
        twsStatusLbl.setWidth(60);
        twsStatusLbl.setHorizontalAlignment(JLabel.LEFT);
        twsStatusLbl.setForeground(Themes.RED);
        add(twsStatusLbl);

        // Log btn
        logBtn.setXY(ddeStatusLbl.getX() + ddeStatusLbl.getWidth() + 60, 80);
        logBtn.setBackground(Color.WHITE);
        logBtn.setBorder(BorderFactory.createLineBorder(Themes.BLUE_DARK.brighter()));
        logBtn.setForeground(Themes.BLUE_DARK);
        add(logBtn);

        // Excel location
        excelLocationField.setXY(portField.getX(), portField.getY() + portField.getHeight() + 15);
        excelLocationField.setWidth(400);
        excelLocationField.setHeight(25);
        excelLocationField.setText("excelPath");
        excelLocationField.setBackground(Color.WHITE);
        excelLocationField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Themes.BLUE_DARK));
        excelLocationField.setForeground(Themes.BLUE_DARK);
        add(excelLocationField);
    }

    private String[] getClientComboItems() {
        String[] arr = new String[LocalHandler.clients.size() + 1];
        arr[0] = "ALL";
        int i = 1;
        for (BASE_CLIENT_OBJECT client : LocalHandler.clients) {
            arr[i] = client.getName().toUpperCase();
            i++;
        }
        return arr;
    }
}
