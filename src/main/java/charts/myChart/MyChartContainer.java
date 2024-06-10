package charts.myChart;

import api.Manifest;
import charts.MyChartPanel;
import charts.timeSeries.MyTimeSeries;
import gui.MyGuiComps;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import serverObjects.BASE_CLIENT_OBJECT;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Field;

public class MyChartContainer extends MyGuiComps.MyFrame {

    private static final long serialVersionUID = 1L;

    // Index series array
    MyChart[] charts;

    BASE_CLIENT_OBJECT client;
    String name;

    public MyChartContainer(BASE_CLIENT_OBJECT client, MyChart[] charts, String name) {
        super(name, client);
        this.charts = charts;
        this.client = client;
        this.name = name;

        // Layout
        setLayout(new GridLayout(charts.length, 0));

        // Load data
        if (Manifest.DB) {
            load_data();
        }

        // Append charts
        appendCharts();

    }

    @Override
    public String getName() {
        return name;
    }


    @Override
    public void initialize() {
        // Load bounds
        loadBounds();

        // On Close
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onClose(e);
            }
        });

    }

    private void load_data() {
        // Load each serie
        for (MyChart chart : charts) {
            for (MyTimeSeries serie : chart.getSeries()) {
//                new Thread(() -> {
                    try {
//                        Thread.sleep(1000);
                        serie.load_data();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//
//                }).start();
            }
        }

        // Check load
        check_load();
    }

    private void check_load() {

        while (true) {
            try {
                //Sleep
                Thread.sleep(1000);

                boolean load = true;

                for (MyChart chart : charts) {
                    for (MyTimeSeries serie : chart.getSeries()) {
                        if (!serie.isLoad()) {
                            load = false;
                        }
                        System.out.println(serie.getName() + " Load " + serie.isLoad());
                    }
                }

                // Is load
                if (load) {
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void create() {
        pack();
        setVisible(true);
    }

    private void appendCharts() {
        for (MyChart myChart : charts) {
            MyChartPanel chartPanel = new MyChartPanel(myChart.chart, myChart.getProps().getBool(ChartPropsEnum.IS_INCLUDE_TICKER));
            myChart.chartPanel = chartPanel;

            initProps(chartPanel);
            addPan(chartPanel);
            mouseListener(chartPanel, myChart, this);
            mouseWheel(chartPanel, myChart);
            add(chartPanel);
        }
    }

    private void initProps(MyChartPanel chartPanel) {
        chartPanel.setMouseZoomable(false);
        chartPanel.setMouseWheelEnabled(false);
        chartPanel.setDomainZoomable(false);
        chartPanel.setRangeZoomable(false);
        chartPanel.setZoomTriggerDistance(Integer.MAX_VALUE);
        chartPanel.setFillZoomRectangle(false);
        chartPanel.setZoomAroundAnchor(false);
    }

    private void mouseWheel(MyChartPanel chartPanel, MyChart myChart) {
        chartPanel.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                myChart.getUpdater().updateChartRange();
            }
        });
    }

    private void mouseListener(MyChartPanel chartPanel, MyChart myChart, MyChartContainer container) {

        // 2 Clicks
        chartPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2) {
                    DateAxis axis = (DateAxis) myChart.plot.getDomainAxis();
                    NumberAxis rangeAxis = (NumberAxis) myChart.plot.getRangeAxis();

                    rangeAxis.setAutoRange(true);
                    axis.setAutoRange(true);
                }
            }
        });

        // Mouse release
        chartPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                System.out.println("Mouse released");
                myChart.getUpdater().updateChartRange();
            }
        });

        chartPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getButton() == MouseEvent.BUTTON3) {
                    new ChartFilterWindow("Filter", client, myChart, container);
                }
            }
        });
    }

    private void addPan(MyChartPanel chartPanel) {
        try {
            Field mask = ChartPanel.class.getDeclaredField("panMask");
            mask.setAccessible(true);
            mask.set(chartPanel, 0);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void onClose(WindowEvent e) {
        // Update bound to database
        insetOrUpdateBounds();

        for (MyChart myChart : charts) {
//            myChart.getUpdater().getHandler().close();
            myChart.close();
        }
        dispose();
    }

    @Override
    public void initListeners() {

    }



}