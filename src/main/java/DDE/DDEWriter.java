package DDE;

import IDDE.IDDEWriter;
import com.pretty_tools.dde.DDEException;
import com.pretty_tools.dde.client.DDEClientConversation;
import exp.Exps;
import serverObjects.BASE_CLIENT_OBJECT;
import threads.MyThread;

public class DDEWriter extends MyThread implements Runnable {

    private boolean run = true;
    private DDEConnection ddeConnection = new DDEConnection();
    private DDEClientConversation conversation;
    Exps exps;
    BASE_CLIENT_OBJECT client;
    IDDEWriter iddeWriter;

    // Constructor
    public DDEWriter(BASE_CLIENT_OBJECT client) {
        this.client = client;
        this.exps = client.getExps();
        this.iddeWriter = client.getDdeHandler().getIddeWriter();
        this.conversation = ddeConnection.createNewConversation(client.getDdeHandler().getPath());
    }

    @Override
    public void initRunnable() {
        setName("DDE Writer");
        setRunnable(this);
    }

    @Override
    public void run() {



        dd


        while (run) {
            try {

                // Sleep
                Thread.sleep(4000);

                if (client.isStarted()) {
                    // Write the data to the excel
                    iddeWriter.write(conversation);
                }

            } catch (InterruptedException e) {
                close();
            }
        }
    }

    // Close
    public void close() {
        try {
            conversation.disconnect();
        } catch (DDEException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        run = false;
    }
}