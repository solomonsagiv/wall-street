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

        JMenuItem fullCharts_2 = new JMenuItem("Full charts 2");
        fullCharts_2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Full_Chart_2 chart = new Full_Chart_2(client);
                chart.createChart();
            }
        });

        JMenuItem fullCharts_3 = new JMenuItem("Full charts 3");
        fullCharts_3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Full_Chart_3 chart = new Full_Chart_3(client);
                chart.createChart();
            }
        });

        JMenuItem dec_funcs = new JMenuItem("Dec funcs");
        dec_funcs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DecisionsFuncChart chart = new DecisionsFuncChart(client);
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

        JMenuItem details = new JMenuItem("Details");
        details.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DetailsWindow detailsWindow = new DetailsWindow(client);
                detailsWindow.frame.setVisible(true);
            }
        });

        charts.add(fullCharts_2);
        charts.add(fullCharts_3);
        charts.add(dec_funcs);
        charts.add(baskets);
        charts.add(futures_real_time_long);

        menu.add(details);
        menu.add(settingWindow);
        menu.add(charts);

        return menu;
    }
}
