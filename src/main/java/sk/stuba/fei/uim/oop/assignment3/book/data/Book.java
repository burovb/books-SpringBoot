package sk.stuba.fei.uim.oop.assignment3.book.data;

import lombok.Data;
import lombok.NoArgsConstructor;
import sk.stuba.fei.uim.oop.assignment3.author.data.Author;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name, description;

    @ManyToOne
    private Author author;

    private Integer pages, amount, lendCount;
}
