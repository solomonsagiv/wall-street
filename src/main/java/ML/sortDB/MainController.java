package ML.sortDB;

import java.util.ArrayList;

public class MainController {

    int id = 100005746;
    ExelReader exelReader;
    WriterDB writerDB;

    public MainController() {

        exelReader = new ExelReader();

    }

    public static void main(String[] args) {
        MainController mainController = new MainController();
        mainController.control();
    }

    // The function that control the program flow
    public void control() {

        // Read the excel and get the lines arrayList
        ArrayList<TA35Data> table = exelReader.readExel(id);
        id = table.get(table.size() - 1).getId();
        System.out.println(id);

        // Write to db
        writerDB = new WriterDB(table);
        writerDB.write();

    }
}
