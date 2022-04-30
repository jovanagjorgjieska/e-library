package mk.ukim.finki.elibrary.model;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private String author;

    private Integer year;

    @ManyToOne
    private Category category;

    private Integer likes = 0;

    private Integer numTaken = 0;

    public Book() {

    }

    public Book(String title, String description, String author, Integer year, Category category) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.year = year;
        this.category = category;
    }
}
