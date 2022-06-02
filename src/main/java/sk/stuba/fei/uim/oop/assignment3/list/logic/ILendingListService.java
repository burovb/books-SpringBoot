package sk.stuba.fei.uim.oop.assignment3.list.logic;

import sk.stuba.fei.uim.oop.assignment3.exceptions.BadRequestException;
import sk.stuba.fei.uim.oop.assignment3.exceptions.NotFoundException;
import sk.stuba.fei.uim.oop.assignment3.list.data.LendingList;
import sk.stuba.fei.uim.oop.assignment3.list.web.bodies.BookIdRequest;

import java.util.List;

public interface ILendingListService {
    List<LendingList> getAll();

    LendingList create();

    LendingList getById(Long id) throws NotFoundException;

    void removeById(Long id) throws NotFoundException;

    LendingList addBookById(Long id, BookIdRequest request) throws NotFoundException, BadRequestException;

    void removeBookById(Long id, BookIdRequest request) throws NotFoundException;

    void lendById(Long id) throws NotFoundException, BadRequestException;
}
