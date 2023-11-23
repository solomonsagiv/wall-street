package gui.mainWindow;

import DDE.DDEConnection;
import DDE.DDEReader;
import DDE.DDEWriter;
import api.Manifest;
import com.pretty_tools.dde.DDEException;
import com.pretty_tools.dde.client.DDEClientConversation;
import dataBase.mySql.ConnectionPool;
import dataBase.mySql.MySql;
import dataBase.mySql.dataUpdaters.IDataBaseHandler;
import gui.MyGuiComps;
import locals.L;
import locals.LocalHandler;
import locals.Themes;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Dax;
import serverObjects.indexObjects.Ndx;
import serverObjects.indexObjects.Spx;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConnectionPanel extends MyGuiComps.MyPanel {



    // Variables
    JComboBox clientComboBox;
    MyGuiComps.MyLabel connecionLbl = new MyGuiComps.MyLabel("Connection");
    MyGuiComps.MyButton connectionBtn = new MyGuiComps.MyButton("Connect");
    MyGuiComps.MyButton disConnectBtn = new MyGuiComps.MyButton("Disconnect");
    MyGuiComps.MyButton upload_rates_btn = new MyGuiComps.MyButton("Upload rates");
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
                    case "NDX":
                        client = Ndx.getInstance();
                        break;
                    case "DAX":
                        client = Dax.getInstance();
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
                    // Connection
                    Connection connection = ConnectionPool.get_slo_single_connection();

                    // Trunticate current
                    MySql.trunticate("topics_to_monitor", "sapi", connection);

                    // Update sapi request
                    update_sapi_request();

                    upload_params_button.complete();
                    MyGuiComps.color_on_complete(upload_params_button);
                } catch (Exception exception) {
                    exception.printStackTrace();
                    JOptionPane.showMessageDialog(null, exception.getCause());
                }
            }
        });

        upload_rates_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Delete current
                delete_rates();

                // Insert new rates
                insert_rates();
            }
        });
    }

    private void delete_rates() {
        // Delete current
        Connection slo_conn = null;
        Connection jibe_dev_conn = null;
        try {
            slo_conn = ConnectionPool.get_slo_single_connection();
            jibe_dev_conn = ConnectionPool.get_jibe_dev_single_connection();

            MySql.Queries.delete_today_rates(slo_conn);
            MySql.Queries.delete_today_rates();
            MySql.Queries.delete_today_rates(jibe_dev_conn);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void insert_rates() {
        // I
        for (BASE_CLIENT_OBJECT client : LocalHandler.clients) {
            try {
                //
                client.getDdeHandler().getIddeReader().init_rates();
                IDataBaseHandler.insert_interes_rates(client);
            } catch (Exception exception) {
                exception.printStackTrace();
                JOptionPane.showMessageDialog(null, exception.getCause());
            }
        }

//        String file = Manifest.STOCKS_EXCEL_FILE_LOCATION;
//        System.out.println(file);
//
//        // Stocks
//        init_stocks_rates();
    }


    private void init_stocks_rates() {
        try {
            DDEClientConversation conversation = new DDEConnection().createNewConversation(Manifest.STOCKS_EXCEL_FILE_LOCATION);

            // Aapl
            String aapl_symbol = "aapl";
            String exp_type = "week";
            double aapl_interest = L.dbl(conversation.request("R17C5"));
            double aapl_dividend = L.dbl(conversation.request("R17C6"));
            int aapl_days_left = (int) L.dbl(conversation.request("R17C7"));
            double aapl_base = L.dbl(conversation.request("R17C8"));

            MySql.Queries.update_stock_rates(aapl_symbol, aapl_interest, aapl_dividend, aapl_days_left, aapl_base, exp_type);

        } catch (NullPointerException | DDEException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Stocks excel file not found " + e.getMessage());
        }

    }

    public static void update_sapi_request() {
        try {
            DDEConnection ddeConnection = new DDEConnection();
//            String path = Spx.getInstance().getSapi_excel_path();

//            String path = "C:/Users/yosef/Desktop/[bbg index.xlsm]SAPI List";

            String path = "C:/Users/yosef/OneDrive/Desktop/[WS trading.xlsm]SAPI List";

            DDEClientConversation conversation = ddeConnection.createNewConversation(path);

            int col = 3;
            ArrayList<String> strings = new ArrayList<>();
            String base_cell = "R%sC%s";

            for (int row = 11; row < 1000; row++) {
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

                String q = String.format(queryBuiler.toString(), "sapi.topics_to_monitor");

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
            }  // Ndx
            else if (clientComboBox.getSelectedItem().equals("NDX")) {
                registerClient(Ndx.getInstance());
            }
            // Dax
            else if (clientComboBox.getSelectedItem().equals("DAX")) {
                registerClient(Dax.getInstance());
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
        upload_params_button.setWidth(140);
        add(upload_params_button);

        // Upload rates
        upload_rates_btn.setXY(upload_params_button.getX() + upload_params_button.getWidth() + 3, upload_params_button.getY());
        upload_rates_btn.setBackground(Themes.BLUE_DARK);
        upload_rates_btn.setForeground(Color.WHITE);
        upload_rates_btn.setWidth(140);
        add(upload_rates_btn);

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
