package rollService;

import options.Options;
import serverObjects.BASE_CLIENT_OBJECT;
import service.MyBaseService;
import service.ServiceEnum;

public class RollService extends MyBaseService {

    Options o1, o2;

    public RollService( BASE_CLIENT_OBJECT client, Options o1, Options o2 ) {
        super( client );
        this.o1 = o1;
        this.o2 = o2;
    }

    @Override
    public void go() {

    }

    @Override
    public String getName() {
        return "RollService";
    }

    @Override
    public int getSleep() {
        return 200;
    }

    @Override
    public ServiceEnum getType() {
        return ServiceEnum.ROLL_COUNTER;
    }
}
