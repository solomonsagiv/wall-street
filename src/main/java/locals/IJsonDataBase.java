package locals;

import myJson.MyJson;

public interface IJsonDataBase {

    public MyJson getAsJson();
    public void loadFromJson(MyJson object );
    public MyJson getResetJson();

}
