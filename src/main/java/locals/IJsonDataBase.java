package locals;

import myJson.MyJson;

public interface IJsonDataBase {

    MyJson getAsJson();
    void loadFromJson(MyJson object );
    MyJson getResetObject();

}
