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

        JMenuItem fullCharts_4 = new JMenuItem("Full charts 4");
        fullCharts_4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Full_Chart_4 chart = new Full_Chart_4(client);
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

        JMenuItem futures_real_time_1000 = new JMenuItem("Futures 1000");
        futures_real_time_1000.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FuturesChartLong_1000 chart = new FuturesChartLong_1000(client);
                chart.createChart();
            }
        });

        JMenuItem futures_real_time_300 = new JMenuItem("Futures 300");
        futures_real_time_300.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FuturesChartLong_300 chart = new FuturesChartLong_300(client);
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

        charts.add(fullCharts_4);
        charts.add(futures_real_time_300);
        charts.add(baskets);
        charts.add(futures_real_time_1000);

        menu.add(details);
        menu.add(settingWindow);
        menu.add(charts);

        return menu;
    }
}
