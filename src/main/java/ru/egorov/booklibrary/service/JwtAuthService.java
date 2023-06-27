package ru.egorov.booklibrary.service;

import ru.egorov.booklibrary.security.jwt.JwtRequest;
import ru.egorov.booklibrary.security.jwt.JwtResponse;

public interface JwtAuthService {

    JwtResponse login(JwtRequest request);
    JwtResponse refresh(String refreshToken);

}
