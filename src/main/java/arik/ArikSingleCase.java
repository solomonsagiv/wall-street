package arik;

import com.pengrad.telegrambot.model.Update;

public abstract class ArikSingleCase extends ArikCase {

    String message;

    // Constructor
    public ArikSingleCase( String message ) {
        this.message = message;
    }

}
