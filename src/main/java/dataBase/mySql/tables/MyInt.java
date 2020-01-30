package dataBase.mySql.tables;

import javax.persistence.Column;

public class MyInt {

    int my;
    public MyInt(int my) {
        this.my = my;
    }


    public static void main( String[] args ) {

        MyInt myInt= new MyInt( 5 );

        T t = new T( myInt);

        myInt.my = 8;

        System.out.println(t.t.my );

    }

    public static class T {
        MyInt t;
        public T(MyInt t) {
            this.t = t;
        }
    }

}