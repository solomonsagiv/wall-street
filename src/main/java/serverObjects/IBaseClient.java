package serverObjects;

public interface IBaseClient {

    void initTwsHandler( );
    void initTablesHandlers( );
    void initOptionsHandler() throws Exception;
    void initDDECells();
    ApiEnum getApi();
    void requestApi();

}
