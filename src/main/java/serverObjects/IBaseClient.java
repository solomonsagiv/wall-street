package serverObjects;

public interface IBaseClient {

    void initOptionsHandler();
    void initDDECells();
    ApiEnum getApi();
    void initBaseId();

}
