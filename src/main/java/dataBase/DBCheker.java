package dataBase;

import serverObjects.indexObjects.SpxCLIENTObject;
import tables.daily.SpxTable;

import javax.swing.*;
import java.time.LocalTime;
import java.util.ArrayList;

public class DBCheker {

	// Check in day data
	public void checkInDay() {

		// Variables
		String date = "";
		LocalTime time;
		boolean newDay = false;

		// Get the table from the database
		ArrayList < SpxTable > spxTable = ( ArrayList < SpxTable > ) HB.getTableList( SpxTable.class , SpxCLIENTObject.getInstance().getSessionfactory() );

		System.out.println( "Got table" );
		System.out.println( "Start running" );

		// For each line in table
		for ( SpxTable line : spxTable ) {

			// Day changed
			if ( !line.getDate().equals( date ) ) {
				date = line.getDate();
				newDay = true;
			} else {
				newDay = false;
			}

			// Time
			time = LocalTime.parse( line.getTime() );

			// Time after 17:00
			if ( time.isAfter( LocalTime.of( 17 , 0 , 0 ) ) ) {

				// Check races
				if ( line.getCon_up() == 0 && line.getCon_down() == 0 && line.getIndex_up() == 0 && line.getIndex_down() == 0 ) {
					alert( "Races equals to 0" );
				}

			}


		}

	}

	// Alert window
	public void alert( String text ) {

		JOptionPane.showConfirmDialog( null , text );

	}

}
