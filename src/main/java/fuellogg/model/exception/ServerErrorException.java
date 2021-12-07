package fuellogg.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ServerErrorException extends Exception{
    private static final String MESSAGE_SERVER_ERROR= "Ooops. Something went wrong.";

    public ServerErrorException(String MESSAGE) {
        super(MESSAGE);
    }
}
