package arik.locals;

import com.pengrad.telegrambot.model.request.*;
import serverObjects.indexObjects.INDEX_CLIENT_OBJECT;

public class KeyBoards {
    /**
     * keyboards
     */
    // Main
    public static Keyboard main() {
        Keyboard keyboard = new ReplyKeyboardMarkup( new KeyboardButton[] { new KeyboardButton( "Dax" ),
                new KeyboardButton( "Ndx" ), new KeyboardButton( "Spx" ) },
                new KeyboardButton[] { new KeyboardButton( "Dax" ) } );
        return keyboard;
    }

    // Binance
    public static Keyboard alerts() {
        Keyboard keyboard = new ReplyKeyboardMarkup(
                new KeyboardButton[] { new KeyboardButton( Emojis.New + Emojis.alarm_clock ),
                        new KeyboardButton( "Open alerts" ), new KeyboardButton( Emojis.no_bell ),
                        new KeyboardButton( Emojis.no_bell + " All" ) },
                new KeyboardButton[] { new KeyboardButton( "Main menu" ) } );
        return keyboard;
    }

    // Binance
    public static Keyboard stock_option_keyBoard( INDEX_CLIENT_OBJECT stockObject ) {
        String stockName = stockObject.getName( );

        Keyboard keyboard = new ReplyKeyboardMarkup(
                new KeyboardButton[] { new KeyboardButton( stockName + " " + Emojis.status ),
                        new KeyboardButton( Emojis.chart ), new KeyboardButton( Emojis.racing_car ),
                        new KeyboardButton( "Yesterday" ) },
                new KeyboardButton[] { new KeyboardButton( Emojis.alarm_clock ), },

                new KeyboardButton[] { new KeyboardButton( "Main menu" ) } );
        return keyboard;
    }

    // Inline keyboard
    public static InlineKeyboardMarkup inline_keyboard() {
        return new InlineKeyboardMarkup(
                new InlineKeyboardButton[] { new InlineKeyboardButton( "Hey" ).url( "https://google.com" ) } );
    }

}
