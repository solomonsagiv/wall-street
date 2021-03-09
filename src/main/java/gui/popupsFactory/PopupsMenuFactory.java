package gui.popupsFactory;

import charts.myCharts.*;
import gui.DetailsWindow;
import serverObjects.indexObjects.INDEX_CLIENT_OBJECT;
import setting.clientSetting.SettingWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PopupsMenuFactory {

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

        JMenuItem fullCharts = new JMenuItem( "Full charts" );
        fullCharts.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent e ) {
                FullCharts chart = new FullCharts( client );
                try {
                    chart.createChart( );
                } catch ( CloneNotSupportedException cloneNotSupportedException ) {
                    cloneNotSupportedException.printStackTrace( );
                }
            }
        } );

        JMenuItem marginChart = new JMenuItem( "Margin" );
        marginChart.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent e ) {
                BidAskMarginChart chart = new BidAskMarginChart( client );
                try {
                    chart.createChart( );
                } catch ( CloneNotSupportedException cloneNotSupportedException ) {
                    cloneNotSupportedException.printStackTrace( );
                }
            }
        } );

        JMenuItem opAvg15 = new JMenuItem( "OpAvg 15 -- Index B/A -- Ind races -- Index" );
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

        JMenuItem indQuarterOpAvg15Future = new JMenuItem( "indQuarterOpAvg15Future" );
        indQuarterOpAvg15Future.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent e ) {
                IndexVsQuarterVSOpAvg15LiveChart chart = new IndexVsQuarterVSOpAvg15LiveChart( client );
                chart.createChart( );
            }
        } );

        JMenuItem baskets = new JMenuItem( "Baskets" );
        baskets.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent actionEvent ) {
                if ( client.getBasketFinder( ) != null ) {
                    try {
                        Index_baskets_chart chart = new Index_baskets_chart( client );
                        chart.createChart( );
                    } catch ( CloneNotSupportedException e ) {
                        e.printStackTrace( );
                    }
                }
            }
        } );

        JMenuItem threeFut = new JMenuItem( "Futures real time" );
        threeFut.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent e ) {
                FuturesChart chart = new FuturesChart( client );
                chart.createChart( );
            }
        } );

        // Export menu
        JMenu export = new JMenu( "Export" );

        JMenuItem exportSumLine = new JMenuItem( "Export sum line" );
        exportSumLine.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent e ) {
                // todo
            }
        } );

        JMenuItem export_to_excel = new JMenuItem( "Export to excel" );
        export_to_excel.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent e ) {
                try {
                } catch ( Exception exception ) {
                    JOptionPane.showMessageDialog( null, exception.getStackTrace( ) );
                }
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

        export.add( exportSumLine );
        export.add( export_to_excel );
        charts.add( threeFut );
        charts.add( fullCharts );
        charts.add( baskets );
        charts.add( opAvg15 );
        charts.add( opAvg );
        charts.add( marginChart );
        charts.add( indQuarterOpAvg15Future );

        menu.add( details );
        menu.add( settingWindow );
        menu.add( export );
        menu.add( charts );

        return menu;
    }
}
