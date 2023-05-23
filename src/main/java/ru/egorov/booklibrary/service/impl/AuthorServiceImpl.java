package ru.egorov.booklibrary.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.egorov.booklibrary.domain.entity.Author;
import ru.egorov.booklibrary.repository.AuthorRepository;
import ru.egorov.booklibrary.service.AuthorService;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    @Override
    public Author getById(Long id) {
        return null;
    }

    @Override
    public void saveNewAuthor(Author author) {

    }

    @Override
    public Author updateAuthor(Author author) {
        return null;
    }

    @Override
    public void deleteAuthorById(Long id) {

    }
}
