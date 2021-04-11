package dataBase.props;

import myJson.MyJson;
import serverObjects.BASE_CLIENT_OBJECT;

public abstract class Prop {

    BASE_CLIENT_OBJECT client;
    private String name;

    public Prop(BASE_CLIENT_OBJECT client, String name) {
        this.client = client;
        this.name = name;
    }

    public abstract void setData(String json_text);
    public abstract MyJson getData();

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
