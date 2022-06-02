package sk.stuba.fei.uim.oop.assignment3.author.logic;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import sk.stuba.fei.uim.oop.assignment3.author.data.Author;
import sk.stuba.fei.uim.oop.assignment3.author.data.IAuthorRepository;
import sk.stuba.fei.uim.oop.assignment3.author.web.bodies.AuthorRequest;
import sk.stuba.fei.uim.oop.assignment3.exceptions.NotFoundException;

import java.util.List;

@Service
public class AuthorService implements IAuthorService {
    @Getter
    private IAuthorRepository repository;

    @Autowired
    public AuthorService(IAuthorRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Author> getAll() {
        return this.repository.findAll();
    }

    @Override
    public Author create(AuthorRequest request) {
        Author a = new Author();

        a.setName(request.getName());
        a.setSurname(request.getSurname());

        return this.repository.save(a);
    }

    @Override
    public Author getById(Long id) throws NotFoundException {
        return this.repository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public Author update(Long id, AuthorRequest request) throws NotFoundException {
        Author a = getById(id);

        var name = request.getName();
        var surname = request.getSurname();

        if (name != null) a.setName(name);
        if (surname != null) a.setSurname(surname);

        return this.repository.save(a);
    }

    @Override
    public void removeById(Long id) throws NotFoundException {
        try {
            this.repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException();
        }
    }
}
