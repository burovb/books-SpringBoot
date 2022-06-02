package sk.stuba.fei.uim.oop.assignment3.book.logic;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.stuba.fei.uim.oop.assignment3.author.data.Author;
import sk.stuba.fei.uim.oop.assignment3.author.logic.AuthorService;
import sk.stuba.fei.uim.oop.assignment3.book.data.Book;
import sk.stuba.fei.uim.oop.assignment3.book.data.IBookRepository;
import sk.stuba.fei.uim.oop.assignment3.book.web.bodies.BookAmount;
import sk.stuba.fei.uim.oop.assignment3.book.web.bodies.BookRequest;
import sk.stuba.fei.uim.oop.assignment3.book.web.bodies.BookRequestEdit;
import sk.stuba.fei.uim.oop.assignment3.exceptions.NotFoundException;

import java.util.List;

@Service
public class BookService implements IBookService {
    @Getter
    private IBookRepository repository;
    @Autowired
    private AuthorService authorService;

    @Autowired
    public BookService(IBookRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Book> getAll() {
        return this.repository.findAll();
    }

    @Override
    public Book create(BookRequest request) throws NotFoundException {
        Book b = new Book();

        b.setName(request.getName());
        b.setDescription(request.getDescription());
        b.setPages(request.getPages());
        b.setAmount(request.getAmount());
        b.setLendCount(request.getLendCount());

        Author author = this.authorService.getRepository().findById(request.getAuthor()).orElseThrow(NotFoundException::new);
        author.getBooks().add(b);
        b.setAuthor(author);

        return this.repository.save(b);
    }

    @Override
    public Book getById(Long id) throws NotFoundException {
        return this.repository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public Book update(Long id, BookRequestEdit request) throws NotFoundException {
        Book b = this.getById(id);
        Author a = b.getAuthor();

        var authorId = request.getAuthor();
        if (authorId != null && authorId != 0 && !a.getId().equals(authorId)) {
            a.getBooks().remove(b);
            a = this.authorService.getRepository().findById(authorId).orElseThrow(NotFoundException::new);
        }

        var name = request.getName();
        var description = request.getDescription();
        var pages = request.getPages();

        if (name != null) b.setName(name);
        if (description != null) b.setDescription(description);
        if (pages != null && pages != 0) b.setPages(pages);

        a.getBooks().add(b);
        this.authorService.getRepository().save(a);
        b.setAuthor(a);

        return this.repository.save(b);
    }

    @Override
    public Book removeById(Long id) throws NotFoundException {
        List<Author> list = this.authorService.getRepository().findAll();
        Book book = this.getById(id);
        for (Author author: list) {
            List<Book> bookList = author.getBooks();
            bookList.remove(book);
        }

        this.repository.deleteById(id);
        return book;
    }

    @Override
    public Book addAmount(Long id, BookAmount request) throws NotFoundException {
        Book b = this.repository.findById(id).orElseThrow(NotFoundException::new);
        b.setAmount(b.getAmount() + request.getAmount());

        return this.repository.save(b);
    }

}
