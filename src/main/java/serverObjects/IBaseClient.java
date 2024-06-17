package serverObjects;

public interface IBaseClient {

    void initExpHandler();

    ApiEnum getApi();

    void initSeries(BASE_CLIENT_OBJECT client);

    void openChartsOnStart();

    void init_races();

}
