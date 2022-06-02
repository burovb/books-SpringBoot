package sk.stuba.fei.uim.oop.assignment3.book.web.bodies;

import lombok.Getter;

@Getter
public class BookRequestEdit {
    private String name, description;

    private Long author;

    private Integer pages;
}
