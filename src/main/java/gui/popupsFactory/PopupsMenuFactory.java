package gui.popupsFactory;

import charts.myCharts.*;
import charts.myCharts.stockCharts.MonthCounter_IndexCounter_Index_Chart;
import charts.myCharts.stockCharts.Month_Index_Live_Chart;
import dataBase.mySql.mySqlComps.TablesEnum;
import gui.DetailsWindow;
import options.OptionsWindow;
import options.fullOptions.FullOptionsWindow;
import options.fullOptions.PositionsWindow;
import serverObjects.indexObjects.INDEX_CLIENT_OBJECT;
import serverObjects.stockObjects.STOCK_OBJECT;
import setting.clientSetting.SettingWindow;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PopupsMenuFactory {

    public static JPopupMenu stockPanel( STOCK_OBJECT client ) {
        // Main menu
        JPopupMenu menu = new JPopupMenu( );

        // Charts menu
        JMenu charts = new JMenu( "Charts" );

        // Setting
        JMenuItem settingWindow = new JMenuItem( "Setting" );
        settingWindow.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent e ) {
                new SettingWindow( client.getName( ), client );
            }
        } );

        JMenuItem contractIndexRealTime = new JMenuItem( "Month - Index live" );
        contractIndexRealTime.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent e ) {
                Month_Index_Live_Chart chart = new Month_Index_Live_Chart(client);
                chart.createChart();
            }
        } );

        JMenuItem indexBidAskCounterItem = new JMenuItem( "Counters - Index" );
        indexBidAskCounterItem.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent e ) {
                MonthCounter_IndexCounter_Index_Chart chart = new MonthCounter_IndexCounter_Index_Chart(client);
                try {
                    chart.createChart();
                } catch ( CloneNotSupportedException cloneNotSupportedException ) {
                    cloneNotSupportedException.printStackTrace( );
                }
            }
        } );

        // Export menu
        JMenu export = new JMenu( "Export" );

        JMenuItem exportSumLine = new JMenuItem( "Export sum line" );
        exportSumLine.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent e ) {
                client.getTablesHandler( ).getTable( TablesEnum.SUM ).insert( );
            }
        } );

        JMenuItem details = new JMenuItem( "Details" );
        details.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent e ) {
                DetailsWindow detailsWindow = new DetailsWindow( client );
                detailsWindow.frame.setVisible( true );
            }
        } );

        JMenuItem optionsCounter = new JMenuItem( "Options counter table" );
        optionsCounter.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent e ) {
                OptionsWindow window = new OptionsWindow( client, client.getOptionsHandler( ).getMainOptions( ) );
                window.frame.setVisible( true );
                window.startWindowUpdater( );
            }
        } );

        JMenuItem fullOptionsTable = new JMenuItem( "Full options table" );
        fullOptionsTable.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent e ) {
                new FullOptionsWindow( client );
            }
        } );

        JMenuItem optionsPosition = new JMenuItem( "Positions" );
        optionsPosition.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent e ) {
                new PositionsWindow( client, client.getOptionsHandler( ).getPositionCalculator( ).getPositions( ) );
            }
        } );

        export.add( exportSumLine );

        charts.add( contractIndexRealTime );
        charts.add(indexBidAskCounterItem);

        menu.add( details );
        menu.add( settingWindow );
        menu.add( export );
        menu.add( charts );
        menu.add( optionsCounter );
        menu.add( fullOptionsTable );
        menu.add( optionsPosition );

        return menu;
    }

    public static JPopupMenu indexPanel ( INDEX_CLIENT_OBJECT client ) {
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

        JMenuItem indexBidAskCounter_indexItem = new JMenuItem("Index B/A");
        indexBidAskCounter_indexItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IndexBidAskCounterIndexChart chart = new IndexBidAskCounterIndexChart(client);
                chart.createChart();
            }
        });

        JMenuItem indQuarterOpAvgFuture = new JMenuItem("indQuarterOpAvgFuture");
        indQuarterOpAvgFuture.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IndexVsQuarterVSOpAvgLiveChart chart = new IndexVsQuarterVSOpAvgLiveChart(client);
                chart.createChart();
            }
        });

        JMenuItem indQuarterOpAvg15Future = new JMenuItem("indQuarterOpAvg15Future");
        indQuarterOpAvg15Future.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IndexVsQuarterVSOpAvg15LiveChart chart = new IndexVsQuarterVSOpAvg15LiveChart(client);
                chart.createChart();
            }
        });


        JMenuItem e2_indexCounter_index_item = new JMenuItem("E2 / Ind counuter/ Ind");
        e2_indexCounter_index_item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                E2_IndexCounter_Index_Chart chart = new E2_IndexCounter_Index_Chart(client);
                try {
                    chart.createChart();
                } catch (CloneNotSupportedException cloneNotSupportedException) {
                    cloneNotSupportedException.printStackTrace();
                }
            }
        });

        JMenuItem e2BACounter_index = new JMenuItem("E2 B/A");
        e2BACounter_index.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FutureFarBidAskCounterIndexChart chart = new FutureFarBidAskCounterIndexChart(client);
                chart.createChart();
            }
        });

        JMenuItem quarter_index_item = new JMenuItem("E1");
        quarter_index_item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IndexVsQuarterLiveChart chart = new IndexVsQuarterLiveChart(client);
                chart.createChart();
            }
        });

        JMenuItem quarter_quarterFar_index_item = new JMenuItem("E1 / E2");
        quarter_quarterFar_index_item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IndexVsQuarterQuarterFarLiveChart chart = new IndexVsQuarterQuarterFarLiveChart(client);
                chart.createChart();
            }
        });

        // Export menu
        JMenu export = new JMenu("Export");

        JMenuItem exportSumLine = new JMenuItem("Export sum line");
        exportSumLine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.getTablesHandler().getTable(TablesEnum.SUM).insert();
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

        JMenuItem optionsCounter = new JMenuItem("Options counter table");
        optionsCounter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OptionsWindow window = new OptionsWindow(client, client.getOptionsHandler().getMainOptions());
                window.frame.setVisible(true);
                window.startWindowUpdater();
            }
        });

        JMenuItem fullOptionsTable = new JMenuItem("Full options table");
        fullOptionsTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FullOptionsWindow(client);
            }
        });

        JMenuItem optionsPosition = new JMenuItem("Positions");
        optionsPosition.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PositionsWindow(client, client.getOptionsHandler().getPositionCalculator().getPositions());
            }
        });

        export.add(exportSumLine);

        charts.add(indQuarterOpAvg15Future);
        charts.add(indQuarterOpAvgFuture);
        charts.add(e2_indexCounter_index_item);
        charts.add(e2BACounter_index);
        charts.add(quarter_index_item);
        charts.add(quarter_quarterFar_index_item);
        charts.add(indexBidAskCounter_indexItem);

        menu.add(details);
        menu.add(settingWindow);
        menu.add(export);
        menu.add(charts);
        menu.add(optionsCounter);
        menu.add(fullOptionsTable);
        menu.add(optionsPosition);

        return menu;
    }
}
