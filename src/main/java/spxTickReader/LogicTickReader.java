package spxTickReader;

import DDE.DDEConnection;
import IDDE.IDDEReader;
import com.pretty_tools.dde.DDEException;
import com.pretty_tools.dde.client.DDEClientConversation;
import exp.E;
import exp.ExpStrings;
import locals.L;
import serverObjects.BASE_CLIENT_OBJECT;
import threads.MyThread;

public class LogicTickReader extends MyThread implements Runnable {

    BASE_CLIENT_OBJECT client;

    DDEClientConversation conversation;
    private DDEConnection ddeConnection = new DDEConnection();
    IDDEReader iddeReaderUpdater;
    final String FILE_LOCATION = "C:/Users/user/Desktop/Book1.xlsm";

    E e1;

    final String futLast = "R6C5";
    final String futBid = "R7C5";
    final String futAsk = "R5C5";
    final String lastTick = "R6C6";
    final String volume = "R6C7";

    boolean changed = false;

    public LogicTickReader(BASE_CLIENT_OBJECT client) {
        this.client = client;
        this.e1 = (E) client.getExps().getExp(ExpStrings.q1);
        this.iddeReaderUpdater = iddeReaderUpdater;
        conversation = ddeConnection.createNewConversation(FILE_LOCATION);
    }

    @Override
    public void run() {
        go();
    }

    public void go() {

        while (isRun()) {
            try {
                // Sleep
                Thread.sleep(200);

                // Read
                read();

                // Print
                print();

            } catch (InterruptedException | DDEException e) {
                e.printStackTrace();
            }
        }

    }

    private void print() {

        if (changed) {
//        System.out.println( LocalTime.now() );
            System.out.println(+e1.getVolume() + " " + client.getLastTick() + " " + e1.getFuture_bid() + " " + e1.get_future() + " " + e1.getFuture_ask() + " ");
        }
    }

    private void read() throws DDEException {

        changed = false;

        double newVolume = L.dbl(conversation.request(volume).replaceAll("\\s+", ""));

        if (newVolume > e1.getVolume()) {

            changed = true;

            e1.set_future(L.dbl(conversation.request(futLast).replaceAll("\\s+", "")));
            e1.setFuture_bid(L.dbl(conversation.request(futBid).replaceAll("\\s+", "")));
            e1.setFuture_ask(L.dbl(conversation.request(futAsk).replaceAll("\\s+", "")));
            client.setLastTick(L.INT(conversation.request(lastTick).replaceAll("\\s+", "")));
        }

    }

    @Override
    public void initRunnable() {
        setRunnable(this);
    }
}