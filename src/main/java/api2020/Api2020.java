package api2020;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Api2020 {

	public static String DOLLAR = "9999806";
	public static String nasdaq = "70121215";
	public static String dax = "70121124";
	public static String spx = "70121058";
	public static String TEVA = "629014";
	public static String MYL = "1136704";
	public static String SODA = "1121300";
	public static String PRGO = "1130699";
	public static String TSEM = "1082379";
	public static String OPK = "1129543";
	public static String GZT = "126011";
	public static String ORA = "1134402";
	public static String ESLT = "1081124";
	public static String ICL = "281014";
	public static String CEL = "1101534";
	public static String NICE = "273011";
	public static String PTNR = "1083484";

	@SuppressWarnings( "deprecation" )
	public static String url ( String name ) {
		return "http://localhost:4071/aoc/core/AocRT/getmanyfields?s=" + URLEncoder.encode ( name )
				+ "&f=open,high,low,close,last,base,weight_ta35";
	}

	@SuppressWarnings( "deprecation" )
	public static String url_dollar ( String name ) {
		return "http://localhost:4071/aoc/core/AocRT/getmanyfields?s=" + URLEncoder.encode ( name )
				+ "&f=open,high,low,close,last_known,base,weight_ta35";
	}

	// HTTP GET request
	public static JSONArray sendGet ( String url ) throws Exception {

		URL obj = new URL ( url );
		HttpURLConnection con = ( HttpURLConnection ) obj.openConnection ( );

		// optional default is GET
		con.setRequestMethod ( "GET" );

		// add request header
		con.setRequestProperty ( "User-Agent", "Mozilla/5.0" );

		BufferedReader in = new BufferedReader ( new InputStreamReader ( con.getInputStream ( ) ) );
		String inputLine = in.readLine ( );
		StringBuffer response = new StringBuffer ( );

		// json parser
		response.append ( inputLine );
		org.json.JSONObject job = new org.json.JSONObject ( inputLine );
		org.json.JSONObject pageName = job.getJSONObject ( "GetManyFieldsResult" );

		in.close ( );

		return pageName.getJSONArray ( "Values" );
	}

	public static JSONObject get ( String url ) {
		JSONObject object = new JSONObject ( );
		try {

			object = new JSONObject ( Jsoup.connect ( url ).get ( ).body ( ) );
			System.out.println ( object.toString ( ) );

		} catch ( IOException e ) {
			e.printStackTrace ( );
		}

		return object;
	}

}
