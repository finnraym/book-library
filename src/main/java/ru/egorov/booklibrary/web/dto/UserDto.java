package ru.egorov.booklibrary.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.egorov.booklibrary.web.validation.OnCreate;
import ru.egorov.booklibrary.web.validation.OnUpdate;

@Data
@Schema(description = "User DTO")
public class UserDto {
    @NotNull(message = "Id must be not null.", groups = OnUpdate.class)
    @Schema(description = "User id", example = "1")
    private Long id;
    @Schema(description = "User username", example = "admin@mail.ru")
    @NotNull(message = "Username must be not null", groups = {OnUpdate.class, OnCreate.class})
    @Length(max = 80, message = "Username length must be smaller than 80 symbols.", groups = {OnUpdate.class, OnCreate.class})
    private String username;
    @Schema(description = "User password")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Password must be not null", groups = {OnUpdate.class, OnCreate.class})
    private String password;
    @Schema(description = "User password confirmation")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Password must be not null", groups = OnCreate.class)
    private String confirmedPassword;
}
