package fuellogg.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ObjectNotFoundException extends RuntimeException{
    private static final String MESSAGE_OBJECT_NOT_FOUND= "Sorry. Object not found.";

    public ObjectNotFoundException(String MESSAGE) {
        super(MESSAGE);
    }
}
