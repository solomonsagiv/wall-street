package gui.mainWindow;

import DDE.DDEConnection;
import DDE.DDEReader;
import DDE.DDEWriter;
import com.pretty_tools.dde.client.DDEClientConversation;
import dataBase.mySql.ConnectionPool;
import dataBase.mySql.MySql;
import dataBase.mySql.dataUpdaters.IDataBaseHandler;
import gui.MyGuiComps;
import locals.LocalHandler;
import locals.Themes;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Apple;
import serverObjects.indexObjects.Dax;
import serverObjects.indexObjects.Ndx;
import serverObjects.indexObjects.Spx;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConnectionPanel extends MyGuiComps.MyPanel {

    // Variables
    JComboBox clientComboBox;
    MyGuiComps.MyLabel connecionLbl = new MyGuiComps.MyLabel("Connection ");
    MyGuiComps.MyButton connectionBtn = new MyGuiComps.MyButton("Connect");
    MyGuiComps.MyButton disConnectBtn = new MyGuiComps.MyButton("Disconnect");
    MyGuiComps.MyLabel ddeStatusLbl = new MyGuiComps.MyLabel("DDE");
    public static MyGuiComps.MyTextField excelLocationField = new MyGuiComps.MyTextField();
    MyGuiComps.MyButton upload_params_button = new MyGuiComps.MyButton("Upload params");

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
                    case "APPLE":
                        client = Apple.getInstance();
                        break;
                    default:
                        client = Spx.getInstance();
                        break;
                }
                excelLocationField.setText(client.getExcel_path());
            }
        });

        excelLocationField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    client.setExcel_path(excelLocationField.getText());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getStackTrace());
                }
            }
        });

        upload_params_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    // Update sapi request
                    update_sapi_request();

                    upload_params_button.complete();
                    MyGuiComps.color_on_complete(upload_params_button);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    public static void update_sapi_request() {
        try {
            DDEConnection ddeConnection = new DDEConnection();
            String path = Spx.getInstance().getSapi_excel_path();
            DDEClientConversation conversation = ddeConnection.createNewConversation(path);

            int col = 2;
            ArrayList<String> strings = new ArrayList<>();
            String base_cell = "R%sC%s";

            for (int row = 2; row < 1000; row++) {
                String cell = String.format(base_cell, row, col);
                String s = conversation.request(cell);
                s = s.trim();
                if (!s.equals("0")) {
                    strings.add(s);
                } else {
                    break;
                }
            }

            // Create the query
            if (strings.size() > 0) {

                // Create the query
                StringBuilder queryBuiler = new StringBuilder("INSERT INTO %s (topic, fields, enabled, time_created) VALUES ");
                int last_item_id = strings.get(strings.size() - 1).hashCode();
                for (String s : strings) {
                    queryBuiler.append(String.format("('%s', null, true, now())", s));
                    if (s.hashCode() != last_item_id) {
                        queryBuiler.append(",");
                    }
                }
                queryBuiler.append(";");

                System.out.println(queryBuiler.toString());

                String q = String.format(queryBuiler.toString(), "sapi.topic_to_monitor");

                System.out.println(q);

                // Insert
                Connection connection = ConnectionPool.get_slo_single_connection();
                MySql.insert(q, connection);

                // Clear the list
                strings.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

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
            // Apple
            else if (clientComboBox.getSelectedItem().equals("APPLE")) {
                registerClient(Apple.getInstance());
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
        client.getDdeHandler().setDdeReaderThread(ddeReader);
        ddeReaders.put(client.getName(), ddeReader);

        // Writer
        DDEWriter ddeWriter = new DDEWriter(client);
        ddeWriter.getHandler().start();
        client.getDdeHandler().setDdeWriterThread(ddeWriter);
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

        // Connect btn
        connectionBtn.setXY(20, 50);
        connectionBtn.setForeground(Color.WHITE);
        connectionBtn.setBorder(BorderFactory.createLineBorder(Themes.GREEN.brighter()));
        connectionBtn.setBackground(Themes.GREEN);
        add(connectionBtn);

        // Disconnect btn
        disConnectBtn.setXY(connectionBtn.getX(), connectionBtn.getY() + connectionBtn.getHeight() + 3);
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

        // Upload params button
        upload_params_button.setXY(clientComboBox.getX() + clientComboBox.getWidth() + 3, clientComboBox.getY());
        upload_params_button.setBackground(Themes.BLUE_DARK);
        upload_params_button.setForeground(Color.WHITE);
        upload_params_button.setWidth(100);
        add(upload_params_button);


        // Status lbl
        ddeStatusLbl.setXY(disConnectBtn.getX() + disConnectBtn.getWidth() + 20, disConnectBtn.getY());
        ddeStatusLbl.setWidth(60);
        ddeStatusLbl.setHorizontalAlignment(JLabel.LEFT);
        ddeStatusLbl.setForeground(Themes.RED);
        add(ddeStatusLbl);

        // Excel location
        excelLocationField.setXY(disConnectBtn.getX(), disConnectBtn.getY() + disConnectBtn.getHeight() + 15);
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
