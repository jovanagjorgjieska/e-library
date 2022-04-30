package mk.ukim.finki.elibrary.service;

import mk.ukim.finki.elibrary.model.Book;
import mk.ukim.finki.elibrary.model.Shelf;

import java.util.List;

public interface ShelfService {
    List<Book> listAllBooksInShelf(Long shelfId);

    Shelf getActiveShelf(String username);

    Shelf addBookToShelf(String username, Long bookId);

    Shelf returnBook(String username, Long bookId);
}
