package mk.ukim.finki.elibrary.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Shelf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    @ManyToMany
    private List<Book>books;

    public Shelf() {
    }

    public Shelf(User user) {
        this.user = user;
        this.books = new ArrayList<>();
    }
}
