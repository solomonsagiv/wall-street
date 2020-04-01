package locals;

import org.json.JSONObject;

public interface IJsonDataBase {

    JSONObject getAsJson();
    void loadFromJson(JSONObject object );
    JSONObject getResetObject();

}
