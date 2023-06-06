package ru.egorov.booklibrary.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.egorov.booklibrary.domain.entity.Author;
import ru.egorov.booklibrary.exception.DataNotFoundException;
import ru.egorov.booklibrary.repository.AuthorRepository;
import ru.egorov.booklibrary.service.AuthorService;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    @Transactional(readOnly = true)
    @Override
    public Author getById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Author with id " + id + " not found."));
    }

    @Transactional
    @Override
    public Author saveNewAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Transactional
    @Override
    public Author updateAuthor(Author author) {
        Author updatedAuthor = getById(author.getId());

        updatedAuthor.setFirstName(author.getFirstName());
        updatedAuthor.setSecondName(author.getSecondName());

        if (Objects.nonNull(author.getDateOfBirth()))
            updatedAuthor.setDateOfBirth(author.getDateOfBirth());

        return updatedAuthor;
    }

    @Transactional
    @Override
    public void deleteAuthorById(Long id) {
        authorRepository.deleteById(id);
    }
}
