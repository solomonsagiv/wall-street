package threads;

public class MyThreadHandler {

    // Variables
    MyThread myThread;
    Thread thread;

    // Constructor
    public MyThreadHandler(MyThread myThread) {
        this.myThread = myThread;
    }

    // ---------- Functions ---------- //

    // Start
    public void start() {

        if (thread == null) {

            myThread.setRun(true);
            thread = new Thread(myThread.getRunnable());
            thread.start();
        }
    }

    // Close
    public void close() {

        if (thread != null) {
            thread.interrupt();
            myThread.setRun(false);
            thread = null;
        }
    }

    // Restart
    public void restart() {

        close();
        start();

    }
}
