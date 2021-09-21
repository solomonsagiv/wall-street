package gui.index;

import gui.panels.IMyPanel;
import gui.popupsFactory.PopupsMenuFactory;
import locals.Themes;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.INDEX_CLIENT_OBJECT;
import serverObjects.indexObjects.Spx;
import threads.MyThread;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class IndexPanel extends JPanel implements IMyPanel {

    public static void main(String[] args) {
        IndexWindow indexPanel = new IndexWindow("Spx", Spx.getInstance());
    }

    // Ticker
    TickerPanel tickerPanel;

    // Races
    CountersPanel countersPanel;

    // Exp
    ExpSumPanel expPanel;

    // Basket panel
    BasketsPanel basketsPanel;

    int height = 250;

    Color backGround = Themes.GREY_LIGHT;

    INDEX_CLIENT_OBJECT client;

    private Updater updater;

    public IndexPanel(INDEX_CLIENT_OBJECT client) {
        this.client = client;
        client.getExps();

        init();
        initListeners();

        // Updater
        getUpdater().getHandler().start();

    }

    private void initListeners() {
        // Right click
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                if (event.getModifiers() == MouseEvent.BUTTON3_MASK) {
                    showPopUpMenu(event);
                }
            }
        });
    }

    public void showPopUpMenu(MouseEvent event) {
        JPopupMenu menu = PopupsMenuFactory.indexPanel(client);
        // Show the menu
        menu.show(event.getComponent(), event.getX(), event.getY());
    }

    private void init() {

        setLayout(null);
        setBounds(0, 0, 0, height);

        // ---------- Ticker section ---------- //
        tickerPanel = new TickerPanel(client);
        tickerPanel.setXY(0, 0);
        add(tickerPanel);

        // ---------- Basket panel -------------//
        basketsPanel = new BasketsPanel(client);
        basketsPanel.setXY(tickerPanel.getX() + tickerPanel.getWidth() + 1, tickerPanel.getY());
        add(basketsPanel);

        // ---------- Races and roll ---------- //
        countersPanel = new CountersPanel(client);
        countersPanel.setXY(basketsPanel.getX() + basketsPanel.getWidth() + 1, basketsPanel.getY());
        add(countersPanel);

        // --------------- Exp --------------- //
        expPanel = new ExpSumPanel(client);
        expPanel.setXY(countersPanel.getX() + countersPanel.getWidth() + 1, countersPanel.getX());
        add(expPanel);

    }

    public Updater getUpdater() {
        if (updater == null) {
            updater = new Updater(client);
        }
        return updater;
    }

    @Override
    public void updateText() {
        try {
            tickerPanel.updateText();
            countersPanel.updateText();
            basketsPanel.updateText();
            expPanel.updateText();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        getUpdater().close();
    }

    public class Updater extends MyThread implements Runnable {

        public Updater(BASE_CLIENT_OBJECT client) {
            super(client);
            setName("UPDATER");
        }

        @Override
        public void initRunnable() {
            setRunnable(this);
        }

        @Override
        public void run() {

            setRun(true);

            while (isRun()) {
                try {
                    // Sleep
                    Thread.sleep(500);

                    updateText();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }

        public void close() {
            setRun(false);
        }
    }

}