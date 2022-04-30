package mk.ukim.finki.elibrary.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class RecommendationNotFoundException extends RuntimeException{
    public RecommendationNotFoundException(Long id) {
        super(String.format("Recommendation with id: %d is not found", id));
    }
}
