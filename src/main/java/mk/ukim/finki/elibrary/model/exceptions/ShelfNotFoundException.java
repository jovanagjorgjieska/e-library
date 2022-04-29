package mk.ukim.finki.elibrary.model.exceptions;

import mk.ukim.finki.elibrary.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ShelfNotFoundException extends RuntimeException{
    public ShelfNotFoundException() {
        super(String.format("Shelf for that user is not found!"));
    }
}
