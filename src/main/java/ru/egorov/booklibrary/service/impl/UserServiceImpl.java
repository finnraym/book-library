package ru.egorov.booklibrary.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.egorov.booklibrary.domain.entity.Role;
import ru.egorov.booklibrary.domain.entity.User;
import ru.egorov.booklibrary.exception.DataAlreadyExistsException;
import ru.egorov.booklibrary.exception.DataNotFoundException;
import ru.egorov.booklibrary.repository.UserRepository;
import ru.egorov.booklibrary.service.UserService;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User entity = getByUsername(username);

        return new org.springframework.security.core.userdetails.User(
                entity.getUsername(),
                entity.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(entity.getRole().toString()))
        );
    }

    @Transactional(readOnly = true)
    @Override
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("User with id " + id + " not found!"));
    }

    @Transactional(readOnly = true)
    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new DataNotFoundException("User with username " + username + " not found!"));
    }

    @Transactional
    @Override
    public User saveNewUser(User user) {
        existsByUsername(user.getUsername());

        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(Role.ROLE_USER);

        return userRepository.save(user);
    }

    @Transactional
    @Override
    public User updateUser(User user) {
        User entity = getById(user.getId());

        if (!entity.getUsername().equals(user.getUsername())) {
            existsByUsername(user.getUsername());
            entity.setUsername(user.getUsername());
        }

        entity.setRole(user.getRole());
        entity.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(entity);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    private void existsByUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new DataAlreadyExistsException("User with username " + username + " already exists!");
        }
    }
}
