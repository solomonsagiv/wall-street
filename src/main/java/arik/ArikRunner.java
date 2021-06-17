package arik;

import arik.cases.ChooseAlertCase;
import arik.cases.DontKnowCaes;
import arik.cases.SpeedCase;
import arik.locals.Emojis;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.response.GetUpdatesResponse;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ArikRunner extends Thread {

    public static ArikCase statusCase;
    boolean run = true;
    int[] allowed = {365117561, 948009529, 513323078};
    int sagiv_id = 365117561;
    Arik arik;
    CasesHandler casesHandler;
    long date = 0;

    // Constructor
    public ArikRunner(Arik arik) {
        this.arik = arik;
        casesHandler = new CasesHandler();
        casesHandler.addCase(new ChooseAlertCase("/Alerts"));
        casesHandler.addCase(new SpeedCase("/Speed+-5000"));
//        casesHandler.addCase(new AccCase("/Acc+-0"));
//        casesHandler.addCase(new );
    }

    // Run
    public void run() {
        try {
            loop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        run = false;
    }

    // Loop
    private void loop() throws InterruptedException {
        int alert_count = 1;

        try {
            while (run) {
                // BackGround
                // Get updates
                getUpdates(alert_count);
                sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
            sleep(1000);
            loop();
        }
    }

    // Get upadtes
    private void getUpdates(int alert_count) {
        GetUpdatesResponse updatesResponse = arik.getBot()
                .execute(new GetUpdates().limit(1000).offset(arik.getUpdateId()).timeout(5));
        List<Update> updates = updatesResponse.updates();
        if (updates.size() > 0) {
            for (Update update : updates) {
                if (update.message().date() > date) {
                    this.date = update.message().date();
                    // Validate user
                    if (is_allowed(allowed, update.message().from().id())) {
                        try {

                            // Get text from user
                            String user_text = update.message().text();
                            System.out.println(user_text);

                            // Run cases
                            runCases(update);

                        } catch (Exception e) {
                            arik.sendMessage(update, e.getMessage(), null);
                        }
                    } else {
                        arik.sendMessage(update, "Fuck you", null);

                        // Notice me
                        arik.sendMessage(
                                "Someone try to talk with me " + "\n He said: " + update.message().text()
                                        + "\n His name is: " + update.message().from().firstName() + "\n" + "id: "
                                        + update.message().from().id()
                        );
                        break;
                    }
                }
            }
        }
    }

    public void runCases(Update update) {

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (ArikCase arikCase : casesHandler.getCases()) {

//            if (statusCase == arikCase) {
//                arikCase.doCase(update);
//                return;
//            }

            // Get the clean text
            String text = update.message().text().toLowerCase();

            if (arikCase.getName().toLowerCase().equals(text)) {
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        arikCase.doCase(update);
//                        statusCase = arikCase;
                    }
                });
                return;
            }
        }
        new DontKnowCaes("").doCase(update);
    }

    // Is allowed
    public boolean is_allowed(int[] allowed, int id) {
        for (int i : allowed) {
            if (id == i) {
                return true;
            }
        }
        return false;
    }

    // Kill all alerts
    public String kill_all_alerts() {
        return " Killed all the alerts " + Emojis.check_mark;
    }

    // Floor
    public double floor(double d, int zeros) {
        return Math.floor(d * zeros) / zeros;
    }

    // Str
    private String str(Object o) {
        return String.valueOf(o);
    }

}