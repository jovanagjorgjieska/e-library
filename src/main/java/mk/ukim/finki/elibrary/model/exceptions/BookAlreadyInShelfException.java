package mk.ukim.finki.elibrary.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.PRECONDITION_FAILED)
public class BookAlreadyInShelfException extends RuntimeException{
    public BookAlreadyInShelfException(Long id, String username) {
        super(String.format("Book with id: %d already exists in shelf for user with username %s", id, username));
    }
}
