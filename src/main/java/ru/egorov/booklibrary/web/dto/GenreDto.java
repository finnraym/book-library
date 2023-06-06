package ru.egorov.booklibrary.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import ru.egorov.booklibrary.web.validation.OnUpdate;

import java.util.Objects;

@Data
@Builder(toBuilder = true)
public class GenreDto {
    @NotNull(message = "Id must be not null.", groups = OnUpdate.class)
    private Long id;
    @NotNull(message = "Name must be not null.")
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
