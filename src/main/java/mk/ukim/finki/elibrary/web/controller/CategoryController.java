package mk.ukim.finki.elibrary.web.controller;

import mk.ukim.finki.elibrary.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String getCategoryPage(Model model){
        model.addAttribute("categories", this.categoryService.findAll());
        model.addAttribute("bodyContent", "categories");
        return "master-template";
    }
}
