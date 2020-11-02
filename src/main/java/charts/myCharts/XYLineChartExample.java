package charts.myCharts;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.util.Random;

/**
 * @author imssbora
 */
public class XYLineChartExample extends JFrame {
    private static final long serialVersionUID = 6294689542092367723L;

    public XYLineChartExample(String title) {
        super(title);

        // Create dataset
        XYDataset dataset = createDataset();

        // Create chart
        JFreeChart chart = ChartFactory.createXYLineChart(
                "XY Line Chart Example",
                "X-Axis",
                "Y-Axis",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        // Create Panel
        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            XYLineChartExample example = new XYLineChartExample("XY Chart Example | BORAJI.COM");
            example.setSize(800, 400);
            example.setLocationRelativeTo(null);
            example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            example.setVisible(true);
        });
    }

    private XYDataset createDataset() {
        XYSeriesCollection dataset = new XYSeriesCollection();

        XYSeries series = new XYSeries("Y = X + 2");

        for (int i = 0; i < 200000; i++) {

            double val = new Random().nextDouble() * 1000;

            series.add(i, val);
        }


        new Thread(() -> {

            for (int i = 0; i < series.getItemCount(); i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                series.remove(0);
            }

        }).start();

        //Add series to dataset
        dataset.addSeries(series);

        return dataset;
    }
}