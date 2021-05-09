package backGround;

import api.Manifest;
import arik.Arik;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.Spx;
import threads.MyThread;

import java.time.LocalTime;
import java.util.HashMap;

public class BackGroundHandler {

    public static BackGroundHandler instance;
    HashMap<String, BackRunner> backRunners = new HashMap<>();

    private BackGroundHandler() {
    }

    public static BackGroundHandler getInstance() {
        if (instance == null) {
            instance = new BackGroundHandler();
        }
        return instance;
    }

    public BackRunner createNewRunner(BASE_CLIENT_OBJECT client) {
        if (!backRunners.containsKey(client.getName())) {

            // Start new runner
            BackRunner backRunner = new BackRunner(client);
            backRunner.getHandler().start();

            // Append to map
            backRunners.put(client.getName(), backRunner);

            return backRunner;
        } else {
            return backRunners.get(client.getName());
        }
    }

    public void removeRunner(BASE_CLIENT_OBJECT client) {
        backRunners.remove(client.getName());
    }

    // Clients
    private class BackRunner extends MyThread implements Runnable {

        boolean run = true;
        double last_0 = 0;
        boolean runnersClosed = false;
        LocalTime now;

        int lastChangeCountToStart = 0;

        public BackRunner(BASE_CLIENT_OBJECT client) {
            super(client);
        }

        @Override
        public void run() {

            while (isRun()) {
                try {

                    // Sleep
                    Thread.sleep(2000);

                    now = LocalTime.now();

                    double last = client.getIndex();

                    if (last != last_0) {
                        last_0 = last;
                        lastChangeCountToStart++;
                    }

                    // Index start time
                    if (now.isAfter(client.getIndexStartTime()) && !client.isStarted() && lastChangeCountToStart >= 2) {
                        client.setOpen(last);
                        client.startAll();
                    }

                    //  Index end time ( Close runners )
                    if (now.isAfter(client.getIndexEndTime()) && !runnersClosed) {
                        client.getMyServiceHandler().removeService(client.getMySqlService());
                        client.getMyServiceHandler().removeService(client.getListsService());
                        runnersClosed = true;
                    }

                    // Future end time ( Export )
                    if (now.isAfter(client.getFutureEndTime())) {
                        client.closeAll();
                        client.fullExport();

                        if (Manifest.DB) {
                            // Arik
                            if (client instanceof Spx) {
                                Arik.getInstance().sendMessageToEveryOne(client.getArikSumLine());
                            }
                        }
                        break;
                    }
                } catch (InterruptedException e) {
                } catch (Exception e) {
                    Arik.getInstance().sendMessage(e.getMessage() + "\n" + e.getCause());
                }
            }
        }

        @Override
        public void initRunnable() {
            setRunnable(this);
        }

        public boolean isRun() {
            return run;
        }

        public void setRun(boolean run) {
            this.run = run;
        }
    }

}