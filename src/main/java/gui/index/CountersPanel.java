package gui.index;

import exp.E;
import exp.ExpStrings;
import gui.MyGuiComps;
import gui.panels.IMyPanel;
import serverObjects.BASE_CLIENT_OBJECT;
import javax.swing.*;

public class CountersPanel extends MyGuiComps.MyPanel implements IMyPanel {

    BASE_CLIENT_OBJECT client;

    MyGuiComps.MyPanel header;
    MyGuiComps.MyLabel headerLbl;
    MyGuiComps.MyPanel body;
    MyGuiComps.MyLabel bidAskCounterLbl;
    MyGuiComps.MyLabel fut_deltaLbl;
    MyGuiComps.MyTextField fut_delta_field;
    MyGuiComps.MyTextField bidAskCounterField;

    E q1;

    public CountersPanel(BASE_CLIENT_OBJECT client) {
        super();
        this.client = client;
        initsialize();
        q1 = (E) client.getExps().getExp(ExpStrings.q1);
    }

    private void initsialize() {
        setSize(100, 300);

        // ------ Head ------ //
        header = new MyGuiComps.MyPanel();
        header.setSize(90, 25);
        header.setXY(0, 0);
        add(header);

        headerLbl = new MyGuiComps.MyLabel("Races", true);
        headerLbl.setHorizontalAlignment(JLabel.CENTER);
        header.add(headerLbl);

        // ------ Body ------ //
        body = new MyGuiComps.MyPanel();
        body.setXY(0, header.getY() + header.getHeight() + 1);
        body.setSize(90, 300);
        add(body);

        // Bid Ask counter
        bidAskCounterLbl = new MyGuiComps.MyLabel("B/A");
        bidAskCounterLbl.setWidth(40);
        bidAskCounterLbl.setXY(0, 3);
        body.add(bidAskCounterLbl);

        bidAskCounterField = new MyGuiComps.MyTextField();
        bidAskCounterField.setWidth(50);
        bidAskCounterField.setXY(bidAskCounterLbl.getX() + bidAskCounterLbl.getWidth(), bidAskCounterLbl.getY());
        body.add(bidAskCounterField);

        // Fut delta
        fut_deltaLbl = new MyGuiComps.MyLabel("Del");
        fut_deltaLbl.setWidth(40);
        fut_deltaLbl.setXY(bidAskCounterLbl.getX(), bidAskCounterLbl.getY() + bidAskCounterLbl.getHeight() + 1);
        body.add(fut_deltaLbl);

        fut_delta_field = new MyGuiComps.MyTextField();
        fut_delta_field.setWidth(50);
        fut_delta_field.setXY(fut_deltaLbl.getX() + fut_deltaLbl.getWidth(), fut_deltaLbl.getY());
        body.add(fut_delta_field);
    }

    @Override
    public void updateText() {
        try {
            bidAskCounterField.colorForge(client.getIndexBidAskCounter());
            fut_delta_field.colorForge(q1.getDelta());
        } catch (Exception e ) {
            e.printStackTrace();
        }
    }
}
