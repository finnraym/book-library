package ru.egorov.booklibrary.repository;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.egorov.booklibrary.domain.entity.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByNameContainingIgnoreCase(@NotNull String name, Sort sort);

    List<Book> findAllByYearOfIssueEquals(Integer year, Sort sort);

    List<Book> findAllByYearOfIssueGreaterThan(Integer year, Sort sort);

    List<Book> findAllByYearOfIssueLessThan(Integer year, Sort sort);

    List<Book> findAllByNumberOfPagesEquals(Integer numberOfPages, Sort sort);
    List<Book> findAllByNumberOfPagesGreaterThan(Integer numberOfPages, Sort sort);
    List<Book> findAllByNumberOfPagesLessThan(Integer numberOfPages, Sort sort);

    @Query(nativeQuery = true, value = "SELECT b.id, b.name, b.number_of_pages, b.year_of_issue FROM book b " +
            "LEFT JOIN book_author ba ON b.id = ba.book_id " +
            "WHERE ba.author_id=:authorId")
    List<Book> findAllByAuthorId(@Param("authorId") Long authorId);

    @Query(nativeQuery = true, value = "SELECT b.id, b.name, b.number_of_pages, b.year_of_issue FROM book b " +
            "LEFT JOIN book_genre bg ON b.id = bg.book_id " +
            "WHERE bg.genre_id=:genreId")
    List<Book> findAllByGenreId(@Param("genreId") Long genreId);

}
