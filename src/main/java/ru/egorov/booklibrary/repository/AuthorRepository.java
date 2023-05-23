package ru.egorov.booklibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.egorov.booklibrary.domain.entity.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
