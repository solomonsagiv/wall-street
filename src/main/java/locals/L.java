package locals;

import java.text.DecimalFormat;

public class L {

	private static DecimalFormat df100;
	private static DecimalFormat df10;

	public static double modulu ( double value ) {

		while ( true ) {

			if ( value % 10 == 0 ) {
				return value;
			}

			value = ( ( int ) ( value / 10 ) ) * 10;

		}

	}

	public static DecimalFormat format100 () {
		if ( df100 == null ) {
			df100 = new DecimalFormat ( "#,##0.00;-#,##0.00" );
			df100.setNegativePrefix ( "-" );
		}
		return df100;
	}


	public static DecimalFormat format10 () {
		if ( df10 == null ) {
			df10 = new DecimalFormat ( "#,##0.0;-#,##0.0" );
			df10.setNegativePrefix ( "-" );
		}
		return df10;
	}

	public static String format100 ( double num ) {
		if ( df100 == null ) {
			df100 = new DecimalFormat ( "#,##0.00;-#,##0.00" );
			df100.setNegativePrefix ( "-" );
		}
		return df100.format ( num );
	}

	public static String format10 ( double num ) {
		if ( df10 == null ) {
			df10 = new DecimalFormat ( "#,##0.0;-#,##0.0" );
			df10.setNegativePrefix ( "-" );
		}
		return df10.format ( num );
	}


	public static double dbl ( String s ) {
		return Double.parseDouble ( s );
	}

	public static String str ( Object o ) {
		return String.valueOf ( o );
	}

	public static int INT ( String s ) {
		return Integer.parseInt ( s );
	}

	public static double opo ( double d ) {
		return d * -1.0;
	}

	public static double abs ( double d ) {
		return Math.abs ( d );
	}

}
