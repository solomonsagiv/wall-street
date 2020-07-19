package shlomi;

import serverObjects.BASE_CLIENT_OBJECT;
import shlomi.algorithm.Algorithm;
import threads.MyThread;

public class ShlomiRunner extends MyThread implements Runnable {

    int sleep = 1000;
    Algorithm algorithm;

    // Constructor
    public ShlomiRunner(BASE_CLIENT_OBJECT client, Algorithm algorithm) {
        super(client);
        this.algorithm = algorithm;
    }

    @Override
    public void run() {
        init();
    }

    private void init() {
        while (isRun()) {
            try {
                // Sleep
                Thread.sleep(sleep);

                // Go (Do the algorithm logic)
                go();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void go() {
        algorithm.doAlgo();
    }

    @Override
    public void initRunnable() {
        setRunnable(this);
    }
}
