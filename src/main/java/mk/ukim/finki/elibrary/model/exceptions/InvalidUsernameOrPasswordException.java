package mk.ukim.finki.elibrary.model.exceptions;

public class InvalidUsernameOrPasswordException extends RuntimeException{
    public InvalidUsernameOrPasswordException() {
        super("Invalid username or password do not match exception.");
    }
}
