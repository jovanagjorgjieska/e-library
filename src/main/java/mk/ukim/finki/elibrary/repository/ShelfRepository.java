package mk.ukim.finki.elibrary.repository;

import mk.ukim.finki.elibrary.model.Shelf;
import mk.ukim.finki.elibrary.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShelfRepository extends JpaRepository<Shelf, Long> {
    Optional<Shelf>findByUser(User user);
}
