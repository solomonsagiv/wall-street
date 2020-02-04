package dataBase.mySql.tables;

import locals.MyObjects;

public class TableComps {

    // ---------- Column ---------- //
    public static abstract class MyTableColumn {

        // Variables
        BaseTable table;
        private String name;
        private MyObjects.MyDouble myDouble;
        private MyObjects.MyString myString;
        private int type;

        public static final int INT = 0;
        public static final int STRING = 1;
        public static final int DOUBLE = 2;

        // Constructor
        public MyTableColumn( BaseTable table, String name, int type ) {
            this.table = table;
            this.name = name;
            this.type = type;
            table.columns.add( this );
        }

        // Constructor
        public MyTableColumn( BaseTable table, String name, MyObjects.MyDouble myDouble ) {
            this( table, name, DOUBLE );
            this.myDouble = myDouble;
        }

        // Constructor
        public MyTableColumn( BaseTable table, String name, MyObjects.MyString myString ) {
            this( table, name, STRING );
            this.myString = myString;
        }

        public abstract Object getVal();

        public MyObjects.MyDouble getMyDouble() {
            return myDouble;
        }
        public void setMyDouble( MyObjects.MyDouble myDouble ) {
            this.myDouble = myDouble;
        }

        public String getName() {
            return name;
        }
        public void setName( String name ) {
            this.name = name;
        }

        public MyObjects.MyString getMyString() {
            return myString;
        }
        public void setMyString( MyObjects.MyString myString ) {
            this.myString = myString;
        }

        public int getType() {
            return type;
        }
        public void setType( int type ) {
            this.type = type;
        }
    }

}
