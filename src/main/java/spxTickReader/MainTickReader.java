package spxTickReader;

import serverObjects.indexObjects.Spx;

public class MainTickReader {

    public static void main(String[] args) {
        LogicTickReader logicTickReader = new LogicTickReader(Spx.getInstance());
        logicTickReader.getHandler().start();
    }

}