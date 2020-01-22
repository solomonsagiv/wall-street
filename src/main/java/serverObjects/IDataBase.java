package serverObjects;

import org.hibernate.SessionFactory;

public interface IDataBase {

    SessionFactory getSessionfactory();

    void initTables();

}
