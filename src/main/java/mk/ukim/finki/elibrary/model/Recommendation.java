package mk.ukim.finki.elibrary.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String text;

    @ManyToOne
    private User user;

    public Recommendation() {
    }

    public Recommendation(String title, String text, User user) {
        this.title = title;
        this.text = text;
        this.user = user;
    }
}
