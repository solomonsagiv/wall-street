package gui.popupsFactory;

import charts.myCharts.DAX_CHART_10;
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
                new SettingWindow(client.getName() + " setting", client);
            }
        });

        JMenuItem fullCharts_4 = new JMenuItem("Full charts 4");
        fullCharts_4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Chart_4 chart = new Chart_4(client);
                chart.createChart();
            }
        });

        JMenuItem fullCharts_q1 = new JMenuItem("Full charts Q1");
        fullCharts_q1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Full_Chart_Q1 chart = new Full_Chart_Q1(client);
                chart.createChart();
            }
        });

        JMenuItem charts_12 = new JMenuItem("Charts 12");
        charts_12.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Chart_12 chart = new Chart_12(client);
                chart.createChart();
            }
        });

        JMenuItem charts_13 = new JMenuItem("Charts 13 (Dax)");
        charts_13.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Chart_13 chart = new Chart_13(client);
                chart.createChart();
            }
        });

        JMenuItem europe_op_avg = new JMenuItem("Dax chart 10");
        europe_op_avg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DAX_CHART_10 chart = new DAX_CHART_10(client);
                chart.createChart();
            }
        });

        JMenuItem baskets = new JMenuItem("Baskets");
        baskets.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (client.getBasketFinder_by_stocks() != null) {
                    Index_baskets_chart chart = new Index_baskets_chart(client);
                    chart.createChart();
                }
            }
        });

        JMenuItem real_time = new JMenuItem("Realtime");
        real_time.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Realtime_Chart chart = new Realtime_Chart(client);
                chart.createChart();
            }
        });

        JMenuItem futures = new JMenuItem("Realtime 2");
        futures.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Realtime_2_Chart chart = new Realtime_2_Chart(client);
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

        JMenuItem races = new JMenuItem("Races");
        races.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Races_Chart chart = new Races_Chart(client);
                chart.createChart();
            }
        });

        charts.add(charts_12);
        charts.add(charts_13);
        charts.add(real_time);
        charts.add(races);

        menu.add(details);
        menu.add(settingWindow);
        menu.add(charts);

        return menu;
    }
}
