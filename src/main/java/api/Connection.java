package api;

import org.json.JSONObject;

public class Connection {

	public static String connect () {
		JSONObject success = new JSONObject ( );
		try {
			Downloader downloader = Downloader.getInstance ( );
			downloader.start ( );

			success.put ( "Success", true );
			return String.valueOf ( success );
		} catch ( Exception e ) {
			success.put ( "Success", false );
			return String.valueOf ( success );
		}
	}

	public static String disConnect () {
		JSONObject success = new JSONObject ( );
		try {
			Downloader downloader = Downloader.getInstance ( );
			downloader.close ( );
			success.put ( "Success", true );
			return String.valueOf ( success );
		} catch ( Exception e ) {
			success.put ( "Success", false );
			return String.valueOf ( success );
		}
	}

	public static String isConnect () {
		JSONObject success = new JSONObject ( );
		boolean isConnected = false;
		try {
			Downloader downloader = Downloader.getInstance ( );
			isConnected = downloader.getClient ( ).isConnected ( );
			success.put ( "connected", isConnected );
			return String.valueOf ( success );
		} catch ( Exception e ) {
			success.put ( "connected", isConnected );
			return String.valueOf ( success );
		}
	}

	public String cancelMarket () {
		JSONObject success = new JSONObject ( );
		try {
			Downloader downloader = Downloader.getInstance ( );
			downloader.close ( );
			success.put ( "Success", true );
			return String.valueOf ( success );
		} catch ( Exception e ) {
			success.put ( "Success", false );
			return String.valueOf ( success );
		}
	}
}
