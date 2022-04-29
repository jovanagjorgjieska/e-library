package mk.ukim.finki.elibrary.web.controller;

import mk.ukim.finki.elibrary.model.Author;
import mk.ukim.finki.elibrary.model.Book;
import mk.ukim.finki.elibrary.model.Category;
import mk.ukim.finki.elibrary.service.AuthorService;
import mk.ukim.finki.elibrary.service.BookService;
import mk.ukim.finki.elibrary.service.CategoryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    public BookController(BookService bookService, AuthorService authorService, CategoryService categoryService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String getBookPage(@RequestParam(required = false) String error, Model model) {
        if(error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<Book> books = this.bookService.findAll();
        model.addAttribute("books", books);
        model.addAttribute("bodyContent", "books");
        return "master-template";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        this.bookService.deleteById(id);
        return "redirect:/books";
    }

    @GetMapping("/edit-form/{id}")
    public String editBookPage(@PathVariable Long id, Model model) {
        if (this.bookService.findById(id).isPresent()) {
            Book book = this.bookService.findById(id).get();
            List<Author> authors = this.authorService.findAll();
            List<Category> categories = this.categoryService.findAll();
            model.addAttribute("authors", authors);
            model.addAttribute("categories", categories);
            model.addAttribute("book", book);
            model.addAttribute("bodyContent", "add-book");
            return "master-template";
        }
        return "redirect:/books?error=BookNotFound";
    }

    @GetMapping("/add-form")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addBookPage(Model model) {
        List<Author> authors = this.authorService.findAll();
        List<Category> categories = this.categoryService.findAll();
        model.addAttribute("authors", authors);
        model.addAttribute("categories", categories);
        model.addAttribute("bodyContent", "add-book");
        return "master-template";
    }

    @PostMapping("/add")
    public String saveBook(
            @RequestParam(required = false) Long id,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam Long author,
            @RequestParam Integer year,
            @RequestParam Long category) {
        if (id != null) {
            this.bookService.edit(id, title, description, author, year, category);
        } else {
            this.bookService.save(title, description, author, year, category);
        }
        return "redirect:/books";
    }
}
