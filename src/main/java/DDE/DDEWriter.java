package DDE;

import com.pretty_tools.dde.DDEException;
import com.pretty_tools.dde.client.DDEClientConversation;
import roll.RollEnum;
import serverObjects.indexObjects.Spx;
import threads.MyThread;

public class DDEWriter extends MyThread implements Runnable {

    private String excelPath = "C://Users/user/Desktop/DDE/[SPXT.xlsx]Trading";
    private boolean run = true;
    private DDEConnection ddeConnection = new DDEConnection();
    private DDEClientConversation conversation;

    Spx spx = Spx.getInstance();

    String opAvgCell = "R2C10";
    String rollCell = "R2C11";
    String indexBidAskCounterCell = "R2C12";
    String basketsCell = "R3C5";
    String basketsCell2 = "R3C7";

    // Constructor
    public DDEWriter() {
        this.conversation = ddeConnection.createNewConversation(excelPath);
    }

    @Override
    public void initRunnable() {
        setName("DDE Writer");
        setRunnable(this);
    }

    @Override
    public void run() {

        while (run) {
            try {

                // Sleep
                Thread.sleep(4000);

                if (spx.isStarted()) {
                    // Write the data to the excel
                    writeData();
                }

            } catch (InterruptedException e) {
                close();
            }
        }
    }

    // Write the data to the excel
    private void writeData() {
        try {
            conversation.poke(rollCell, str(spx.getRollHandler().getRoll(RollEnum.E1_E2).getAvg()));
            conversation.poke(indexBidAskCounterCell, str(spx.getIndexBidAskCounter()));
            conversation.poke(basketsCell, str(spx.getBasketService().getBaskets()));
            conversation.poke(basketsCell2, str(spx.getBasketService2().getBaskets()));
            try {
                conversation.poke(opAvgCell, str(spx.getExps().getMainExp().getOpAvgFuture()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (DDEException e) {
            System.out.println("DDE request error on updateData()");
            e.printStackTrace();
        }
    }

    public String str(Object o) {
        return String.valueOf(o);
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