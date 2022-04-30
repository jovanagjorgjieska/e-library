package mk.ukim.finki.elibrary.service.impl;

import mk.ukim.finki.elibrary.model.Recommendation;
import mk.ukim.finki.elibrary.model.User;
import mk.ukim.finki.elibrary.model.exceptions.RecommendationNotFoundException;
import mk.ukim.finki.elibrary.model.exceptions.UserNotFoundException;
import mk.ukim.finki.elibrary.repository.RecommendationRepository;
import mk.ukim.finki.elibrary.repository.UserRepository;
import mk.ukim.finki.elibrary.service.RecommendationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecommendationServiceImpl implements RecommendationService {

    private final RecommendationRepository recommendationRepository;
    private final UserRepository userRepository;

    public RecommendationServiceImpl(RecommendationRepository recommendationRepository, UserRepository userRepository) {
        this.recommendationRepository = recommendationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Recommendation> findAll() {
        return this.recommendationRepository.findAll();
    }

    @Override
    public Optional<Recommendation> findById(Long id) {
        return this.recommendationRepository.findById(id);
    }

    @Override
    public Optional<Recommendation> save(String title, String text, String userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Recommendation recommendation = new Recommendation(title, text, user);
        this.recommendationRepository.save(recommendation);
        return Optional.of(recommendation);
    }

    @Override
    public Optional<Recommendation> edit(Long id, String title, String text, String userId) {
        Recommendation recommendation = this.recommendationRepository.findById(id).orElseThrow(() -> new RecommendationNotFoundException(id));
        User user = this.userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        recommendation.setTitle(title);
        recommendation.setText(text);
        recommendation.setUser(user);
        this.recommendationRepository.save(recommendation);
        return Optional.of(recommendation);
    }


    @Override
    public void deleteById(Long id) {
        this.recommendationRepository.deleteById(id);
    }
}
