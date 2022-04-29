package mk.ukim.finki.elibrary.service.impl;

import mk.ukim.finki.elibrary.model.Author;
import mk.ukim.finki.elibrary.model.Book;
import mk.ukim.finki.elibrary.model.Category;
import mk.ukim.finki.elibrary.model.exceptions.AuthorNotFoundException;
import mk.ukim.finki.elibrary.model.exceptions.BookNotFoundException;
import mk.ukim.finki.elibrary.model.exceptions.CategoryNotFoundException;
import mk.ukim.finki.elibrary.repository.AuthorRepository;
import mk.ukim.finki.elibrary.repository.BookRepository;
import mk.ukim.finki.elibrary.repository.CategoryRepository;
import mk.ukim.finki.elibrary.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Book> findAll() {
        return this.bookRepository.findAll();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return this.bookRepository.findById(id);
    }

    @Override
    public Optional<Book> findByTitle(String title) {
        return this.bookRepository.findByTitle(title);
    }

    @Override
    public Optional<Book> save(String title, String description, Long authorId, Integer year, Long categoryId) {
        Author author = this.authorRepository.findById(authorId).orElseThrow(() -> new AuthorNotFoundException(authorId));
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId));
        Book book = new Book(title, description, author, year, category);
        this.bookRepository.save(book);
        return Optional.of(book);
    }

    @Override
    public Optional<Book> edit(Long id, String title, String description, Long authorId, Integer year, Long categoryId) {
        Book book = this.bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        Author author = this.authorRepository.findById(authorId).orElseThrow(() -> new AuthorNotFoundException(authorId));
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId));
        book.setTitle(title);
        book.setDescription(description);
        book.setAuthor(author);
        book.setYear(year);
        book.setCategory(category);
        this.bookRepository.save(book);
        return Optional.of(book);
    }

    @Override
    public void deleteById(Long id) {
        this.bookRepository.deleteById(id);
    }

    @Override
    public Book likeBook(Long id) {
        Book book = this.bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        book.setLikes(book.getLikes()+1);
        return this.bookRepository.save(book);
    }

    @Override
    public List<Book> filterByAuthorAndCategory(Long authorId, Category category) {
        Author author = authorId != null ? this.authorRepository.findById(authorId).orElse((Author) null) : null;
        if(author!=null && category!=null)
            return this.bookRepository.findAllByCategoryAndAuthor(category, author);
        else if(author!=null)
            return this.bookRepository.findAllByAuthor(author);
        else if(category!=null)
            return this.bookRepository.findAllByCategory(category);
        else
            return this.bookRepository.findAll();
    }
}
