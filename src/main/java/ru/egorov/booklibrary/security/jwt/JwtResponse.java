package ru.egorov.booklibrary.security.jwt;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JwtResponse {

    private Long userId;
    private String username;
    private String accessToken;
    private String refreshToken;
}
