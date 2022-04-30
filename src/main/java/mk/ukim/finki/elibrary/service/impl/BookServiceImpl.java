package mk.ukim.finki.elibrary.service.impl;

import mk.ukim.finki.elibrary.model.Book;
import mk.ukim.finki.elibrary.model.Category;
import mk.ukim.finki.elibrary.model.exceptions.BookNotFoundException;
import mk.ukim.finki.elibrary.model.exceptions.CategoryNotFoundException;
import mk.ukim.finki.elibrary.repository.BookRepository;
import mk.ukim.finki.elibrary.repository.CategoryRepository;
import mk.ukim.finki.elibrary.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    public BookServiceImpl(BookRepository bookRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
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
    public Optional<Book> save(String title, String description, String author, Integer year, Long categoryId) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId));
        Book book = new Book(title, description, author, year, category);
        this.bookRepository.save(book);
        return Optional.of(book);
    }

    @Override
    public Optional<Book> edit(Long id, String title, String description, String author, Integer year, Long categoryId) {
        Book book = this.bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
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
    public List<Book> filterByAuthorAndCategory(String author, Category category) {
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
