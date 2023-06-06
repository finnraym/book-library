package ru.egorov.booklibrary.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ru.egorov.booklibrary.web.validation.OnUpdate;

import java.time.LocalDate;
import java.util.Objects;

@Data
@Builder(toBuilder = true)
public class AuthorDto {

    @NotNull(message = "Id must be not null.", groups = OnUpdate.class)
    private Long id;
    @NotNull(message = "First name must not be null.")
    private String firstName;
    @NotNull(message = "Second name must not be null.")
    private String secondName;
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
