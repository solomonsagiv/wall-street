package serverObjects;

public interface IBaseClient {

    void initOptionsHandler() throws Exception;
    void initDDECells();
    ApiEnum getApi();
    void requestApi();
    void initBaseId();

}
