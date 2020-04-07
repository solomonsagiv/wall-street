package props;

import locals.IJsonDataBase;
import myJson.MyJson;
import options.JsonEnum;
import java.time.LocalTime;

// -------------------- Trading hours -------------------- //
public class TradingHours implements IJsonDataBase {

    private LocalTime indexStartTime;
    private LocalTime indexEndTime;
    private LocalTime futureEndTime;

    @Override
    public MyJson getAsJson() {
        MyJson json = new MyJson();
        json.put( JsonEnum.INDEX_START_TIME.toString(), getIndexEndTime() );
        json.put( JsonEnum.INDEX_END_TIME.toString(), getIndexEndTime() );
        json.put( JsonEnum.FUTURE_END_TIME.toString(), getIndexEndTime() );
        return json;
    }

    @Override
    public void loadFromJson( MyJson json ) {
        setIndexStartTime( LocalTime.parse( json.getString( JsonEnum.INDEX_START_TIME.toString() ) ) );
        setIndexEndTime( LocalTime.parse( json.getString( JsonEnum.INDEX_END_TIME.toString() ) ) );
        setFutureEndTime( LocalTime.parse( json.getString( JsonEnum.FUTURE_END_TIME.toString() ) ) );
    }

    @Override
    public MyJson getResetObject() {
        return getAsJson();
    }

    public LocalTime getIndexStartTime() {
        return indexStartTime;
    }
    public void setIndexStartTime( LocalTime indexStartTime ) {
        this.indexStartTime = indexStartTime;
    }
    public LocalTime getIndexEndTime() {
        return indexEndTime;
    }
    public void setIndexEndTime( LocalTime indexEndTime ) {
        this.indexEndTime = indexEndTime;
    }
    public LocalTime getFutureEndTime() {
        return futureEndTime;
    }
    public void setFutureEndTime( LocalTime futureEndTime ) {
        this.futureEndTime = futureEndTime;
    }


}
