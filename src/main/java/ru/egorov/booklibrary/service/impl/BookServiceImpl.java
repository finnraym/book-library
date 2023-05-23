package ru.egorov.booklibrary.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.egorov.booklibrary.domain.entity.Book;
import ru.egorov.booklibrary.repository.BookRepository;
import ru.egorov.booklibrary.service.BookService;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public Book getById(Long id) {
        return null;
    }

    @Override
    public void saveNewBook(Book book) {

    }

    @Override
    public Book updateBook(Book book) {
        return null;
    }

    @Override
    public void deleteBookById(Long id) {

    }
}
