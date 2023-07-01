package ru.egorov.booklibrary.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.egorov.booklibrary.web.validation.OnCreate;
import ru.egorov.booklibrary.web.validation.OnUpdate;

import java.util.Objects;

@Data
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Genre DTO")
public class GenreDto {
    @NotNull(message = "Id must be not null.", groups = OnUpdate.class)
    @Schema(description = "Genre id", example = "1")
    private Long id;
    @NotNull(message = "Name must be not null.")
    @Schema(description = "Genre name", example = "directory")
    @Length(max = 60, message = "Genre name must be smaller than 60 symbols.", groups = {OnUpdate.class, OnCreate.class})
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenreDto genreDto = (GenreDto) o;
        return name.equals(genreDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
