package locals;

import org.json.JSONObject;

import java.util.ArrayList;

public class MyObjects {

    // Base object
    public abstract static class MyBaseObject implements IMyObject {

        public MyBaseObject( ArrayList< MyBaseObject > objectsList ) {
            objectsList.add( this );
        }

        public MyBaseObject() {
        }
    }

    // Double object
    public static class MySimpleDouble extends MyDouble {

        double val = 0;

        public MySimpleDouble( ArrayList< MyBaseObject > objectsList ) {
            super( objectsList );
        }

        public MySimpleDouble() {
        }

        @Override
        public void calc() {}

        public double getVal() {
            return val;
        }

        public void setVal( double val ) {
            this.val = val;
        }

        @Override
        public void initMe( int sleep ) {
        }

        @Override
        public int getSleep() {
            return 0;
        }

        @Override
        public String toString() {
            return "MyDouble{" +
                    "val=" + val +
                    '}';
        }
    }


    // Double object
    public abstract static class MyDouble extends MyBaseObject {

        double val = 0;

        public MyDouble( ArrayList< MyBaseObject > objectsList ) {
            super( objectsList );
        }

        public MyDouble() {
        }

        public abstract void calc();

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
    public static class MySimpleInteger extends MyInteger {

        int val = 0;

        public MySimpleInteger( ArrayList< MyBaseObject > objectsList ) {
            super( objectsList );
        }

        public MySimpleInteger() {
        }

        @Override
        public int calc() {
            return 0;
        }

        @Override
        public int getSleep() {
            return 0;
        }

        @Override
        public void initMe( int sleep ) {

        }

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

        public void increment() {
            val++;
        }

        public void decrement() {
            val--;
        }

    }

    // Integer object
    public abstract static class MyInteger extends MyBaseObject {

        int val = 0;

        public MyInteger( ArrayList< MyBaseObject > objectsList ) {
            super( objectsList );
        }

        public MyInteger() {
        }

        public abstract int calc();

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

        public void increment() {
            val++;
        }

        public void decrement() {
            val--;
        }

    }

    // Integer object
    public abstract static class MyString extends MyBaseObject {

        String val = "";

        public MyString( ArrayList< MyBaseObject > objectsList ) {
            super( objectsList );
        }

        public MyString() {
        }

        public abstract void calc();

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



    // Json object
    public abstract static class MyJSONObject extends MyBaseObject {

        JSONObject val = new JSONObject();

        public MyJSONObject( ArrayList< MyBaseObject > objectsList ) {
            super( objectsList );
        }

        public MyJSONObject() {}

        public abstract JSONObject getValByCurrentCalc();

        public abstract void calc();

        public JSONObject getVal() {
            return val;
        }

        public void setVal( JSONObject val ) {
            this.val = val;
        }

        @Override
        public String toString() {
            return "MyJSONObject{" +
                    "val=" + val +
                    '}';
        }
    }



    // Interface
    public interface IMyObject {

        void initMe( int sleep );

        int getSleep();

    }

}
