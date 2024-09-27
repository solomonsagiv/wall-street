import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService
public class RestApi {


    @WebResult
    public String sayHallo(@WebParam(name = "name") String name) {
        return null;
    }

}
