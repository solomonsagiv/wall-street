package lists;

import serverObjects.BASE_CLIENT_OBJECT;
import service.MyBaseService;

// Regular list updater
public class ListsService extends MyBaseService {

    // Variables


    // Constructor
    public ListsService(BASE_CLIENT_OBJECT client, String name, int type, int sleep) {
        super(client, name, type, sleep);
    }

    @Override
    public void go() {
        insert();
    }

    private void insert() {
        for ( MyList list: getClient().getLists() ) {
            list.addVal( );
        }
    }
}

