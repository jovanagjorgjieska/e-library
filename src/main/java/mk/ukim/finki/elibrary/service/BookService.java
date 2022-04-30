package mk.ukim.finki.elibrary.service;

import mk.ukim.finki.elibrary.model.Book;
import mk.ukim.finki.elibrary.model.Category;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> findAll();

    Optional<Book> findById(Long id);

    Optional<Book> findByTitle(String title);

    Optional<Book> save(String title, String description, String author, Integer year, Long categoryId);

    Optional<Book> edit(Long id, String title, String description, String author, Integer year, Long categoryId);

    void deleteById(Long id);

    Book likeBook(Long id);

    List<Book> filterByAuthorAndCategory(String author, Category category);
}
