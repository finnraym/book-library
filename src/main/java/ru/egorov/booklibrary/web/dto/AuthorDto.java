package ru.egorov.booklibrary.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import ru.egorov.booklibrary.web.validation.OnCreate;
import ru.egorov.booklibrary.web.validation.OnUpdate;

import java.time.LocalDate;
import java.util.Objects;

@Data
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Author DTO")
public class AuthorDto {

    @Schema(description = "Author id", example = "1")
    @NotNull(message = "Id must be not null.", groups = OnUpdate.class)
    private Long id;
    @Schema(description = "Author first name", example = "Joshua")
    @NotNull(message = "First name must not be null.")
    @Length(max = 255, message = "Author first name length must be smaller than 255 symbols.", groups = {OnUpdate.class, OnCreate.class})
    private String firstName;
    @Schema(description = "Author second name", example = "Bloch")
    @NotNull(message = "Second name must not be null.")
    @Length(max = 255, message = "Author second name length must be smaller than 255 symbols.", groups = {OnUpdate.class, OnCreate.class})
    private String secondName;
    @Schema(description = "Author date of birth", example = "1961-08-28")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorDto authorDto = (AuthorDto) o;
        return Objects.equals(id, authorDto.id) && firstName.equals(authorDto.firstName) && secondName.equals(authorDto.secondName) && Objects.equals(dateOfBirth, authorDto.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, secondName, dateOfBirth);
    }
}
