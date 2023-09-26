package arik.window;

import arik.Arik;
import gui.AlertWindow;
import gui.MyGuiComps;
import tws.accounts.ConnectionsAndAccountHandler;
import tws.handlers.AccountAndConnection;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ArikMainPanel extends MyGuiComps.MyPanel {

    MyGuiComps.MyLabel header;
    MyGuiComps.MyButton startBtn;
    MyGuiComps.MyButton testBtn;
    JCheckBox enableTestBtn;
    JCheckBox trading_check_box;
    MyGuiComps.MyButton connect_tws_clients_btn;
    JComboBox<String> comboBox;

    public static JTextArea textArea;
    public static JTextArea connectionTextArea;


    public ArikMainPanel() {
        super();
        initListeners();
    }

    private void initListeners() {

        // Start arik
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Arik.getInstance().start();
                startBtn.setEnabled(false);
            }
        });

        // Allow trading
        trading_check_box.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBox source = (JCheckBox) e.getSource();
                if (source.isSelected()) {
                    Arik.allow_trading = true;
                } else {
                    Arik.allow_trading = false;
                }
            }
        });

        // Connect tws clients
        connect_tws_clients_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    // Connect accounts
                    if (comboBox.getSelectedItem().toString().toLowerCase().equals("all")) {
                        ConnectionsAndAccountHandler.connect_all_clients();
                    } else {
                        AccountAndConnection accountAndConnection = ConnectionsAndAccountHandler.accountAndConnectionHashMap.get(comboBox.getSelectedItem().toString());
                        ConnectionsAndAccountHandler.connect_single_client(accountAndConnection);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    AlertWindow.Show(e.getMessage(), e.getCause(), e.getStackTrace());
                }
            }
        });

        // Adding an action listener to the JComboBox
        comboBox.addActionListener(e -> {
            JComboBox<String> source = (JComboBox<String>) e.getSource();
            String selected = (String) source.getSelectedItem();
            System.out.println("Selected Item: " + selected);
        });


        // Test btn (Send order to connected accounts to test orders)
        testBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (comboBox.getSelectedItem().toString().toLowerCase().equals("all")) {
                    ConnectionsAndAccountHandler.send_test_order_all_accounts();
                } else {
                    AccountAndConnection accountAndConnection = ConnectionsAndAccountHandler.accountAndConnectionHashMap.get(comboBox.getSelectedItem().toString());
                    ConnectionsAndAccountHandler.send_single_account_order(accountAndConnection);
                }

            }

        });

        // Enable test btn to be clickable
        enableTestBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBox source = (JCheckBox) e.getSource();
                if (source.isSelected()) {
                    testBtn.setEnabled(true);
                } else {
                    testBtn.setEnabled(false);
                }
            }
        });
    }
    
    @Override
    protected void init() {
        super.init();

        // Header
        header = new MyGuiComps.MyLabel("Arik");
        header.setXY(5, 5);
        header.setFont(header.getFont().deriveFont(20f));
        add(header);

        // ComboBox
        comboBox = new JComboBox<>();
        comboBox.setBounds(header.getX(), header.getY() + header.getHeight() + 5, 150, 20);
        add(comboBox);

        // Start btn
        startBtn = new MyGuiComps.MyButton("Start");
        startBtn.setXY(comboBox.getX(), comboBox.getY() + comboBox.getHeight() + 5);
        startBtn.setWidth(150);
        startBtn.setHeight(100);
        add(startBtn);

        // Connect clients
        connect_tws_clients_btn = new MyGuiComps.MyButton("Connect Clients");
        connect_tws_clients_btn.setXY(startBtn.getX(), startBtn.getY() + startBtn.getHeight() + 5);
        connect_tws_clients_btn.setWidth(150);
        connect_tws_clients_btn.setHeight(100);
        add(connect_tws_clients_btn);

        // Trading check box
        trading_check_box = new JCheckBox();
        trading_check_box.setBounds(connect_tws_clients_btn.getX(), connect_tws_clients_btn.getY() + connect_tws_clients_btn.getHeight() + 5, 150, 20);
        trading_check_box.setText("Trading");
        add(trading_check_box);

        // Status scroll pane
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(startBtn.getX() +startBtn.getWidth() + 5, header.getY(), 300, 500);
        add(scrollPane);

        // Connection text area
        connectionTextArea = new JTextArea();
        JScrollPane connectionScrollPane = new JScrollPane(connectionTextArea);
        connectionScrollPane.setBounds(scrollPane.getX() + scrollPane.getWidth() + 5, scrollPane.getY(), 300, 500);
        add(connectionScrollPane);

        // Test Btn
        testBtn = new MyGuiComps.MyButton("Send order test");
        testBtn.setBounds(connectionScrollPane.getX() + connectionScrollPane.getWidth() + 5, connectionScrollPane.getY(), 150, 100);
        testBtn.setEnabled(false);
        add(testBtn);

        // Enable test btn checkbox
        enableTestBtn = new JCheckBox("Enable test button");
        enableTestBtn.setBounds(testBtn.getX(), testBtn.getY() + testBtn.getHeight() + 5, 150, 20);
        enableTestBtn.setSelected(false);
        add(enableTestBtn);

    }
}
