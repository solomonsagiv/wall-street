package arik;

import arik.locals.Emojis;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.request.SendMessage;

public class Arik {

    public static final int nivosID = 540675119;
    public static int sagivID = 365117561;
    public static int yosiID = 948009529;
    public static int royID = 513323078;
    public static int ronenID = 948009529;
    private static Arik arik;
    private ArikRunner arikRunner;
    private boolean running = false;
    private TelegramBot bot;

    private int updateId = 0;

    private int[] accounts = {sagivID, yosiID, ronenID, royID};
    private int[] accountsForPositions = {nivosID};

    private Arik() {
        bot = TelegramBotAdapter.build("400524449:AAE4dPbl22dfI9lB1r17W4ivqz2lc4C1xUY");
    }

    public static void main(String[] args) {
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
            arikRunner = new ArikRunner(this);
            arikRunner.start();
            running = true;
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
    public void sendMessageToEveryOne(String text) {
        try {
            for (int account : accounts) {
                sendMessage(account, text, null);
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

}
