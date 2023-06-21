package ru.egorov.booklibrary.repository;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
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

}
