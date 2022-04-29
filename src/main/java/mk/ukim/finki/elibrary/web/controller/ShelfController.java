package mk.ukim.finki.elibrary.web.controller;

import mk.ukim.finki.elibrary.model.Shelf;
import mk.ukim.finki.elibrary.model.User;
import mk.ukim.finki.elibrary.service.ShelfService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/shelf")
public class ShelfController {

    private final ShelfService shelfService;

    public ShelfController(ShelfService shelfService) {
        this.shelfService = shelfService;
    }

    @GetMapping
    public String getShelfPage(@RequestParam(required = false) String error,
                                      HttpServletRequest req,
                                      Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        String username = req.getRemoteUser();
        Shelf shelf = this.shelfService.getActiveShelf(username);
        model.addAttribute("books", this.shelfService.listAllBooksInShelf(shelf.getId()));
        model.addAttribute("bodyContent", "shelf");
        return "master-template";
    }

    @PostMapping("/add-book/{id}")
    public String addBookToShelf(@PathVariable Long id, HttpServletRequest req, Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            this.shelfService.addBookToShelf(user.getUsername(), id);
            return "redirect:/shelf";
        } catch (RuntimeException exception) {
            return "redirect:/shelf?error=" + exception.getMessage();
        }
    }
}
