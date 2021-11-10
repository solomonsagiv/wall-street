package arik.window;

import arik.Arik;
import gui.MyGuiComps;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ArikMainPanel extends MyGuiComps.MyPanel {

    MyGuiComps.MyLabel header;
    MyGuiComps.MyButton startBtn;
    JScrollPane scrollPane;
    public static JTextArea textArea;

    public ArikMainPanel() {
        super();
        initListeners();
    }

    private void initListeners() {
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Arik.getInstance().start();
            }
        });
    }

    @Override
    protected void init() {
        super.init();

        // Header
        header = new MyGuiComps.MyLabel("Arik");
        header.setXY(5, 5);
        header.setFont(header.getFont().deriveFont(20f));
        add(header);

        // Start btn
        startBtn = new MyGuiComps.MyButton("Start");
        startBtn.setXY(header.getX(), header.getY() + header.getHeight() + 5);
        startBtn.setWidth(100);
        startBtn.setHeight(100);
        add(startBtn);

        // Status scroll pane
        textArea = new JTextArea();
        scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(startBtn.getX() +startBtn.getWidth() + 5, header.getY(), 300, 500);
        add(scrollPane);

    }
}
