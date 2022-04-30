package mk.ukim.finki.elibrary.service.impl;

import mk.ukim.finki.elibrary.model.Book;
import mk.ukim.finki.elibrary.model.Shelf;
import mk.ukim.finki.elibrary.model.User;
import mk.ukim.finki.elibrary.model.exceptions.BookAlreadyInShelfException;
import mk.ukim.finki.elibrary.model.exceptions.BookNotFoundException;
import mk.ukim.finki.elibrary.model.exceptions.ShelfNotFoundException;
import mk.ukim.finki.elibrary.model.exceptions.UserNotFoundException;
import mk.ukim.finki.elibrary.repository.ShelfRepository;
import mk.ukim.finki.elibrary.repository.UserRepository;
import mk.ukim.finki.elibrary.service.BookService;
import mk.ukim.finki.elibrary.service.ShelfService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShelfServiceImpl implements ShelfService {

    private final ShelfRepository shelfRepository;
    private final BookService bookService;
    private final UserRepository userRepository;

    public ShelfServiceImpl(ShelfRepository shelfRepository, BookService bookService, UserRepository userRepository) {
        this.shelfRepository = shelfRepository;
        this.bookService = bookService;
        this.userRepository = userRepository;
    }

    @Override
    public List<Book> listAllBooksInShelf(Long shelfId) {
        if(!this.shelfRepository.findById(shelfId).isPresent())
            throw new ShelfNotFoundException();
        return this.shelfRepository.findById(shelfId).get().getBooks();
    }

    @Override
    public Shelf getActiveShelf(String username) {
        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        return this.shelfRepository.findByUser(user)
                .orElseGet(() -> {
                    Shelf shelf = new Shelf(user);
                    return this.shelfRepository.save(shelf);
                });
    }

    @Override
    public Shelf addBookToShelf(String username, Long bookId) {
        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        Shelf shelf = this.shelfRepository.findByUser(user).orElseThrow(() -> new ShelfNotFoundException());
        Book book = this.bookService.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
        if(shelf.getBooks()
                .stream().filter(i -> i.getId().equals(bookId))
                .collect(Collectors.toList()).size() > 0)
            throw new BookAlreadyInShelfException(bookId, username);
        shelf.getBooks().add(book);
        return this.shelfRepository.save(shelf);
    }

    @Override
    public Shelf returnBook(String username, Long bookId) {
        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        Shelf shelf = this.shelfRepository.findByUser(user).orElseThrow(() -> new ShelfNotFoundException());
        Book book = this.bookService.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
        shelf.getBooks().remove(book);
        return this.shelfRepository.save(shelf);
    }
}
