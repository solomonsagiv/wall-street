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


        JMenuItem charts_5 = new JMenuItem("Charts 5");
        charts_5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Charts5 chart = new Charts5(client);
                chart.createChart();
            }
        });


        JMenuItem charts_6 = new JMenuItem("Charts 6");
        charts_6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Chart_6 chart = new Chart_6(client);
                chart.createChart();
            }
        });


        JMenuItem charts_7 = new JMenuItem("Charts 7");
        charts_7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Chart_7 chart = new Chart_7(client);
                chart.createChart();
            }
        });

        JMenuItem wallstreet = new JMenuItem("Wall-street");
        wallstreet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Chart_wallstreet chart = new Chart_wallstreet(client);
                chart.createChart();
            }
        });


        JMenuItem europe = new JMenuItem("Europe");
        europe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Chart_Europe chart = new Chart_Europe(client);
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


        JMenuItem futures_real_time_400 = new JMenuItem("Futures 400");
        futures_real_time_400.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FuturesChartLong_400 chart = new FuturesChartLong_400(client);
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

        charts.add(charts_5);
        charts.add(fullCharts_4);
        charts.add(charts_7);
        charts.add(europe);
        charts.add(fullCharts_q1);
        charts.add(futures_real_time_400);
        charts.add(wallstreet);
        charts.add(europe);

        menu.add(details);
        menu.add(settingWindow);
        menu.add(charts);

        return menu;
    }
}
