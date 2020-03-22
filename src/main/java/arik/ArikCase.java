package arik;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.Keyboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ArikCase {

    // Variables
    protected Map<String, ArikCase> caseMaps = new HashMap<>();
    protected Update update;
    protected boolean used = false;
    private Keyboard keyboard;

    // Constructor
    public ArikCase() {}

    public abstract boolean doCase( Update update );

    public Map myMessages() {
        if ( caseMaps == null ) throw new NullPointerException( "Message isn't set" );
        return caseMaps;
    }

    public void setMyMessage( Map myMessages ) {
        this.caseMaps = myMessages;
    }

    public Keyboard getKeyboard() {
        return keyboard;
    }

    public void setKeyboard( Keyboard keyboard ) {
        if ( keyboard == null ) throw new NullPointerException( getClass().getName() + " Keyboard isn't set" );
        this.keyboard = keyboard;
    }
}