package ru.egorov.booklibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.egorov.booklibrary.domain.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
