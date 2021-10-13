package delta;

import exp.E;
import exp.ExpStrings;
import serverObjects.BASE_CLIENT_OBJECT;

public class DeltaCalc {

    E exp;

    public DeltaCalc(BASE_CLIENT_OBJECT client) {
        this.exp = (E) client.getExps().getExp(ExpStrings.q1);
    }

    public static double calc(int quantity, double last, double bid, double ask) {

        double delta = 0;

        // Buy
        if (last == ask) {
            delta = quantity;
        }

        // Sell
        if (last == bid) {
            delta = quantity * -1;
        }

        return delta;
    }

}
