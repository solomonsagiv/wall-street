package serverObjects;

public interface IBaseClient {

    void initExpHandler();

    ApiEnum getApi();

    void initSeries();

    void openChartsOnStart();

}
