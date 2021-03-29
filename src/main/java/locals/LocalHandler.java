package locals;

import serverObjects.BASE_CLIENT_OBJECT;

import java.util.HashSet;
import java.util.Set;

public class LocalHandler {

    public static Set<BASE_CLIENT_OBJECT> clients = new HashSet<>();

    public static String[] getClientsNames() {
        String[] arr = new String[clients.size()];
        int i = 0;
        for (BASE_CLIENT_OBJECT client : clients) {
            arr[i] = client.getName().toUpperCase();
            i++;
        }
        return arr;
    }

}
