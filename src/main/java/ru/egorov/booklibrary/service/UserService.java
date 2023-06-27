package ru.egorov.booklibrary.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.egorov.booklibrary.domain.entity.User;

public interface UserService extends UserDetailsService {
    User getById(Long id);
    User getByUsername(String username);
    User saveNewUser(User user);
    User updateUser(User user);
    void deleteById(Long id);
}
