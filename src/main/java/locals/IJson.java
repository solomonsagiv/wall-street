package locals;

import myJson.MyJson;

public interface IJson {

    public MyJson getAsJson();
    public void loadFromJson(MyJson object );
    public MyJson getResetJson();

}
