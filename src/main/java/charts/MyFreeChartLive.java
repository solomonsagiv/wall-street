package charts;

import arik.Arik;
import dataBase.mySql.myBaseTables.MyBoundsTable;
import dataBase.mySql.mySqlComps.TablesEnum;
import locals.Themes;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import serverObjects.BASE_CLIENT_OBJECT;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;

public class MyFreeChartLive extends JFrame {

    private static final long serialVersionUID = 1L;

    // Index series array
    XYPlot plot;
    MySingleFreeChartLive[] singleFreeCharts;

    BASE_CLIENT_OBJECT client;
    String name;

    public MyFreeChartLive(MySingleFreeChartLive[] singleFreeCharts, BASE_CLIENT_OBJECT client, String name) {
        this.singleFreeCharts = singleFreeCharts;
        this.client = client;
        this.name = name;
        init();
    }

    @Override
    public String getName() {
        return name;
    }

    private void init() {

        // Load bounds
        loadBounds();

        // Background color
        setBackground(Themes.GREY_LIGHT);

        // On Close
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onClose(e);
            }
        });

        // Set layout
        setLayout(new GridLayout(singleFreeCharts.length, 0));

        // Append charts
        appendCharts();

    }

    private void appendCharts() {
        // Append charts
        for (MySingleFreeChartLive mySingleFreeChart : singleFreeCharts) {
            JFreeChart chart = mySingleFreeChart.getChart();
            MyChartPanel chartPanel = new MyChartPanel(chart, mySingleFreeChart.isIncludeTickerData());
            chartPanel.setBorder(null);
            mySingleFreeChart.setChartPanel(chartPanel);

            add(chartPanel);
        }
    }

    private void loadBounds() {
        new Thread(() -> {
            try {
                ResultSet rs = ((MyBoundsTable) client.getTablesHandler().getTable(TablesEnum.BOUNDS)).getBound(client.getName(), getName());

                int width = 300, height = 500, x = 100, y = 100;

                while (rs.next()) {
                    x = rs.getInt("x");
                    y = rs.getInt("y");
                    width = rs.getInt("width");
                    height = rs.getInt("height");
                }

                setPreferredSize(new Dimension(width, height));
                setBounds(x, y, width, height);
            } catch (Exception e) {
                e.printStackTrace();
                Arik.getInstance().sendErrorMessage(e);
            }
        }).start();
    }

    public void onClose(WindowEvent e) {

        // Update bounds
        new Thread(() -> {
            ((MyBoundsTable) client.getTablesHandler().getTable(TablesEnum.BOUNDS)).updateBoundOrCreateNewOne(client.getName(), name, getX(), getY(), getWidth(), getHeight());
        }).start();

        // Close updaters
        for (MySingleFreeChartLive mySingleFreeChart : singleFreeCharts) {
            mySingleFreeChart.closeUpdate();
        }

        dispose();
    }

}
