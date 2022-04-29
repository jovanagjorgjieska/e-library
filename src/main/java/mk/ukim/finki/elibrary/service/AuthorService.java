package mk.ukim.finki.elibrary.service;

import mk.ukim.finki.elibrary.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    List<Author> findAll();

    Optional<Author> findById(Long id);

    Optional<Author> save(String name, String surname, String country);

    void deleteById(Long id);
}
