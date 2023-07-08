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
import ru.egorov.booklibrary.domain.entity.Genre;
import ru.egorov.booklibrary.exception.DataNotFoundException;
import ru.egorov.booklibrary.repository.AuthorRepository;
import ru.egorov.booklibrary.repository.GenreRepository;
import ru.egorov.booklibrary.service.impl.GenreServiceImpl;
import ru.egorov.booklibrary.web.response.DataResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class GenreServiceTest {

    @Mock
    private GenreRepository genreRepository;

    private GenreService genreService;

    AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        genreService = new GenreServiceImpl(genreRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void testGetById_Found() {
        mock(GenreRepository.class);

        Genre genre = new Genre(1L, "directory");

        when(genreRepository.findById(1L)).thenReturn(Optional.of(genre));
        assertThat(genreService.getById(1L)).isEqualTo(genre);
    }

    @Test
    void testGetById_NotFound() {
        mock(GenreRepository.class);

        Genre genre = new Genre(1L, "directory");

        when(genreRepository.findById(1L)).thenReturn(Optional.of(genre));
        assertThrows(DataNotFoundException.class, () -> genreService.getById(2L), "Genre with id 2 not found.");
    }

    @Test
    void testSaveNewAuthor() {
        mock(GenreRepository.class);

        Genre genre = new Genre(1L, "directory");

        when(genreRepository.save(genre)).thenReturn(genre);
        assertThat(genreService.saveNewGenre(genre)).isEqualTo(genre);
    }

    @Test
    void testUpdateAuthor() {
        mock(GenreRepository.class);

        Genre genre = new Genre(1L, "directory");

        when(genreRepository.findById(genre.getId())).thenReturn(Optional.of(genre));
        assertThat(genreService.updateGenre(genre)).isEqualTo(genre);
    }

    @Test
    void testDeleteAuthorById() {
        mock(GenreRepository.class, CALLS_REAL_METHODS);

        doAnswer(Answers.CALLS_REAL_METHODS).when(genreRepository).deleteById(any());
    }

    @Test
    void testGetAll() {
        mock(AuthorRepository.class);
        List<Genre> content = List.of(
                new Genre(1L, "directory"),
                new Genre(2L, "monograph"),
                new Genre(3L, "encyclopedia"),
                new Genre(4L, "textbook")
        );

        Pageable pageable = PageRequest.of(0, 4, Sort.by("id"));
        PageImpl<Genre> page = new PageImpl<>(content);

        when(genreRepository.findAll(pageable)).thenReturn(page);

        DataResponse<Genre> expectedResponse = DataResponse.<Genre>builder()
                .data(page.getContent())
                .pageNo(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .build();

        DataResponse<Genre> realResponse = genreService.getAll(0, 4, "id", "asc");

        assertThat(realResponse.getPageSize()).isEqualTo(expectedResponse.getPageSize());
        assertIterableEquals(realResponse.getData(), expectedResponse.getData());
        assertThat(realResponse.getPageNo()).isEqualTo(expectedResponse.getPageNo());
        assertThat(realResponse.getTotalElements()).isEqualTo(expectedResponse.getTotalElements());
    }

    @Test
    void testGetAllByName() {
        mock(AuthorRepository.class);
        List<Genre> data = List.of(
                new Genre(4L, "textbook")
        );

        List<Genre> expectedResult = new ArrayList<>(data);

        when(genreRepository.findByNameContainingIgnoreCase("text", Sort.by("name"))).thenReturn(data);
        List<Genre> result = genreService.getByName("text");
        assertIterableEquals(expectedResult, result);
    }
}
