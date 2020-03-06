package serverObjects;

public interface IBaseClient {

    void initIds( );
    void initTwsHandler( );
    void initName( );
    void initRacesMargin( );
    double getStrikeMargin( );
    void initStartOfIndexTrading( );
    void initEndOfIndexTrading( );
    void initEndOfFutureTrading( );
    void initDbId( );
    void initTablesHandlers( );
    void initOptionsHandler() throws Exception;
    void initDDECells();
    ApiEnum getApi();
    void requestApi();

}
