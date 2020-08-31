package gui.popupsFactory;

import basketFinder.window.BasketWindow;
import charts.myCharts.*;
import charts.myCharts.stockCharts.MonthCounter_IndexCounter_Index_Chart;
import charts.myCharts.stockCharts.Month_Index_Live_Chart;
import dataBase.mySql.mySqlComps.TablesEnum;
import gui.DetailsWindow;
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
                Month_Index_Live_Chart chart = new Month_Index_Live_Chart( client );
                chart.createChart( );
            }
        } );

        JMenuItem indexBidAskCounterItem = new JMenuItem( "Counters - Index" );
        indexBidAskCounterItem.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent e ) {
                MonthCounter_IndexCounter_Index_Chart chart = new MonthCounter_IndexCounter_Index_Chart( client );
                try {
                    chart.createChart( );
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
                new PositionsWindow( client, client.getExps( ).getPositionCalculator( ).getPositions( ) );
            }
        } );

        export.add( exportSumLine );

        charts.add( contractIndexRealTime );
        charts.add( indexBidAskCounterItem );

        menu.add( details );
        menu.add( settingWindow );
        menu.add( export );
        menu.add( charts );
        menu.add( fullOptionsTable );
        menu.add( optionsPosition );

        return menu;
    }

    public static JPopupMenu indexPanel( INDEX_CLIENT_OBJECT client ) {

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

        JMenuItem baskets = new JMenuItem( "Baskets" );
        baskets.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent e ) {
                new BasketWindow( "Baskets " + client.getName( ), client );
            }
        } );

        JMenuItem opAvg = new JMenuItem( "OpAvg Index B/A FutFarCounter Index" );
        opAvg.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent e ) {
                OpAvgFuture_E2_IndexCounter_Index_Chart chart = new OpAvgFuture_E2_IndexCounter_Index_Chart( client );
                try {
                    chart.createChart( );
                } catch ( CloneNotSupportedException cloneNotSupportedException ) {
                    cloneNotSupportedException.printStackTrace( );
                }
            }
        } );

        JMenuItem fourLineChart = new JMenuItem( "4 lines" );
        fourLineChart.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent e ) {
                FourLineChart chart = new FourLineChart( client );
                chart.createChart( );
            }
        } );

        JMenuItem opAvg15 = new JMenuItem( "OpAvg 15 Index B/A FutFarCounter Index" );
        opAvg15.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent e ) {
                OpAvg15Future_E2_IndexCounter_Index_Chart chart = new OpAvg15Future_E2_IndexCounter_Index_Chart( client );
                try {
                    chart.createChart( );
                } catch ( CloneNotSupportedException cloneNotSupportedException ) {
                    cloneNotSupportedException.printStackTrace( );
                }
            }
        } );

        JMenuItem fiveLinesChart = new JMenuItem( "5 lines" );
        fiveLinesChart.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent e ) {
                FiveLineChart chart = new FiveLineChart( client );
                chart.createChart( );
            }
        } );

        JMenuItem indQuarterOpAvg15Future = new JMenuItem( "indQuarterOpAvg15Future" );
        indQuarterOpAvg15Future.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent e ) {
                IndexVsQuarterVSOpAvg15LiveChart chart = new IndexVsQuarterVSOpAvg15LiveChart( client );
                chart.createChart( );
            }
        } );

        JMenuItem e2_indexCounter_index_item2 = new JMenuItem( "E2 / Ind counuter/ Ind ( 2 )" );
        e2_indexCounter_index_item2.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent e ) {
                IndexCounter_Index_Chart chart = new IndexCounter_Index_Chart( client );
                try {
                    chart.createChart( );
                } catch ( CloneNotSupportedException cloneNotSupportedException ) {
                    cloneNotSupportedException.printStackTrace( );
                }
            }
        } );

        JMenuItem quarter_index_item = new JMenuItem( "E1" );
        quarter_index_item.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent e ) {
                IndexVsQuarterLiveChart chart = new IndexVsQuarterLiveChart( client );
                chart.createChart( );
            }
        } );

        JMenuItem quarter_quarterFar_index_item = new JMenuItem( "E1 / E2" );
        quarter_quarterFar_index_item.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent e ) {
                IndexVsQuarterQuarterFarLiveChart chart = new IndexVsQuarterQuarterFarLiveChart( client );
                chart.createChart( );
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
                new PositionsWindow( client, client.getExps( ).getPositionCalculator( ).getPositions( ) );
            }
        } );

        export.add( exportSumLine );

        charts.add( fourLineChart );
        charts.add( opAvg15 );
        charts.add( opAvg );
        charts.add( e2_indexCounter_index_item2 );
        charts.add( indQuarterOpAvg15Future );
        charts.add( fiveLinesChart );
        charts.add( quarter_index_item );
        charts.add( quarter_quarterFar_index_item );

        menu.add( details );
        menu.add( settingWindow );
        menu.add( baskets );
        menu.add( export );
        menu.add( charts );
        menu.add( fullOptionsTable );
        menu.add( optionsPosition );

        return menu;
    }
}
