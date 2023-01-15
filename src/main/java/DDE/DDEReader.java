package DDE;

import IDDE.IDDEReader;
import com.pretty_tools.dde.DDEException;
import com.pretty_tools.dde.client.DDEClientConversation;
import serverObjects.BASE_CLIENT_OBJECT;
import threads.MyThread;

public class DDEReader extends MyThread implements Runnable {

    // Variables
    int sleep = 100;
    DDEClientConversation conversation;
    private DDEConnection ddeConnection = new DDEConnection();
    IDDEReader iddeReaderUpdater;

    // Constructor
    public DDEReader(BASE_CLIENT_OBJECT client) {
        super(client);
        this.iddeReaderUpdater = client.getDdeHandler().getIddeReader();
        this.conversation = ddeConnection.createNewConversation(client.getExcel_path());
    }

    @Override
    public void initRunnable() {
        setRunnable(this);
    }

    @Override
    public void run() {

        while (isRun()) {
            try {
                // Sleep
                Thread.sleep(sleep);

                // DDE
                read();
            } catch (InterruptedException e) {
                break;
            } catch (DDEException e) {
                // TODO
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public DDEClientConversation getConversation() {
        return conversation;
    }

    private void read() throws DDEException {
        updateData();
    }

    private void updateData() throws DDEException {
        iddeReaderUpdater.updateData(conversation);
    }
}