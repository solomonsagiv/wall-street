package webServices;

import javax.jws.WebService;

@WebService(endpointInterface = "webServices.MainService", serviceName = "main")
public class HelloWorld implements MainService {

    @Override
    public String seyHello( String name ) {
        return "Hello " + name;
    }

}
