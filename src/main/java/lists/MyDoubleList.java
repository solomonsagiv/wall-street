package lists;

import java.util.ArrayList;

public class MyDoubleList extends ArrayList<Double> {

    private double sum = 0;

    @Override
    public boolean add(Double d) {
        sum += d;
        return super.add(d);
    }

    public double getAvg() {
        if (size() <= 0) {
            return 0;
        }
        return sum / size();
    }

    public double getAvg(int seconds) {
        if (sum <= 0) {
            return 0;
        }

        double currSum = 0;

        for (int i = size() - seconds; i < size(); i++) {
            currSum += get(i);
        }

        return currSum / size();
    }


    @Override
    public Double remove(int index) {
        sum -= get(index);
        return super.remove(index);
    }

    @Override
    public boolean remove(Object o) {
        sum -= (double) o;
        return super.remove(o);
    }

    public double getSum() {
        return sum;
    }
}