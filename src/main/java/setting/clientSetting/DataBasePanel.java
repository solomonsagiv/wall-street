package setting.clientSetting;

import dataBase.mySql.ConnectionPoolJibeProd;
import dataBase.mySql.MySql;
import dataBase.mySql.dataUpdaters.IDataBaseHandler;
import dataBase.props.Props;
import gui.MyGuiComps;
import locals.L;
import locals.Themes;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Spx;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class DataBasePanel extends MyGuiComps.MyPanel {

    public static void main(String[] args) {
        SettingWindow window = new SettingWindow("Test", Spx.getInstance());
    }


    // Variables
    BASE_CLIENT_OBJECT client;

    MyGuiComps.MyButton stopBtn;
    MyGuiComps.MyButton startBtn;
    MyGuiComps.MyButton updateRatesBtn;
    MyGuiComps.MyButton startJIbeDbBtn;
    MyGuiComps.MyButton stopJIbeDbBtn;
    MyGuiComps.MyButton setStartBoundsBtn;
    MyGuiComps.MyButton loadStartBoundsBtn;
    JComboBox pre_set_combox;
    MyGuiComps.MyTextField set_start_min_field;

    int pre_set = 1;

    // Constructor
    public DataBasePanel(BASE_CLIENT_OBJECT client) {
        this.client = client;
        initialize();
        initListeners();
    }

    private void initListeners() {

        // Start
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                client.getMyServiceHandler().addService(client.getMySqlService());
                startBtn.complete();
            }
        });

        // Stop
        stopBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                client.getMyServiceHandler().removeService(client.getMySqlService());
                stopBtn.complete();
            }
        });

        // Update rates
        updateRatesBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    client.getDdeHandler().getIddeReader().init_rates();

                    // Delete current
                    Connection slo_conn = ConnectionPoolJibeProd.get_slo_single_connection();
                    MySql.Queries.delete_today_rates(client.getId_name(),slo_conn);
                    MySql.Queries.delete_today_rates(client.getId_name());

                    // Update new one
                    IDataBaseHandler.insert_interes_rates(client);
                    updateRatesBtn.complete();
                    MyGuiComps.color_on_complete(updateRatesBtn);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

        startJIbeDbBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                client.setLive_db(true);
            }
        });

        stopJIbeDbBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                client.setLive_db(false);
            }
        });

        setStartBoundsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                for (MyGuiComps.MyFrame frame : MyGuiComps.Windows.frames) {
                    frame.set_start_bounds(pre_set);
                }
            }
        });

        loadStartBoundsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                for (MyGuiComps.MyFrame frame : MyGuiComps.Windows.frames) {
                    System.out.println(frame.getTitle() + " ----------------------------");
                    frame.load_start_bounds(pre_set);
                }
            }
        });

        pre_set_combox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                pre_set = L.INT(pre_set_combox.getSelectedItem().toString());
            }
        });

        set_start_min_field.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String min = set_start_min_field.getText();
                client.setChart_start_min(Integer.parseInt(min));
                MySql.Queries.update_prop(client.getId_name(), Props.CHART_START_MIN, min, MySql.JIBE_PROD_CONNECTION);
            }
        });
    }

    private void initialize() {

        // This
        setSize(150, 150);
        TitledBorder titledBorder = new TitledBorder("Data base");
        titledBorder.setTitleColor(Themes.BLUE_DARK);
        setBorder(titledBorder);

        // Start
        startBtn = new MyGuiComps.MyButton("Start mysql");
        startBtn.setXY(10, 30);
        startBtn.setBackground(Themes.BLUE);
        startBtn.setWidth(70);
        startBtn.setFont(startBtn.getFont().deriveFont(9f));
        startBtn.setForeground(Themes.GREY_VERY_LIGHT);
        add(startBtn);

        // Stop
        stopBtn = new MyGuiComps.MyButton("Stop mysql");
        stopBtn.setXY(90, 30);
        stopBtn.setWidth(70);
        stopBtn.setFont(stopBtn.getFont().deriveFont(9f));
        stopBtn.setBackground(Themes.BLUE);
        stopBtn.setForeground(Themes.GREY_VERY_LIGHT);
        add(stopBtn);

        // Update rates
        updateRatesBtn = new MyGuiComps.MyButton("Update rates");
        updateRatesBtn.setXY(stopBtn.getX() + stopBtn.getWidth() + 5, stopBtn.getY());
        add(updateRatesBtn);

        // Start jibe db
        startJIbeDbBtn = new MyGuiComps.MyButton("Start jibe DB");
        startJIbeDbBtn.setXY(updateRatesBtn.getX() + updateRatesBtn.getWidth() + 5, updateRatesBtn.getY());
        startJIbeDbBtn.setWidth(100);
        startJIbeDbBtn.setFont(startJIbeDbBtn.getFont().deriveFont(9f));
        startJIbeDbBtn.setBackground(Themes.BLUE);
        startJIbeDbBtn.setForeground(Themes.GREY_VERY_LIGHT);
        add(startJIbeDbBtn);


        // Stop jibe db
        stopJIbeDbBtn = new MyGuiComps.MyButton("Stop jibe DB");
        stopJIbeDbBtn.setXY(startJIbeDbBtn.getX() + startJIbeDbBtn.getWidth() + 5, startJIbeDbBtn.getY());
        stopJIbeDbBtn.setWidth(100);
        stopJIbeDbBtn.setFont(stopJIbeDbBtn.getFont().deriveFont(9f));
        stopJIbeDbBtn.setBackground(Themes.BLUE);
        stopJIbeDbBtn.setForeground(Themes.GREY_VERY_LIGHT);
        add(stopJIbeDbBtn);

        // Start bounds
        setStartBoundsBtn = new MyGuiComps.MyButton("Set start bounds");
        setStartBoundsBtn.setXY(stopJIbeDbBtn.getX() + stopJIbeDbBtn.getWidth() + 5, stopJIbeDbBtn.getY());
        setStartBoundsBtn.setWidth(140);
        setStartBoundsBtn.setFont(stopJIbeDbBtn.getFont().deriveFont(9f));
        setStartBoundsBtn.setBackground(Themes.BLUE);
        setStartBoundsBtn.setForeground(Themes.GREY_VERY_LIGHT);
        add(setStartBoundsBtn);

        // Load start bounds
        loadStartBoundsBtn = new MyGuiComps.MyButton("Load start bounds");
        loadStartBoundsBtn.setXY(setStartBoundsBtn.getX() + setStartBoundsBtn.getWidth() + 5, setStartBoundsBtn.getY());
        loadStartBoundsBtn.setWidth(140);
        loadStartBoundsBtn.setFont(stopJIbeDbBtn.getFont().deriveFont(9f));
        loadStartBoundsBtn.setBackground(Themes.BLUE);
        loadStartBoundsBtn.setForeground(Themes.GREY_VERY_LIGHT);
        add(loadStartBoundsBtn);

        MyGuiComps.MyLabel start_min_lbl = new MyGuiComps.MyLabel("set start min");
        start_min_lbl.setXY(startBtn.getX(), startBtn.getY() + startBtn.getHeight() + 5);
        start_min_lbl.setWidth(140);
        add(start_min_lbl);

        set_start_min_field = new MyGuiComps.MyTextField();
        set_start_min_field.setXY(start_min_lbl.getX(), start_min_lbl.getY() + start_min_lbl.getHeight() + 5);
        set_start_min_field.setWidth(140);
        set_start_min_field.setFont(set_start_min_field.getFont().deriveFont(9f));
        add(set_start_min_field);

        // Pre set check box
        pre_set_combox = new JComboBox(new String[] {"1", "2"});
        pre_set_combox.setBounds(loadStartBoundsBtn.getX(), loadStartBoundsBtn.getY() + loadStartBoundsBtn.getHeight() + 3, 100, 25);
        add(pre_set_combox);

    }
}
