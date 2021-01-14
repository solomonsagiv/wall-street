package gui;

import dataBase.mySql.ConnectionPool;
import exp.Exp;
import exp.ExpStrings;
import locals.L;
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
import java.util.Map;

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
    Exp exp;

    /**
     * Create the application.
     */
    public DetailsWindow(BASE_CLIENT_OBJECT client) {
        this.client = client;
        this.exp = client.getExps().getMainExp();

        onStartUp();

        initialize();

        // Start Runner thread
        startRunner();
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    window = new DetailsWindow(Spx.getInstance());
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void onStartUp() {
        optionsTypes = new String[client.getExps().getExpList().size()];
        int i = 0;
        for (Map.Entry<String, Exp> exp : client.getExps().getExpMap().entrySet()) {
            optionsTypes[i] = exp.getKey();
            i++;
        }
    }

    public void startRunner() {
        if (runner == null) {
            runner = new Runner();
        }
        runner.start();
    }

    public void closeRunner() {
        if (runner != null) {
            runner.close();
        }
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent arg0) {
                runner.close();
            }
        });
        frame.setBounds(100, 100, 900, 454);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        optionsCombo = new JComboBox(optionsTypes);
        optionsCombo.setBounds(750, 50, 120, 30);
        optionsCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                switch (optionsCombo.getSelectedItem().toString().toLowerCase()) {
                    case "week":
                        exp = client.getExps().getExp(ExpStrings.week);
                        break;
                    case "month":
                        exp = client.getExps().getExp(ExpStrings.month);
                        break;
                    case "quarter":
                        exp = client.getExps().getExp(ExpStrings.e1);
                        break;
                    case "quarter_far":
                        exp = client.getExps().getExp(ExpStrings.e2);
                        break;
                    case "main":
                        exp = client.getExps().getMainExp();
                        break;
                    default:
                        break;
                }
            }
        });

        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 255, 255));
        panel.setBounds(0, 0, 900, 415);
        frame.getContentPane().add(panel);
        panel.setLayout(null);
        panel.add(optionsCombo);

        JPanel panel_1 = new JPanel();
        panel_1.setBorder(null);
        panel_1.setBackground(SystemColor.inactiveCaption);
        panel_1.setBounds(0, 53, 300, 351);
        panel.add(panel_1);
        panel_1.setLayout(null);

        textArea = new JTextArea();
        textArea.setBackground(Color.WHITE);
        textArea.setFont(new Font("Dubai Medium", Font.PLAIN, 14));
        textArea.setBorder(new EmptyBorder(7, 7, 7, 7));
        textArea.setBounds(0, 0, 6, 15);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(null);
        scrollPane.setBounds(0, 0, panel_1.getWidth(), panel_1.getHeight());
        panel_1.add(scrollPane);

        stockNameField = new JLabel(client.getName().toUpperCase());
        stockNameField.setFont(new Font("Dubai Medium", Font.PLAIN, 16));
        stockNameField.setBounds(10, 11, 128, 20);
        panel.add(stockNameField);

        JSeparator separator = new JSeparator();
        separator.setForeground(Color.BLACK);
        separator.setBackground(Color.BLACK);
        separator.setBounds(10, 42, 230, 11);
        panel.add(separator);

        JPanel panel_2 = new JPanel();
        panel_2.setLayout(null);
        panel_2.setBorder(null);
        panel_2.setBackground(SystemColor.inactiveCaption);
        panel_2.setBounds(300, 53, 406, 351);
        panel.add(panel_2);

        optionsArea = new JTextArea();
        optionsArea.setFont(new Font("Dubai Medium", Font.PLAIN, 14));
        optionsArea.setBorder(new EmptyBorder(7, 7, 7, 7));
        optionsArea.setBackground(Color.WHITE);
        optionsArea.setBounds(0, 0, 283, 351);

        JScrollPane optionsScrollPane = new JScrollPane(optionsArea);
        optionsScrollPane.setBorder(null);
        optionsScrollPane.setBounds(0, 0, 406, 351);
        panel_2.add(optionsScrollPane);

        JSeparator separator_1 = new JSeparator();
        separator_1.setForeground(Color.BLACK);
        separator_1.setBackground(Color.BLACK);
        separator_1.setBounds(300, 42, 430, 11);
        panel.add(separator_1);

        JLabel lblOptions = new JLabel("Options");
        lblOptions.setFont(new Font("Dubai Medium", Font.PLAIN, 16));
        lblOptions.setBounds(300, 11, 128, 20);
        panel.add(lblOptions);

    }

    class Runner extends Thread {

        boolean run = true;

        @Override
        public void run() {

            while (run) {
                try {

                    // Write data
                    writeData();

                    // Sleep
                    sleep(1000);
                } catch (InterruptedException e) {
                    close();
                }
            }
        }

        private void close() {
            run = false;
        }

        private void writeData() {

            String text = convertListToString();

            textArea.setText(text);
        }

        private ArrayList<String> getToStringList() {
            ArrayList<String> list = new ArrayList<>();
            list.add("Started: " + client.isStarted());
            list.add("Index: " + client.getIndex());
            list.add("IndexBidAskCounter: " + client.getIndexBidAskCounter());
            list.add("Base: " + client.getBase());
            list.add("\n");
            list.add("DB: " + client.isDbRunning());
            list.add("MySql: " + client.getMyServiceHandler().isExist(client.getMySqlService()));
            list.add("\n");
            list.add("Exp date: " + exp.getExpDate());
            list.add("Start strike: " + client.getStartStrike());
            list.add("End strike: " + client.getEndStrike());
            list.add("");
            list.add("Tws Contract");
            list.add("");
            list.add("All details");
            list.add(client.toStringPretty());
            list.add("Connections: " + L.str(ConnectionPool.getConnectionsPoolInstance().getConnectionsCount()));
            list.add("Used connections: " + L.str(ConnectionPool.getConnectionsPoolInstance().getUseConnectionsCount()));
            return list;
        }

        private String convertListToString() {
            StringBuilder text = new StringBuilder();
            for (String string : getToStringList()) {
                if (string.equals("\n")) {
                    text.append(string);
                } else {
                    text.append(string).append("\n");
                }
            }
            return text.toString();
        }
    }
}


