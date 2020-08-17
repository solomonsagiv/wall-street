package webServices;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService
public interface MainService {

    @WebResult
    public String seyHello( @WebParam(name="name") String name );

}
