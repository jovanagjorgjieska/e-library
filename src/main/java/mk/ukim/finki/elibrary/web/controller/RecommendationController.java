package mk.ukim.finki.elibrary.web.controller;

import mk.ukim.finki.elibrary.model.*;
import mk.ukim.finki.elibrary.service.RecommendationService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping
    public String getRecommendationPage(@RequestParam(required = false) String error, Model model) {
        if(error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<Recommendation> recommendations = this.recommendationService.findAll();
        model.addAttribute("recommendations", recommendations);
        model.addAttribute("bodyContent", "recommendations");
        return "master-template";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteRecommendation(@PathVariable Long id) {
        this.recommendationService.deleteById(id);
        return "redirect:/recommendations";
    }

    @GetMapping("/edit-recommendation/{id}")
    public String editRecommendationPage(@PathVariable Long id, Model model) {
        if (this.recommendationService.findById(id).isPresent()) {
            Recommendation recommendation = this.recommendationService.findById(id).get();
            model.addAttribute("recommendation", recommendation);
            model.addAttribute("bodyContent", "add-recommendation");
            return "master-template";
        }
        return "redirect:/recommendation?error=RecommendationNotFound";
    }

    @GetMapping("/add-recommendation")
    public String addQuestionPage(Model model) {
        model.addAttribute("bodyContent", "add-recommendation");
        return "master-template";
    }

    @PostMapping("/add-recommendation")
    public String saveQuestion(
            @RequestParam(required = false) Long id,
            @RequestParam String title,
            @RequestParam String text,
            HttpServletRequest req,
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        if (id != null) {
            this.recommendationService.edit(id, title, text, user.getUsername());
        } else {
            this.recommendationService.save(title, text, user.getUsername());
        }
        return "redirect:/recommendations";
    }
}
