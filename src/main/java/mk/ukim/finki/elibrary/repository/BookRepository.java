package mk.ukim.finki.elibrary.repository;

import mk.ukim.finki.elibrary.model.Author;
import mk.ukim.finki.elibrary.model.Book;
import mk.ukim.finki.elibrary.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitle(String title);
    List<Book> findAllByAuthor(Author author);
    List<Book> findAllByCategory(Category category);
    List<Book> findAllByCategoryAndAuthor(Category category, Author author);
}
