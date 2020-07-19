package locals;

import java.awt.*;
import java.util.Random;

public class Themes {

    // Colors
    public static Color BINANCE_ORANGE = new Color(255, 204, 0);
    public static Color BINANCE_GREY = new Color(38, 45, 51);
    public static Color BINANCE_GREEN = new Color(7, 93, 55);
    //    public static Color BINANCE_GREEN = new Color( 7, 153, 98 );
    //	public static Color BINANCE_GREEN = new Color( 111 , 207 , 31 );
    public static Color BINANCE_RED = new Color(255, 53, 157);
    public static Color ORANGE = new Color(219, 158, 47);
    public static Color LIGHT_BLUE_2 = new Color(0, 237, 255);
    public static Color BLUE_STRIKE = new Color(48, 82, 181);
    public static Color BLUE_DARK = new Color(0, 24, 49);
    public static Color GREY_VERY_LIGHT = new Color(246, 241, 246);
    public static Color GREY_LIGHT = new Color(234, 229, 234);
    public static Color GREY = new Color(203, 225, 222);
    public static Color PURPLE = new Color(130, 3, 194);

    // Races
    public static Color OPEN_RACE = new Color(148, 201, 246);


    public static Color BLUE_2 = new Color(0, 65, 171);
    public static Color BLUE_LIGHT_2 = new Color(79, 229, 255, 255);

    public static Color BLUE = new Color(0, 51, 102);
    public static Color LIGHT_BLUE = new Color(176, 196, 222);
    public static Color VERY_LIGHT_BLUE = new Color(235, 228, 235);
    public static Color GREEN = new Color(7, 153, 98);
    public static Color GREEN_LIGHT = new Color(7, 217, 147);
    public static Color RED = new Color(229, 19, 0);
    public static Color PINK_LIGHT = new Color(255, 124, 176);

//	public static Color GREEN = new Color( 0 , 128 , 0 );

    // Fonts
    public static Font ARIEL_15 = new Font("Ariel", Font.PLAIN, 15);

    public static Font ARIEL_BOLD_15 = new Font("Ariel", Font.BOLD, 15);
    public static Font ARIEL_BOLD_14 = new Font("Ariel", Font.BOLD, 14);
    public static Font ARIEL_BOLD_12 = new Font("Ariel", Font.BOLD, 12);
    public static Font VEDANA_12 = new Font("Verdana", Font.PLAIN, 12);


    public static Color getRamdomColor() {
        Random random = new Random();
        return new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }


}
