package sk.stuba.fei.uim.oop.assignment3.list.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.stuba.fei.uim.oop.assignment3.exceptions.BadRequestException;
import sk.stuba.fei.uim.oop.assignment3.exceptions.NotFoundException;
import sk.stuba.fei.uim.oop.assignment3.list.logic.LendingListService;
import sk.stuba.fei.uim.oop.assignment3.list.web.bodies.BookIdRequest;
import sk.stuba.fei.uim.oop.assignment3.list.web.bodies.ListResponse;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/list")
public class LendingListController {
    @Autowired
    private LendingListService service;

    @GetMapping
    public List<ListResponse> getAllLists() {
        return this.service.getAll().stream().map(ListResponse::new).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<ListResponse> addList() {
        return new ResponseEntity<>(new ListResponse(this.service.create()), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ListResponse getListById(@PathVariable("id") Long id) throws NotFoundException {
        return new ListResponse(this.service.getById(id));
    }

    @DeleteMapping("/{id}")
    public void removeListById(@PathVariable("id") Long id) throws NotFoundException {
        this.service.removeById(id);
    }

    @PostMapping("/{id}/add")
    public ListResponse addBookById(@PathVariable("id") Long id, @RequestBody BookIdRequest request) throws NotFoundException, BadRequestException {
        return new ListResponse(this.service.addBookById(id, request));
    }

    @DeleteMapping("/{id}/remove")
    public void removeListById(@PathVariable("id") Long id, @RequestBody BookIdRequest request) throws NotFoundException {
        this.service.removeBookById(id, request);
    }

    @GetMapping("/{id}/lend")
    public void LendListById(@PathVariable("id") Long id) throws NotFoundException, BadRequestException {
        this.service.lendById(id);
    }
}
