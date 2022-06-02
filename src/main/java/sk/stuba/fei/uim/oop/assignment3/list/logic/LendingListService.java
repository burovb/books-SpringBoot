package sk.stuba.fei.uim.oop.assignment3.list.logic;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import sk.stuba.fei.uim.oop.assignment3.book.data.Book;
import sk.stuba.fei.uim.oop.assignment3.book.logic.BookService;
import sk.stuba.fei.uim.oop.assignment3.exceptions.BadRequestException;
import sk.stuba.fei.uim.oop.assignment3.exceptions.NotFoundException;
import sk.stuba.fei.uim.oop.assignment3.list.data.ILendingListRepository;
import sk.stuba.fei.uim.oop.assignment3.list.data.LendingList;
import sk.stuba.fei.uim.oop.assignment3.list.web.bodies.BookIdRequest;

import java.util.List;

@Service
public class LendingListService implements ILendingListService {
    @Getter
    private final ILendingListRepository repository;
    @Autowired
    private BookService bookService;

    @Autowired
    public LendingListService(ILendingListRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<LendingList> getAll() {
        return this.repository.findAll();
    }

    @Override
    public LendingList create() {
        return this.repository.save(new LendingList());
    }

    @Override
    public LendingList getById(Long id) throws NotFoundException {
        return this.repository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public void removeById(Long id) throws NotFoundException {
        LendingList list = this.repository.findById(id).orElseThrow(NotFoundException::new);
        if (list.isLended()) {
            for (var book: list.getList()) {
                book.setLendCount(book.getLendCount() - 1);
            }
        }

        try {
            this.repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException();
        }
    }

    @Override
    public LendingList addBookById(Long id, BookIdRequest request) throws NotFoundException, BadRequestException {
        LendingList list = this.repository.findById(id).orElseThrow(NotFoundException::new);
        Book b = this.bookService.getRepository().findById(request.getId()).orElseThrow(NotFoundException::new);
        if (list.isLended() || list.getList().contains(b)) {
            throw new BadRequestException();
        } else {
            list.getList().add(b);
        }

        return list;
    }

    @Override
    public void removeBookById(Long id, BookIdRequest request) throws NotFoundException {
        LendingList list = this.repository.findById(id).orElseThrow(NotFoundException::new);
        Book b = this.bookService.getRepository().findById(request.getId()).orElseThrow(NotFoundException::new);

        list.getList().remove(b);
    }

    @Override
    public void lendById(Long id) throws NotFoundException, BadRequestException {
        LendingList list = this.repository.findById(id).orElseThrow(NotFoundException::new);

        if (!list.isLended()) {
            list.setLended(true);
            for (var book: list.getList()) {
                book.setLendCount(book.getLendCount() + 1);
            }
        } else {
            throw new BadRequestException();
        }
    }
}
