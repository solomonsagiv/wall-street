package exp;

import serverObjects.BASE_CLIENT_OBJECT;

public class E extends Exp {

    private double volume = 0;
    
    public E(BASE_CLIENT_OBJECT client, String expEnum) {
        super(client, expEnum);
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

}