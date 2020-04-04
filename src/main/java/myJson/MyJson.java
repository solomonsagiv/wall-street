package myJson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MyJson extends JSONObject {


    public MyJson(String s) {
        super(s);
    }

    /**
     * Construct an empty JSONObject.
     */
    public MyJson() {}

    @Override
    public int getInt(String key) throws JSONException {
        try {
            return super.getInt(key);
        } catch (JSONException e) {
            return 0;
        }
    }

    @Override
    public double getDouble(String key) throws JSONException {
        try {
            return super.getDouble(key);
        } catch (JSONException e) {
            return 0.0;
        }
    }


    @Override
    public String getString(String key) throws JSONException {
        try {
            return super.getString(key);
        } catch (JSONException e) {
            return "";
        }
    }

    @Override
    public Object get(String key) throws JSONException {
        try {
            return super.get(key);
        } catch (JSONException e) {
            return null;
        }
    }

    @Override
    public JSONObject getJSONObject(String key) throws JSONException {
        try {
            return super.getJSONObject(key);
        } catch (JSONException e) {
            return new JSONObject();
        }
    }

    @Override
    public JSONArray getJSONArray(String key) throws JSONException {
        try {
            return super.getJSONArray(key);
        } catch (JSONException e) {
            return new JSONArray();
        }
    }

    public MyJson getMyJson(String key) throws JSONException {
        try {
            return new MyJson( super.getJSONObject(key).toString());
        } catch (JSONException e) {
            return new MyJson();
        }
    }
}
