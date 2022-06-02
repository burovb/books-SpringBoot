package sk.stuba.fei.uim.oop.assignment3.list.data;

import lombok.Data;
import sk.stuba.fei.uim.oop.assignment3.book.data.Book;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class LendingList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    private List<Book> list;

    private boolean lended;

    public LendingList() {
        this.list = new ArrayList<>();
    }
}
