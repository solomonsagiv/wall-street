package locals;

import java.util.ArrayList;

public class MyObjects {

    // Base object
    public abstract static class MyBaseObject implements IMyObject {

        public MyBaseObject( ArrayList< MyBaseObject > objectsList ) {
            objectsList.add( this );
        }

        public MyBaseObject() {}
    }

    // Double object
    public abstract static class MyDouble extends MyBaseObject {

        double val = 0;

        public MyDouble( ArrayList< MyBaseObject > objectsList ) {
            super( objectsList );
        }

        public MyDouble() {}

        public abstract double getCalc();

        public double getVal() {
            return val;
        }

        public void setVal( double val ) {
            this.val = val;
        }

        @Override
        public String toString() {
            return "MyDouble{" +
                    "val=" + val +
                    '}';
        }
    }

    // Integer object
    public abstract static class MyInteger extends MyBaseObject {

        int val = 0;

        public MyInteger( ArrayList< MyBaseObject > objectsList ) {
            super( objectsList );
        }

        public MyInteger() {}

        public abstract int getCalc();

        public int getVal() {
            return val;
        }

        public void setVal( int val ) {
            this.val = val;
        }

        @Override
        public String toString() {
            return "MyInteger{" +
                    "val=" + val +
                    '}';
        }
    }

    // Integer object
    public abstract static class MyString extends MyBaseObject {

        String val = "";

        public MyString( ArrayList< MyBaseObject > objectsList ) {
            super( objectsList );
        }

        public MyString() {}

        public abstract String getCalc();

        public String getVal() {
            return val;
        }

        public void setVal( String val ) {
            this.val = val;
        }

        @Override
        public String toString() {
            return "MyString{" +
                    "val='" + val + '\'' +
                    '}';
        }
    }


    // Interface
    public interface IMyObject {

        void initMe( int sleep );

        int getSleep();

    }

}
