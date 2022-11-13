package arik;

import api.Manifest;
import arik.alerts.ArikAlgoAlert;
import arik.alerts.ArikPositionsAlert;
import arik.alerts.Jibe_Positions_Algo;
import arik.dataHandler.DataHandler;
import arik.grabdata.ArikGrabData;
import arik.locals.Emojis;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.request.SendMessage;
import dataBase.mySql.MySql;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Arik {

    public static final boolean EVERYONE = false;

    public static int sagivID = 365117561;
    private static Arik arik;
    private ArikRunner arikRunner;
    private boolean running = false;
    private TelegramBot bot;
    private DataHandler dataHandler;

    private int updateId = 0;

    public static ArrayList<Integer> accounts = new ArrayList<>();
    public static ArrayList<Integer> slo = new ArrayList<>();

    private Arik() {
        bot = TelegramBotAdapter.build("400524449:AAE1nFANVNd6Vlj44DKhQLD0fdtlE7MZFj0");
    }

    public static void main(String[] args) {
        Manifest.POOL_SIZE = 5;
        Arik.getInstance().start();

    }

    // Get instance
    public static Arik getInstance() {
        if (arik == null) {
            arik = new Arik();
        }
        return arik;
    }

    public void start() {
        if (!running) {

            try {
                // Load from database
                load_from_db();

                // Data objects
                init_data_handler();

                // Arik messages runner
                arik_runner();

                // Arik data grabber
                data_grabber();

                // Position alerts
                position_alerts();
                
                System.out.println("Running");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
    }

    private void init_data_handler() {
        dataHandler = new DataHandler();
    }

    private void arik_runner() {
        arikRunner = new ArikRunner(this);
        arikRunner.start();
    }

    private void data_grabber() {
        ArikGrabData arikGrabData = new ArikGrabData();
        arikGrabData.getHandler().start();
        running = true;
    }

    private ArrayList<ArikAlgoAlert> getArikAlgoAlerts() {
        // Positions alert
        ArrayList<ArikAlgoAlert> algo_list = new ArrayList<>();
        algo_list.add(new Jibe_Positions_Algo(10000000));
        return algo_list;
    }

    private void position_alerts() {
        ArikPositionsAlert arikPositionsAlert = new ArikPositionsAlert(getArikAlgoAlerts());
        arikPositionsAlert.getHandler().start();
    }


    public static void load_from_db() throws Exception {
        ResultSet rs = MySql.select("select * from sagiv.arik_accounts;");
        while (true) {
            try {
                if (!rs.next()) break;
                int id = rs.getInt("id");
                String name = rs.getString("name");

                accounts.add(id);

//                 Slo accounts
                if (name.toLowerCase().equals("sagiv") ||
                        name.toLowerCase().equals("yogi") ||
                        name.toLowerCase().equals("moti") ||
                        name.toLowerCase().equals("guru")) {
                    slo.add(id);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void close() {
        arik = null;
        bot = null;
        arikRunner.close();
        running = false;
    }

    public void sendMessage(String text) {
        getBot().execute(new SendMessage(sagivID, text));
        updateId += 1;
    }

    public void sendErrorMessage(Exception e) {
        String text = e.getMessage() + "\n" + e.getCause();
        getBot().execute(new SendMessage(sagivID, text));
        updateId += 1;
    }

    public void sendMessage(String action, boolean success) {
        String text = action + " " + " success " + Emojis.check_mark;
        // Success
        if (success) {
            text += " " + " success " + Emojis.check_mark;
            sendMessage(text);
        } else {
            text += " " + " failed " + Emojis.stop;
            sendMessage(text);
        }
    }

    // Send message
    public void sendMessage(Update update, String text, Keyboard keyBoard) {
        if (keyBoard != null) {
            getBot().execute(new SendMessage(update.message().from().id(), text).replyMarkup(keyBoard));
            updateId = update.updateId() + 1;
        } else {
            getBot().execute(new SendMessage(update.message().from().id(), text));
            updateId = update.updateId() + 1;
        }
    }

    // Send message
    public void sendMessageToSlo(String text) {
        try {
            for (int person : slo) {
                sendMessage(person, text, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Send message
    public void sendMessageToEveryOne(String text) {
        try {
            if (EVERYONE) {
                for (int account : accounts) {
                    sendMessage(account, text, null);
                }
            } else {
                for (int account : slo) {
                    sendMessage(account, text, null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Send message
    public void sendMessage(int id, String text, Keyboard keyBoard) {
        if (keyBoard != null) {
            getBot().execute(new SendMessage(id, text).replyMarkup(keyBoard));
            updateId += 1;
        } else {
            getBot().execute(new SendMessage(id, text));
            updateId += 1;
        }
    }
    
    // ----------- Getters and Setters ---------- //
    public int getUpdateId() {
        return updateId;
    }

    public void setUpdateId(int updateId) {
        this.updateId = updateId;
    }

    public ArikRunner getArikRunner() {
        return arikRunner;
    }

    public void setArikRunner(ArikRunner arikRunner) {
        this.arikRunner = arikRunner;
    }

    public TelegramBot getBot() {
        return bot;
    }

    public void setBot(TelegramBot bot) {
        this.bot = bot;
    }

    public DataHandler getDataHandler() {
        return dataHandler;
    }

    public void setDataHandler(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }
}
