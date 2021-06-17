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

        JMenuItem fullCharts = new JMenuItem("Full charts");
        fullCharts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Full_Charts chart = new Full_Charts(client);
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

        JMenuItem threeFut = new JMenuItem("Futures real time");
        threeFut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FuturesChart chart = new FuturesChart(client, null, null);
                chart.createChart();
            }
        });

        JMenuItem stocks_delta_itam = new JMenuItem("Stocks delta");
        stocks_delta_itam.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StocksDeltaChart chart = new StocksDeltaChart(client);
                chart.createChart();
            }
        });

        // Export menu
        JMenu export = new JMenu("Export");

        JMenuItem exportSumLine = new JMenuItem("Export sum line");
        exportSumLine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // todo
            }
        });

        JMenuItem export_to_excel = new JMenuItem("Export to excel");
        export_to_excel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, exception.getStackTrace());
                }
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

        export.add(exportSumLine);
        export.add(export_to_excel);
        charts.add(threeFut);
        charts.add(fullCharts);
        charts.add(dec_funcs);
        charts.add(baskets);
        charts.add(stocks_delta_itam);

        menu.add(details);
        menu.add(settingWindow);
        menu.add(export);
        menu.add(charts);

        return menu;
    }
}
