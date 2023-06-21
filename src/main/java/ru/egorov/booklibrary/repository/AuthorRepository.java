package ru.egorov.booklibrary.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.egorov.booklibrary.domain.entity.Author;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    List<Author> findAllByFirstNameContaining(String firstName, Sort sort);
    List<Author> findAllBySecondNameContaining(String secondName, Sort sort);
}
