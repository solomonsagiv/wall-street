package gui.popupsFactory;

import charts.myCharts.*;
import gui.DetailsWindow;
import serverObjects.indexObjects.INDEX_CLIENT_OBJECT;
import setting.clientSetting.SettingWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PopupsMenuFactory {

    public static JPopupMenu indexPanel(INDEX_CLIENT_OBJECT client) {

        // Main menu
        JPopupMenu menu = new JPopupMenu();

        // Charts menu
        JMenu charts = new JMenu("Charts");

        // Setting
        JMenuItem settingWindow = new JMenuItem("Setting");
        settingWindow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SettingWindow(client.getName(), client);
            }
        });

        JMenuItem df_speeds = new JMenuItem("DF & Speed");
        df_speeds.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DF_SPEED_Chart chart = new DF_SPEED_Chart(client);
                chart.createChart();
            }
        });

        JMenuItem fullCharts_2 = new JMenuItem("Full charts 2");
        fullCharts_2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Full_Chart_2 chart = new Full_Chart_2(client);
                chart.createChart();
            }
        });

        JMenuItem fullCharts_4 = new JMenuItem("Full charts 4");
        fullCharts_4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Full_Chart_4 chart = new Full_Chart_4(client);
                chart.createChart();
            }
        });

        JMenuItem op_avg_chart = new JMenuItem("O/P Avg");
        op_avg_chart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OpAvg_Index_Chart chart = new OpAvg_Index_Chart(client);
                chart.createChart();
            }
        });

        JMenuItem delta_index_3600 = new JMenuItem("Delta 1800");
        delta_index_3600.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Delta_Index_1800_Chart chart = new Delta_Index_1800_Chart(client);
                chart.createChart();
            }
        });

        JMenuItem baskets = new JMenuItem("Baskets");
        baskets.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (client.getBasketFinder() != null) {
                    Index_baskets_chart chart = new Index_baskets_chart(client);
                    chart.createChart();
                }
            }
        });

        JMenuItem futures_real_time_long = new JMenuItem("Futures real time long");
        futures_real_time_long.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FuturesChartLong chart = new FuturesChartLong(client);
                chart.createChart();
            }
        });

        JMenuItem index_plus_opavg = new JMenuItem("Index + O/P avg");
        index_plus_opavg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Index_plus_opAvg_Chart chart = new Index_plus_opAvg_Chart(client);
                chart.createChart();
            }
        });

        JMenuItem details = new JMenuItem("Details");
        details.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DetailsWindow detailsWindow = new DetailsWindow(client);
                detailsWindow.frame.setVisible(true);
            }
        });

        charts.add(index_plus_opavg);
        charts.add(delta_index_3600);
        charts.add(fullCharts_2);
        charts.add(fullCharts_4);
        charts.add(baskets);
        charts.add(futures_real_time_long);
        charts.add(op_avg_chart);
        charts.add(df_speeds);

        menu.add(details);
        menu.add(settingWindow);
        menu.add(charts);

        return menu;
    }
}
