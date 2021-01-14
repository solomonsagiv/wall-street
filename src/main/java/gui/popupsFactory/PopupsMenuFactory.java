package gui.popupsFactory;

import Excel.MyExcelWriter;
import charts.myCharts.*;
import dataBase.mySql.mySqlComps.TablesEnum;
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


        JMenuItem threeFut = new JMenuItem( "Futures real time" );
        threeFut.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent e ) {
                FuturesChart chart = new FuturesChart( client );
                chart.createChart( );
            }
        } );


        JMenuItem indexCounter_index_item = new JMenuItem( "E2 / Ind counuter/ Ind ( 2 )" );
        indexCounter_index_item.addActionListener( new ActionListener( ) {
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
                // todo
            }
        } );

        JMenuItem export_to_excel = new JMenuItem( "Export to excel" );
        export_to_excel.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent e ) {
                try {
                    MyExcelWriter writer = new MyExcelWriter( );
                    writer.export( client.getDataTable( ) );
                } catch ( Exception exception ) {
                    JOptionPane.showMessageDialog( null, exception.getStackTrace() );
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
        charts.add( fourLineChart );
        charts.add( opAvg15 );
        charts.add( opAvg );
        charts.add( indexCounter_index_item );
        charts.add( marginChart );
        charts.add( indQuarterOpAvg15Future );
        charts.add( fiveLinesChart );
        charts.add( quarter_index_item );
        charts.add( quarter_quarterFar_index_item );

        menu.add( details );
        menu.add( settingWindow );
        menu.add( export );
        menu.add( charts );

        return menu;
    }
}
