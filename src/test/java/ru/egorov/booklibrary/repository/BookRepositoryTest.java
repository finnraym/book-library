package ru.egorov.booklibrary.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;
import ru.egorov.booklibrary.domain.entity.Book;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @BeforeAll
    @Sql(scripts = "/sql/clear_db.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(scripts = {"/sql/init_db.sql", "/sql/data.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    static void init() {}


    @Test
    void testFindAllByTitleContainingIgnoreCase_Found() {
        List<Book> books = bookRepository.findAllByTitleContainingIgnoreCase("jaVa", Sort.by("id"));

        assertThat(books.size()).isEqualTo(2);

        assertThat(books.get(0).getTitle()).isEqualTo("Effective Java");
        assertThat(books.get(1).getTitle()).isEqualTo("Cloud native Java");
    }

    @Test
    void testFindAllByTitleContainingIgnoreCase_NotFound() {
        List<Book> books = bookRepository.findAllByTitleContainingIgnoreCase("python", Sort.by("id"));

        assertThat(books.isEmpty()).isTrue();
    }

    @Test
    void testFindAllByYearOfIssueEquals_Found() {
        List<Book> books = bookRepository.findAllByYearOfIssueEquals(2019, Sort.by("id"));

        assertThat(books.size()).isEqualTo(2);

        assertThat(books.get(0).getTitle()).isEqualTo("Effective Java");
        assertThat(books.get(1).getTitle()).isEqualTo("Cloud native Java");
    }

    @Test
    void testFindAllByYearOfIssueEquals_NotFound() {
        List<Book> books = bookRepository.findAllByYearOfIssueEquals(2020, Sort.by("id"));

        assertThat(books.isEmpty()).isTrue();
    }
    @Test
    void testFindAllByYearOfIssueGreaterThan_Found() {
        List<Book> books = bookRepository.findAllByYearOfIssueGreaterThan(2019, Sort.by("id"));

        assertThat(books.size()).isEqualTo(2);

        assertThat(books.get(0).getTitle()).isEqualTo("Spring in Action");
        assertThat(books.get(1).getTitle()).isEqualTo("Spring Microservices in Action");
    }

    @Test
    void testFindAllByYearOfIssueGreaterThan_NotFound() {
        List<Book> books = bookRepository.findAllByYearOfIssueGreaterThan(2022, Sort.by("id"));
        assertThat(books.isEmpty()).isTrue();
    }

    @Test
    void testFindAllByYearOfIssueLessThan_Found() {
        List<Book> books = bookRepository.findAllByYearOfIssueLessThan(2022, Sort.by("id"));

        assertThat(books.size()).isEqualTo(2);

        assertThat(books.get(0).getTitle()).isEqualTo("Effective Java");
        assertThat(books.get(1).getTitle()).isEqualTo("Cloud native Java");
    }

    @Test
    void testFindAllByYearOfIssueLessThan_NotFound() {
        List<Book> books = bookRepository.findAllByYearOfIssueLessThan(2019, Sort.by("id"));
        assertThat(books.isEmpty()).isTrue();
    }

    @Test
    void testFindAllByAuthorId_Found() {
        List<Book> books = bookRepository.findAllByAuthorId(1L);

        assertThat(books.size()).isEqualTo(1);

        assertThat(books.get(0).getTitle()).isEqualTo("Effective Java");
    }

    @Test
    void testFindAllByAuthorId_NotFound() {
        List<Book> books = bookRepository.findAllByAuthorId(15L);
        assertThat(books.isEmpty()).isTrue();
    }

    @Test
    void testFindAllByGenreId_Found() {
        List<Book> books = bookRepository.findAllByGenreId(3L);

        assertThat(books.size()).isEqualTo(3);

        assertThat(books.get(0).getTitle()).isEqualTo("Spring in Action");
        assertThat(books.get(1).getTitle()).isEqualTo("Cloud native Java");
        assertThat(books.get(2).getTitle()).isEqualTo("Spring Microservices in Action");
    }

    @Test
    void testFindAllByGenreId_NotFound() {
        List<Book> books = bookRepository.findAllByGenreId(4L);

        assertThat(books.isEmpty()).isTrue();
    }

}
