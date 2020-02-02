package locals;

import java.util.ArrayList;

public class MyObjects {

    // Base object
    public abstract static class MyBaseObject implements IMyObject {

        public MyBaseObject(ArrayList<MyBaseObject> objectsList) {
            objectsList.add(this);
        }

        public abstract void initMe();

    }

    // Double object
    public abstract static class MyDouble extends MyBaseObject {

        double val = 0;

        public MyDouble(ArrayList<MyBaseObject> objectsList) {
            super(objectsList);
        }

        public double getVal() {
            return val;
        }

        public void setVal(double val) {
            this.val = val;
        }
    }

    // Integer object
    public abstract static class MyInteger extends MyBaseObject {

        int val = 0;

        public MyInteger(ArrayList<MyBaseObject> objectsList) {
            super(objectsList);
        }

        public int getVal() {
            return val;
        }

        public void setVal(int val) {
            this.val = val;
        }
    }

    // Interface
    public interface IMyObject {

        void initMe();

    }

}
