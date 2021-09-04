package gui.index;

import exp.E;
import exp.ExpStrings;
import gui.MyGuiComps;
import gui.panels.IMyPanel;
import serverObjects.BASE_CLIENT_OBJECT;

import javax.swing.*;

public class RacesPanel extends MyGuiComps.MyPanel implements IMyPanel {

    BASE_CLIENT_OBJECT client;

    MyGuiComps.MyPanel header;
    MyGuiComps.MyLabel headerLbl;
    MyGuiComps.MyPanel body;
    MyGuiComps.MyLabel futLbl;
    MyGuiComps.MyLabel indLbl;
    MyGuiComps.MyLabel bidAskCounterLbl;
    MyGuiComps.MyLabel fut_deltaLbl;

    MyGuiComps.MyTextField futField;
    MyGuiComps.MyTextField indField;
    MyGuiComps.MyTextField fut_delta_field;

    MyGuiComps.MyTextField bidAskCounterField;

    E q1;

    public RacesPanel(BASE_CLIENT_OBJECT client) {
        super();
        this.client = client;
        initsialize();
        q1 = (E) client.getExps().getExp(ExpStrings.q1);
    }

    private void initsialize() {
        setSize(90, 300);

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

        // Fut
        futLbl = new MyGuiComps.MyLabel("Fut");
        futLbl.setXY(0, 3);
        futLbl.setWidth(40);
        body.add(futLbl);

        futField = new MyGuiComps.MyTextField();
        futField.setXY(futLbl.getX() + futLbl.getWidth(), futLbl.getY());
        futField.setWidth(40);
        body.add(futField);

        // Ind
        indLbl = new MyGuiComps.MyLabel("Ind");
        indLbl.setWidth(40);
        indLbl.setXY(futLbl.getX(), futLbl.getY() + futLbl.getHeight() + 1);
        body.add(indLbl);

        indField = new MyGuiComps.MyTextField();
        indField.setWidth(40);
        indField.setXY(futField.getX(), futField.getY() + futField.getHeight() + 1);
        body.add(indField);

        // Bid Ask counter
        bidAskCounterLbl = new MyGuiComps.MyLabel("B/A");
        bidAskCounterLbl.setWidth(40);
        bidAskCounterLbl.setXY(indLbl.getX(), indLbl.getY() + indLbl.getHeight() + 1);
        body.add(bidAskCounterLbl);

        bidAskCounterField = new MyGuiComps.MyTextField();
        bidAskCounterField.setWidth(40);
        bidAskCounterField.setXY(bidAskCounterLbl.getX() + bidAskCounterLbl.getWidth(), bidAskCounterLbl.getY());
        body.add(bidAskCounterField);

        // Fut delta
        fut_deltaLbl = new MyGuiComps.MyLabel("Del");
        fut_deltaLbl.setWidth(40);
        fut_deltaLbl.setXY(bidAskCounterLbl.getX(), bidAskCounterLbl.getY() + bidAskCounterLbl.getHeight() + 1);
        body.add(fut_deltaLbl);

        fut_delta_field = new MyGuiComps.MyTextField();
        fut_delta_field.setWidth(40);
        fut_delta_field.setXY(fut_deltaLbl.getX() + fut_deltaLbl.getWidth(), fut_deltaLbl.getY());
        body.add(fut_delta_field);
    }

    @Override
    public void updateText() {
        try {
            futField.colorForge(client.getFutSum());
            indField.colorForge(client.getIndexSum());
            bidAskCounterField.colorForge(client.getIndexBidAskCounter());
            fut_delta_field.colorForge(q1.getDelta());
        } catch (Exception e ) {
            e.printStackTrace();
        }
    }
}
