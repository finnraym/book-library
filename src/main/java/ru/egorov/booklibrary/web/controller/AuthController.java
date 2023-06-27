package ru.egorov.booklibrary.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.egorov.booklibrary.domain.entity.User;
import ru.egorov.booklibrary.security.jwt.JwtRequest;
import ru.egorov.booklibrary.security.jwt.JwtResponse;
import ru.egorov.booklibrary.service.JwtAuthService;
import ru.egorov.booklibrary.service.UserService;
import ru.egorov.booklibrary.web.dto.UserDto;
import ru.egorov.booklibrary.web.mapper.UserMapper;
import ru.egorov.booklibrary.web.validation.OnCreate;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {
    private final JwtAuthService jwtAuthService;
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/login")
    public JwtResponse login(@Validated @RequestBody JwtRequest request) {
        return jwtAuthService.login(request);
    }

    @PostMapping("/register")
    public UserDto register(@Validated(OnCreate.class) @RequestBody UserDto userDto) {
        if (!userDto.getPassword().equals(userDto.getConfirmedPassword())) {
            throw new IllegalArgumentException("Password and confirmedPassword not equals!");
        }
        User user = userMapper.toEntity(userDto);
        User createdUser = userService.saveNewUser(user);
        return userMapper.toDto(createdUser);
    }

    @PostMapping("/refresh")
    public JwtResponse refresh(@RequestBody String refreshToken) {
        return jwtAuthService.refresh(refreshToken);
    }
}
