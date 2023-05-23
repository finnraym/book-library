package ru.egorov.booklibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.egorov.booklibrary.domain.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
