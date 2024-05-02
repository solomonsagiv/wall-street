package arik;

import api.Manifest;
import arik.alerts.ArikAlgoAlert;
import arik.alerts.ArikPositionsAlert;
import arik.alerts.Jibe_Positions_Algo;
import arik.dataHandler.DataHandler;
import arik.grabdata.ArikGrabData;
import arik.locals.Emojis;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.request.SendMessage;
import dataBase.mySql.MySql;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

//import com.pengrad.telegrambot.TelegramBotAdapter;

public class Arik {

    public static boolean allow_trading = false;

    public static final boolean EVERYONE = false;

    public static int sagivID = 365117561;
    private static Arik arik;
    private ArikRunner arikRunner;
    private boolean running = false;
    private TelegramBot bot;
    private DataHandler dataHandler;

    private long updateId = 0;

    public static ArrayList<Long> accounts = new ArrayList<>();
    public static ArrayList<Long> slo = new ArrayList<>();

    private Arik() {

        bot = new TelegramBot("400524449:AAE1nFANVNd6Vlj44DKhQLD0fdtlE7MZFj0");
    }

    public static void main(String[] args) {
        Manifest.POOL_SIZE = 1;
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

        ArrayList<ArikAlgoAlert> algo_list = new ArrayList<>();

        try {
            ResultSet rs = MySql.Queries.get_arik_sessions(MySql.JIBE_PROD_CONNECTION);
            while (true) {
                try {
                    if (!rs.next()) break;
                    int session_id = rs.getInt("session_id");
                    String des = rs.getString("desc");
                    String stock_name = rs.getString("stock_name");

                    algo_list.add(new Jibe_Positions_Algo(10000000, session_id, stock_name, des));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        } catch (Exception e) {
            Arik.getInstance().sendErrorMessage(e);
            e.printStackTrace();
        }
        return algo_list;
    }

    private void position_alerts() {
        ArikPositionsAlert arikPositionsAlert = new ArikPositionsAlert(getArikAlgoAlerts());
        arikPositionsAlert.getHandler().start();
    }


    public static void load_from_db() throws Exception {
        ResultSet rs = MySql.select("select * from sagiv.arik_accounts;", MySql.JIBE_PROD_CONNECTION);
        while (true) {
            try {
                if (!rs.next()) break;
                long id = rs.getLong("id");
                boolean notification = rs.getBoolean("notification");

                if (id > 100) {
                    accounts.add(id);

//                 Slo accounts
                    if (notification) {
                        slo.add(id);
                    }
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
            for (long person : slo) {
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
                for (long account : accounts) {
                    sendMessage(account, text, null);
                }
            } else {
                for (long account : slo) {
                    sendMessage(account, text, null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Send message
    public void sendMessage(long id, String text, Keyboard keyBoard) {
        if (keyBoard != null) {
            getBot().execute(new SendMessage(id, text).replyMarkup(keyBoard));
            updateId += 1;
        } else {
            getBot().execute(new SendMessage(id, text));
            updateId += 1;
        }
    }

    // ----------- Getters and Setters ---------- //
    public long getUpdateId() {
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
