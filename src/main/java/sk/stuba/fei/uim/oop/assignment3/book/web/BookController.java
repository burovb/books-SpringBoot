package sk.stuba.fei.uim.oop.assignment3.book.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.stuba.fei.uim.oop.assignment3.book.logic.BookService;
import sk.stuba.fei.uim.oop.assignment3.book.web.bodies.BookAmount;
import sk.stuba.fei.uim.oop.assignment3.book.web.bodies.BookRequest;
import sk.stuba.fei.uim.oop.assignment3.book.web.bodies.BookRequestEdit;
import sk.stuba.fei.uim.oop.assignment3.book.web.bodies.BookResponse;
import sk.stuba.fei.uim.oop.assignment3.exceptions.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService service;

    @GetMapping
    public List<BookResponse> getAllBooks() {
        return this.service.getAll().stream().map(BookResponse::new).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<BookResponse> addBook(@RequestBody BookRequest request) throws NotFoundException {
        return new ResponseEntity<>(new BookResponse(this.service.create(request)), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public BookResponse getBookById(@PathVariable("id") Long id) throws NotFoundException {
        return new BookResponse(this.service.getById(id));
    }

    @PutMapping("/{id}")
    public BookResponse updateBookById(@PathVariable("id") Long id, @RequestBody(required = false) BookRequestEdit request) throws NotFoundException {
        return new BookResponse(this.service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public void removeBookById(@PathVariable("id") Long id) throws NotFoundException {
        this.service.removeById(id);
    }

    @GetMapping("/{id}/amount")
    public BookAmount getAmountById(@PathVariable("id") Long id) throws NotFoundException {
        return new BookAmount(this.getBookById(id).getAmount());
    }

    @PostMapping("/{id}/amount")
    public BookAmount addAmountById(@PathVariable("id") Long id, @RequestBody BookAmount request) throws NotFoundException {
        return new BookAmount(this.service.addAmount(id, request).getAmount());
    }

    @GetMapping("/{id}/lendCount")
    public BookAmount getLendCountById(@PathVariable("id") Long id) throws NotFoundException {
        return new BookAmount(this.service.getById(id).getLendCount());
    }
}
