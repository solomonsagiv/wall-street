package miki;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.request.SendMessage;

public class Miki {

	private static Miki miki;
	String token = "774658162:AAEDe4o1het1WIoxCcG-mrQnzwtJgCLxWxY";
	private TelegramBot bot;
	private int updateId = 0;

	private Miki () {
		bot = TelegramBotAdapter.build ( token );
	}

	// Get instance
	public static Miki getInstance () {
		if ( miki == null ) {
			miki = new Miki ( );
		}
		return miki;
	}

	// Send message
	public void sendMessage ( int id, String text, Keyboard keyBoard ) {
		if ( keyBoard != null ) {
			bot.execute ( new SendMessage ( id, text ).replyMarkup ( keyBoard ) );
			updateId += 1;
		} else {
			bot.execute ( new SendMessage ( id, text ) );
			updateId += 1;
		}
	}

}
