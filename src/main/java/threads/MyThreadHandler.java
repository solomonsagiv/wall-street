package threads;

import arik.Arik;

public class MyThreadHandler {

	// Variables
	MyThread myThread;
	Thread thread;
	Arik arik;

	// Constructor
	public MyThreadHandler ( MyThread myThread ) {

		this.myThread = myThread;
		this.arik = Arik.getInstance ( );

	}

	// ---------- Functions ---------- //

	// Start
	public void start () {

		if ( thread == null ) {

			myThread.setRun ( true );
			thread = new Thread ( myThread.getRunnable ( ) );
			thread.start ( );
		}

		arik.sendMessage ( myThread.getClient ( ).getName ( ) + " " + myThread.getName ( ) + " Started" );
	}

	// Close
	public void close () {

		if ( thread != null ) {
			thread.interrupt ( );
			myThread.setRun ( false );
			thread = null;
		}
		arik.sendMessage ( myThread.getClient ( ).getName ( ) + " " + myThread.getName ( ) + " Closed" );

	}

	// Restart
	public void restart () {

		close ( );
		start ( );

		arik.sendMessage ( myThread.getClient ( ).getName ( ) + " " + myThread.getName ( ) + " Restarted" );

	}
}
