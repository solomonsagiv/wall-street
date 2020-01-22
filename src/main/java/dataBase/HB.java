package dataBase;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import tables.TableDayFather;

import java.util.ArrayList;

public class HB {

	// Only save
	public static void save( Object object , SessionFactory factory ) {
		// Save the line
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		session.save( object );
		session.getTransaction().commit();
		session.close();
	}

	public static void save( Object object , String entityName , SessionFactory factory ) {
		// Save the line
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		session.save( entityName , object );
		session.getTransaction().commit();
		session.close();
	}

	// Update
	public static void update( SessionFactory factory , Object object ) {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		session.update( object );
		session.getTransaction().commit();
		session.close();
	}

	// Get table list
	public static synchronized ArrayList < ? > getTableList( String tableClassName , SessionFactory factory ) {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		ArrayList < ? > table = ( ArrayList < ? > ) session.createQuery( "from " + tableClassName ).list();
		session.getTransaction().commit();
		session.close();
		return table;
	}

	// Get table list
	public static synchronized ArrayList < ? > getTableList( SessionFactory factory , String query ) {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		ArrayList < ? > table = ( ArrayList < ? > ) session.createQuery( query ).list();
		session.getTransaction().commit();
		session.close();
		return table;
	}

	// Get table list
	public static synchronized ArrayList < ? > getTableList( Class c , SessionFactory factory ) {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		ArrayList < ? > table = ( ArrayList < ? > ) session.createQuery( "from " + c.getName() ).list();
		session.getTransaction().commit();
		session.close();
		return table;
	}

	// Return the last line of the table
	public static Object getLastLine( String tableName , SessionFactory factory ) {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		Object object = session.createQuery( "from " + tableName + " ORDER BY id DESC" ).setMaxResults( 1 ).uniqueResult();
		session.getTransaction().commit();
		session.close();
		return object;
	}

	// Trunticate
	public static void trunticate( String table , SessionFactory factory ) {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		session.createQuery( "DELETE FROM " + table ).executeUpdate();
		session.getTransaction().commit();
		session.close();
	}

	// Line from table by id
	public static Object get_line_by_id( Class c , int id , SessionFactory sessionFactory ) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Object line = session.get( c.getName() , id );
		session.getTransaction().commit();
		session.close();
		return line;
	}

	// Line from table by id
	public static Object get_line_by_id( String entityName , int id , SessionFactory factory ) {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		Object line = session.get( entityName , id );
		session.getTransaction().commit();
		session.close();
		return line;
	}

	// Line from table by id
	public static Object getLineByQuery( String entityName , String colName , String stockName ,
	                                     SessionFactory factory ) {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		Object line = session.createSQLQuery( String.format( "from %s where %s = %s" , entityName , colName , stockName ) );
		session.getTransaction().commit();
		session.close();
		return line;
	}


	// Line from table by id
	public static Object getLineByQuery( String query , SessionFactory factory ) {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		Object line = session.createQuery( query ).setMaxResults( 1 ).uniqueResult();
		session.getTransaction().commit();
		session.close();
		return line;
	}

	public static void saveTable( ArrayList < TableDayFather > table , String entityName, SessionFactory sessionFactory ) {

		int i = 0;

		// Save the line
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();

		for ( TableDayFather line : table ) {
			session.save( entityName, line );
			i++;
		}

		session.getTransaction().commit();
		session.close();

	}

}
