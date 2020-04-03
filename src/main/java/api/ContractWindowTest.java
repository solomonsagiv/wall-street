package api;

import gui.MyGuiComps;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ContractWindowTest extends MyGuiComps.MyFrame {

    public static void main(String[] args) {
        ContractWindowTest contractWindowTest = new ContractWindowTest("Contracts");

    }

    // Variables
    MyGuiComps.MyLabel spxLbl;
    public static MyGuiComps.MyTextField spxQuarterField = new MyGuiComps.MyTextField();
    public static MyGuiComps.MyTextField spxQuarterFarField = new MyGuiComps.MyTextField();


    MyGuiComps.MyLabel ndxLbl = new MyGuiComps.MyLabel("Ndx");
    public static MyGuiComps.MyTextField ndxQuarterField = new MyGuiComps.MyTextField();
    public static MyGuiComps.MyTextField ndxQuarterFarField = new MyGuiComps.MyTextField();


    // Constructor
    public ContractWindowTest( String title ) throws HeadlessException {
        super(title);

        startDownloader();
    }

    private void startDownloader() {
        Downloader downloader = Downloader.getInstance();
        downloader.start();
    }

    @Override
    public void onClose() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // TODO
            }
        });
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initialize() {

        // This
        setBounds(200, 200, 200, 200);
        setPreferredSize(new Dimension(200, 200));

        // Spx
        spxLbl = new MyGuiComps.MyLabel("Spx");
        spxLbl.setXY(10, 10);
        add(spxLbl);

        spxQuarterField = new MyGuiComps.MyTextField();
        spxQuarterField.setXY(10, 40);
        add(spxQuarterField);

        spxQuarterFarField = new MyGuiComps.MyTextField();
        spxQuarterFarField.setXY(spxQuarterField.getX(), spxQuarterField.getY() + spxQuarterField.getHeight() + 5);
        add(spxQuarterFarField);


        // Ndx
        ndxLbl = new MyGuiComps.MyLabel("Ndx");
        ndxLbl.setXY(80, 10);
        add(ndxLbl);

        ndxQuarterField = new MyGuiComps.MyTextField();
        ndxQuarterField.setXY(80, 40);
        add(ndxQuarterField);

        ndxQuarterFarField = new MyGuiComps.MyTextField();
        ndxQuarterFarField.setXY(ndxQuarterField.getX(), ndxQuarterField.getY() + ndxQuarterField.getHeight() + 5);
        add(ndxQuarterFarField);

    }
}
