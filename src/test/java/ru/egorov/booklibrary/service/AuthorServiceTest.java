package ru.egorov.booklibrary.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import ru.egorov.booklibrary.domain.entity.Author;
import ru.egorov.booklibrary.exception.DataNotFoundException;
import ru.egorov.booklibrary.repository.AuthorRepository;
import ru.egorov.booklibrary.service.impl.AuthorServiceImpl;
import ru.egorov.booklibrary.web.response.DataResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
public class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;
    private AuthorService authorService;

    AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        authorService = new AuthorServiceImpl(authorRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void testGetById_Found() {
        mock(AuthorRepository.class);

        Author author = new Author(1L, "Joshua", "Bloch", null);

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        assertThat(authorService.getById(1L)).isEqualTo(author);
    }

    @Test
    void testGetById_NotFound() {
        mock(AuthorRepository.class);

        Author author = new Author(1L, "Joshua", "Bloch", null);

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        assertThrows(DataNotFoundException.class, () -> authorService.getById(2L), "Author with id 2 not found.");
    }

    @Test
    void testSaveNewAuthor() {
        mock(AuthorRepository.class);

        Author author = new Author(1L, "Joshua", "Bloch", null);

        when(authorRepository.save(author)).thenReturn(author);
        assertThat(authorService.saveNewAuthor(author)).isEqualTo(author);
    }

    @Test
    void testUpdateAuthor() {
        mock(AuthorRepository.class);

        Author author = new Author(1L, "Joshua", "Bloch", null);

        when(authorRepository.findById(author.getId())).thenReturn(Optional.of(author));
        assertThat(authorService.updateAuthor(author)).isEqualTo(author);
    }

    @Test
    void testDeleteAuthorById() {
        mock(AuthorRepository.class, CALLS_REAL_METHODS);

        doAnswer(Answers.CALLS_REAL_METHODS).when(authorRepository).deleteById(any());
    }

    @Test
    void testGetAll() {
        mock(AuthorRepository.class);
        List<Author> content = List.of(
                new Author(1L, "Joshua", "Bloch", null),
                new Author(2L, "Josh", "Long", null),
                new Author(3L, "John", "Carnell", null),
                new Author(4L, "Lev", "Tolstoy", null)
        );

        Pageable pageable = PageRequest.of(0, 4, Sort.by("id"));
        PageImpl<Author> page = new PageImpl<>(content);

        when(authorRepository.findAll(pageable)).thenReturn(page);

        DataResponse<Author> expectedResponse = DataResponse.<Author>builder()
                .data(page.getContent())
                .pageNo(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .build();

        DataResponse<Author> realResponse = authorService.getAll(0, 4, "id", "asc");

        assertThat(realResponse.getPageSize()).isEqualTo(expectedResponse.getPageSize());
        assertIterableEquals(realResponse.getData(), expectedResponse.getData());
        assertThat(realResponse.getPageNo()).isEqualTo(expectedResponse.getPageNo());
        assertThat(realResponse.getTotalElements()).isEqualTo(expectedResponse.getTotalElements());
    }

    @Test
    void testGetAllByFirstName() {
        mock(AuthorRepository.class);
        List<Author> data = List.of(
                new Author(1L, "Joshua", "Bloch", null),
                new Author(2L, "Josh", "Long", null),
                new Author(3L, "John", "Carnell", null)
        );

        List<Author> expectedResult = new ArrayList<>(data);

        when(authorRepository.findAllByFirstNameContaining("Jo", Sort.by("firstName"))).thenReturn(data);
        List<Author> result = authorService.getAllByFirstName("Jo", "asc");
        assertIterableEquals(expectedResult, result);
    }

    @Test
    void testGetAllBySecondName() {
        mock(AuthorRepository.class);
        List<Author> data = List.of(
                new Author(1L, "Joshua", "Bloch", null)
        );

        List<Author> expectedResult = new ArrayList<>(data);

        when(authorRepository.findAllBySecondNameContaining("lo", Sort.by("secondName"))).thenReturn(data);
        List<Author> result = authorService.getAllBySecondName("lo", "asc");
        assertIterableEquals(expectedResult, result);
    }

}
