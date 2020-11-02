package dataBase;

import ML.sortDB.TA35Data;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HBsessionTA35 {

    static Session session = null;
    static SessionFactory factory = null;

    private HBsessionTA35() {
    }

    // Create the session
    public static SessionFactory getSessionInstance() {
        if (factory == null) {
            factory = new Configuration().configure("hibernateRdsTA35.cfg.xml").addAnnotatedClass(TA35Data.class).buildSessionFactory();
        }
        return factory;
    }

    // Close connection
    public static void close_connection() {
        if (session != null) {
            session.close();
            factory.close();
        }
    }

}
