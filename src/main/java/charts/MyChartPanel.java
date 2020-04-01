package charts;

import gui.MyGuiComps;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import java.awt.*;

public class MyChartPanel extends ChartPanel {

    private MyGuiComps.MyLabel highLbl;
    private MyGuiComps.MyLabel lowLbl;
    private MyGuiComps.MyLabel lastLbl;

    public MyChartPanel( JFreeChart chart, boolean includeTicker ) {
        super( chart );
        setLayout( null );

        if ( includeTicker ) {

            add( createLbl( "Last", Color.BLACK, 20, 0 ) );
            lastLbl = createLbl( "Last", Color.BLACK, 20, 20 );
            add( lastLbl );
            add( createLbl( "High", Color.BLACK, 70, 0 ) );
            highLbl = createLbl( "High", Color.BLACK, 70, 20 );
            add( highLbl );
            add( createLbl( "Low", Color.BLACK, 120, 0 ) );
            lowLbl = createLbl( "Low", Color.BLACK, 120, 20 );
            add( lowLbl );

        }
    }

    // Create basic lbl
    public MyGuiComps.MyLabel createLbl( String name, Color color, int x, int y ) {
        final MyGuiComps.MyLabel lbl = new MyGuiComps.MyLabel( name );
        lbl.setBounds( x, y, 50, 50 );
        lbl.setFont( new Font( "Arial", Font.BOLD, 14 ) );
        return lbl;
    }

    public MyGuiComps.MyLabel getHighLbl() {
        return highLbl;
    }

    public MyGuiComps.MyLabel getLowLbl() {
        return lowLbl;
    }

    public MyGuiComps.MyLabel getLastLbl() {
        return lastLbl;
    }

}
