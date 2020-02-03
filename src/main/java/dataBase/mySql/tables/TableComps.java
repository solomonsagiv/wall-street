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

        // Constructor
        public MyTableColumn( BaseTable table, String name ) {
            this.table = table;
            this.name = name;
            table.columns.add( this );
        }

        // Constructor
        public MyTableColumn( BaseTable table, String name, MyObjects.MyDouble myDouble ) {
            this( table, name );
            this.myDouble = myDouble;
        }

        // Constructor
        public MyTableColumn( BaseTable table, String name, MyObjects.MyString myString ) {
            this( table, name );
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
    }
}
