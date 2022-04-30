package mk.ukim.finki.elibrary.repository;

import mk.ukim.finki.elibrary.model.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
}
