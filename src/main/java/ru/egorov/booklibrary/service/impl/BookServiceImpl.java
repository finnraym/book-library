package ru.egorov.booklibrary.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.egorov.booklibrary.domain.entity.Author;
import ru.egorov.booklibrary.domain.entity.Book;
import ru.egorov.booklibrary.domain.entity.Genre;
import ru.egorov.booklibrary.exception.DataNotFoundException;
import ru.egorov.booklibrary.repository.BookRepository;
import ru.egorov.booklibrary.service.BookService;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Transactional(readOnly = true)
    @Override
    public Book getById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Book with id " + id + " not found!"));
    }

    @Transactional
    @Override
    public void saveNewBook(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    @Override
    public Book updateBook(Book book) {
        Book updatedBook = getById(book.getId());

        updatedBook.setName(book.getName());
        updatedBook.setYearOfIssue(book.getYearOfIssue());
        updatedBook.setNumberOfPages(book.getNumberOfPages());

        return updatedBook;
    }

    @Transactional
    @Override
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    @Override
    public boolean addGenreForBook(Long bookId, Genre ... genres) {
        boolean result = true;
        Book updatedBook = getById(bookId);

        Set<Genre> bookGenres = updatedBook.getGenres();

        for (Genre genre: genres) {
            if (bookGenres.contains(genre)) {
                result = false;
                continue;
            }
            bookGenres.add(genre);
        }

        updatedBook.setGenres(bookGenres);
        return result;
    }

    @Transactional
    @Override
    public boolean addAuthorForBook(Long bookId, Author ... authors) {
        boolean result = true;
        Book updatedBook = getById(bookId);

        Set<Author> bookAuthors = updatedBook.getAuthors();

        for (Author author: authors) {
            if (bookAuthors.contains(author)) {
                result = false;
                continue;
            }
            bookAuthors.add(author);
        }

        updatedBook.setAuthors(bookAuthors);
        return result;
    }
}
