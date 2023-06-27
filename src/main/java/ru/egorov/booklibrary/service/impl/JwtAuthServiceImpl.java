package ru.egorov.booklibrary.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.egorov.booklibrary.domain.entity.User;
import ru.egorov.booklibrary.security.jwt.JwtRequest;
import ru.egorov.booklibrary.security.jwt.JwtResponse;
import ru.egorov.booklibrary.security.jwt.JwtTokenProvider;
import ru.egorov.booklibrary.service.JwtAuthService;
import ru.egorov.booklibrary.service.UserService;

@Service
@RequiredArgsConstructor
public class JwtAuthServiceImpl implements JwtAuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider tokenProvider;
    @Override
    public JwtResponse login(JwtRequest request) {
        JwtResponse response = new JwtResponse();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        User user = userService.getByUsername(request.getUsername());
        response.setUserId(user.getId());
        response.setUsername(user.getUsername());
        response.setAccessToken(tokenProvider.createAccessToken(user.getId(), user.getUsername(), user.getRole()));
        response.setRefreshToken(tokenProvider.createRefreshToken(user.getId(), user.getUsername()));
        return response;
    }

    @Override
    public JwtResponse refresh(String refreshToken) {
        return tokenProvider.refreshUserTokens(refreshToken);
    }
}
