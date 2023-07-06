package ru.egorov.booklibrary.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;
import ru.egorov.booklibrary.domain.entity.Author;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
public class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @BeforeAll
    @Sql(scripts = "/sql/clear_db.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(scripts = {"/sql/init_db.sql", "/sql/data.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    static void init() {}

    @Test
    void testFindAllByFirstNameContaining_Found() {
        List<Author> authors = authorRepository.findAllByFirstNameContaining("Jo", Sort.by("id"));

        assertThat(authors.size()).isEqualTo(3);

        assertThat(authors.get(0).getFirstName()).isEqualTo("Joshua");
        assertThat(authors.get(0).getSecondName()).isEqualTo("Bloch");

        assertThat(authors.get(1).getFirstName()).isEqualTo("Josh");
        assertThat(authors.get(1).getSecondName()).isEqualTo("Long");

        assertThat(authors.get(2).getFirstName()).isEqualTo("John");
        assertThat(authors.get(2).getSecondName()).isEqualTo("Carnell");
    }

    @Test
    void testFindAllByFirstNameContaining_NotFound() {
        List<Author> authors = authorRepository.findAllByFirstNameContaining("abo", Sort.by("id"));

        assertThat(authors.isEmpty()).isTrue();
    }

    @Test
    void testFindAllBySecondNameContaining_Found() {
        List<Author> authors = authorRepository.findAllBySecondNameContaining("lo", Sort.by("id"));

        assertThat(authors.size()).isEqualTo(1);

        assertThat(authors.get(0).getFirstName()).isEqualTo("Joshua");
        assertThat(authors.get(0).getSecondName()).isEqualTo("Bloch");
    }

    @Test
    void testFindAllBySecondNameContaining_NotFound() {
        List<Author> authors = authorRepository.findAllBySecondNameContaining("abo", Sort.by("id"));

        assertThat(authors.isEmpty()).isTrue();
    }

}
