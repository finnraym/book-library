package ru.egorov.booklibrary.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.egorov.booklibrary.domain.entity.Author;
import ru.egorov.booklibrary.domain.entity.Book;
import ru.egorov.booklibrary.domain.entity.Genre;
import ru.egorov.booklibrary.exception.DataAlreadyExistsException;
import ru.egorov.booklibrary.exception.DataNotFoundException;
import ru.egorov.booklibrary.repository.AuthorRepository;
import ru.egorov.booklibrary.repository.BookRepository;
import ru.egorov.booklibrary.service.impl.BookServiceImpl;
import ru.egorov.booklibrary.web.response.DataResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorService authorService;

    @Mock
    private GenreService genreService;
    private BookService bookService;

    AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        bookService = new BookServiceImpl(bookRepository, authorService, genreService);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void testGetById_Found() {
        mock(BookRepository.class);

        Book book = new Book(1L, "Effective Java", 2019, 466, null, null);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        assertThat(bookService.getById(1L)).isEqualTo(book);
    }

    @Test
    void testGetById_NotFound() {
        mock(BookRepository.class);

        Book book = new Book(1L, "Effective Java", 2019, 466, null, null);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        assertThrows(DataNotFoundException.class, () -> bookService.getById(2L), "Book with id 2 not found.");
    }

    @Test
    void testSaveNewBook() {
        mock(BookRepository.class);

        Book book = new Book(1L, "Effective Java", 2019, 466, null, null);

        when(bookRepository.save(book)).thenReturn(book);
        assertThat(bookService.saveNewBook(book)).isEqualTo(book);
    }

    @Test
    void testUpdateBook() {
        mock(BookRepository.class);

        Book book = new Book(1L, "Effective Java", 2019, 466, null, null);
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        assertThat(bookService.updateBook(book)).isEqualTo(book);
    }


    @Test
    void testDeleteBook() {
        mock(BookRepository.class, CALLS_REAL_METHODS);

        doAnswer(Answers.CALLS_REAL_METHODS).when(bookRepository).deleteById(any());
    }

    @Test
    void testGetAll() {
        mock(BookRepository.class);
        List<Book> content = List.of(
                new Book(1L, "Effective Java", 2019, 466, null, null),
                new Book(2L, "Spring in Action", 2022, 456, null, null),
                new Book(3L, "Cloud native Java", 2019, 621, null, null),
                new Book(4L, "Spring Microservices in Action", 2022, 491, null, null)

        );

        Pageable pageable = PageRequest.of(0, 4, Sort.by("id"));
        PageImpl<Book> page = new PageImpl<>(content);

        when(bookRepository.findAll(pageable)).thenReturn(page);

        DataResponse<Book> expectedResponse = DataResponse.<Book>builder()
                .data(page.getContent())
                .pageNo(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .build();

        DataResponse<Book> realResponse = bookService.getAll(0, 4, "id", "asc");

        assertThat(realResponse.getPageSize()).isEqualTo(expectedResponse.getPageSize());
        assertIterableEquals(realResponse.getData(), expectedResponse.getData());
        assertThat(realResponse.getPageNo()).isEqualTo(expectedResponse.getPageNo());
        assertThat(realResponse.getTotalElements()).isEqualTo(expectedResponse.getTotalElements());
    }

    @Test
    void testGetAllByTitle() {
        mock(BookRepository.class);

        List<Book> data = List.of(
                new Book(1L, "Effective Java", 2019, 466, null, null),
                new Book(2L, "Cloud native Java", 2019, 621, null, null)
        );

        List<Book> expectedResult = new ArrayList<>(data);

        when(bookRepository.findAllByTitleContainingIgnoreCase("java", Sort.by("name"))).thenReturn(data);
        List<Book> result = bookService.getAllByTitle("java");
        assertIterableEquals(expectedResult, result);
    }

    @Test
    void testGetAllByYearOfIssue() {
        mock(BookRepository.class);

        List<Book> data = List.of(
                new Book(1L, "Effective Java", 2019, 466, null, null),
                new Book(2L, "Cloud native Java", 2019, 621, null, null)
        );

        List<Book> expectedResult = new ArrayList<>();
        when(bookRepository.findAllByYearOfIssueEquals(2019, Sort.by("id"))).thenReturn(data);
        List<Book> actual = bookService.getAllByYearOfIssue(2019, "equals", "asc");
        assertIterableEquals(expectedResult, actual);
    }

    @Test
    void testGetAllByNumberOfPages() {
        mock(BookRepository.class);

        List<Book> data = List.of(
                new Book(1L, "Effective Java", 2019, 466, null, null)
        );

        List<Book> expectedResult = new ArrayList<>();
        when(bookRepository.findAllByNumberOfPagesEquals(466, Sort.by("id"))).thenReturn(data);
        List<Book> actual = bookService.getAllByNumberOfPages(466, "equals", "asc");
        assertIterableEquals(expectedResult, actual);
    }

    @Test
    void testAddAuthorForBook_SuccessAdded() {
        mock(BookRepository.class);
        mock(AuthorService.class);

        Book book = new Book(1L, "Effective Java", 2019, 466, null, null);
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        Author author = new Author(1L, "Joshua", "Bloch", null);
        when(authorService.getById(1L)).thenReturn(author);

        assertThat(bookService.addAuthorForBook(1L, 1L)).isTrue();
    }

    @Test
    void testAddAuthorForBook_Exception() {
        mock(BookRepository.class);
        mock(AuthorService.class);

        Author author = new Author(1L, "Joshua", "Bloch", null);
        Book book = new Book(1L, "Effective Java", 2019, 466, Set.of(author), null);
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        when(authorService.getById(1L)).thenReturn(author);

        assertThrows(DataAlreadyExistsException.class, () -> bookService.addAuthorForBook(1L, 1L), "Author with id 1 is already attached to book with id 1");
    }

    @Test
    void testAddGenreForBook_SuccessAdded() {
        mock(BookRepository.class);
        mock(GenreService.class);

        Book book = new Book(1L, "Effective Java", 2019, 466, null, null);
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        Genre genre = new Genre(1L, "directory");
        when(genreService.getById(1L)).thenReturn(genre);

        assertThat(bookService.addGenreForBook(1L, 1L)).isTrue();
    }

    @Test
    void testAddGenreForBook_Exception() {
        mock(BookRepository.class);
        mock(GenreService.class);

        Genre genre = new Genre(1L, "directory");
        Book book = new Book(1L, "Effective Java", 2019, 466, null, Set.of(genre));
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        when(genreService.getById(1L)).thenReturn(genre);

        assertThrows(DataAlreadyExistsException.class, () -> bookService.addGenreForBook(1L, 1L), "Genre with id 1 is already attached to book with id 1");
    }

    @Test
    void testGetAllBookByAuthorId() {
        mock(BookRepository.class);

        List<Book> data = List.of(
                new Book(1L, "Effective Java", 2019, 466, null, null)
        );

        when(bookRepository.findAllByAuthorId(1L)).thenReturn(data);
        assertIterableEquals(data, bookService.getAllByAuthorId(1L));
    }

    @Test
    void testGetAllBookByGenreId() {
        mock(BookRepository.class);

        List<Book> data = List.of(
                new Book(1L, "Effective Java", 2019, 466, null, null)
        );

        when(bookRepository.findAllByGenreId(1L)).thenReturn(data);
        assertIterableEquals(data, bookService.getAllByGenreId(1L));
    }
}
