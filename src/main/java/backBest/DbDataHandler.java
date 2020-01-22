package backBest;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import serverObjects.BASE_CLIENT_OBJECT;

import java.util.ArrayList;
import java.util.List;

public class DbDataHandler {

	public static BASE_CLIENT_OBJECT client;

	// Constructor
	public DbDataHandler () {
		// TODO Auto-generated constructor stub
	}

	// Get all the table lines as list
	public static List < ? > getTable ( Class c ) {
		// The line list
		List < ? > lines = new ArrayList <> ( );

		// Get table from the database
		SessionFactory factory = client.getSessionfactory ( );
		Session session = factory.getCurrentSession ( );
		session.beginTransaction ( );
		lines = session.createQuery ( "from " + c.getName ( ) ).list ( );
		session.getTransaction ( ).commit ( );
		session.close ( );
		return lines;
	}

}
