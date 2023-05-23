package ru.egorov.booklibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.egorov.booklibrary.domain.entity.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
