package serverObjects;

public interface IBaseClient {

    void initExpHandler();

    ApiEnum getApi();

    void initBaseId();

    void initSeries();

    void openChartsOnStart();

}
