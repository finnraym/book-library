package ru.egorov.booklibrary.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;
import ru.egorov.booklibrary.domain.entity.Genre;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class GenreRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;

    @BeforeAll
    @Sql(scripts = "/sql/clear_db.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(scripts = {"/sql/init_db.sql", "/sql/data.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    static void init() {}

    @Test
    void testFindByNameContainingIgnoreCase_Found() {
        List<Genre> genres = genreRepository.findByNameContainingIgnoreCase("text", Sort.by("id"));

        assertThat(genres.size()).isEqualTo(1);
        assertThat(genres.get(0).getName()).isEqualTo("textbook");
    }

    @Test
    void testFindByNameContainingIgnoreCase_NotFound() {
        List<Genre> genres = genreRepository.findByNameContainingIgnoreCase("rom", Sort.by("id"));
        assertThat(genres.isEmpty()).isTrue();
    }
}
