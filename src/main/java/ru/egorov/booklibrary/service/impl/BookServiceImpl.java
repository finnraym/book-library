package ru.egorov.booklibrary.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.egorov.booklibrary.domain.entity.Author;
import ru.egorov.booklibrary.domain.entity.Book;
import ru.egorov.booklibrary.domain.entity.Genre;
import ru.egorov.booklibrary.exception.DataAlreadyExistsException;
import ru.egorov.booklibrary.exception.DataNotFoundException;
import ru.egorov.booklibrary.repository.BookRepository;
import ru.egorov.booklibrary.service.AuthorService;
import ru.egorov.booklibrary.service.BookService;
import ru.egorov.booklibrary.service.GenreService;
import ru.egorov.booklibrary.web.response.DataResponse;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final GenreService genreService;

    @Transactional(readOnly = true)
    @Override
    public Book getById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Book with id " + id + " not found!"));
    }

    @Transactional
    @Override
    public Book saveNewBook(Book book) {
        return bookRepository.save(book);
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

    @Transactional(readOnly = true)
    @Override
    public DataResponse<Book> getAll(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Book> pagesOfBook = bookRepository.findAll(pageable);

        return DataResponse.<Book>builder()
                .data(pagesOfBook.getContent())
                .last(pagesOfBook.isLast())
                .pageNo(pagesOfBook.getNumber())
                .pageSize(pagesOfBook.getSize())
                .totalPages(pagesOfBook.getTotalPages())
                .totalElements(pagesOfBook.getTotalElements())
                .build();
    }

    @Transactional
    @Override
    public boolean addGenreForBook(Long bookId, Long genreId) {
        boolean result = true;
        Book updatedBook = getById(bookId);

        Set<Genre> bookGenres = updatedBook.getGenres();

        Genre genre = genreService.getById(genreId);
        if (!bookGenres.contains(genre)) {
            return bookGenres.add(genre);
        } else {
            throw new DataAlreadyExistsException("Genre with id  " + genreId + " is already attached to book with id " + bookId);
        }
    }

    @Transactional
    @Override
    public boolean addAuthorForBook(Long bookId, Long authorId) {
        Book updatedBook = getById(bookId);

        Set<Author> bookAuthors = updatedBook.getAuthors();

        Author author = authorService.getById(authorId);
        if (!bookAuthors.contains(author)) {
            return bookAuthors.add(author);
        } else {
            throw new DataAlreadyExistsException("Author with id  " + authorId + " is already attached to book with id " + bookId);
        }
    }
}
