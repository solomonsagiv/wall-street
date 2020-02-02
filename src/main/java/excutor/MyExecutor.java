package excutor;

import serverObjects.BASE_CLIENT_OBJECT;
import threads.MyThread;

public class MyExecutor extends MyThread implements Runnable {

    int sleep = 100;

    public MyExecutor(BASE_CLIENT_OBJECT client) {
        super(client);
    }

    @Override
    public void run() {


        while (isRun()) {
            try {

                // Sleep
                Thread.sleep(sleep);

                // Execute
                execute();

            }  catch (InterruptedException e) {
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void execute() {

        //


    }

    @Override
    public void initRunnable() {
        setRunnable(this);
    }
}
