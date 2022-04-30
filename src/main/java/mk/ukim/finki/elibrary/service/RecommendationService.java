package mk.ukim.finki.elibrary.service;

import mk.ukim.finki.elibrary.model.Recommendation;

import java.util.List;
import java.util.Optional;

public interface RecommendationService {
    List<Recommendation> findAll();

    Optional<Recommendation>findById(Long id);

    Optional<Recommendation> save(String title, String text, String userId);

    Optional<Recommendation> edit(Long id, String title, String text, String userId);

    void deleteById(Long id);
}
