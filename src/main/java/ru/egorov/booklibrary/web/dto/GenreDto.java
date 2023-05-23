package ru.egorov.booklibrary.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
@Builder(toBuilder = true)
public class GenreDto {
    private Long id;
    @NotNull
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenreDto genreDto = (GenreDto) o;
        return Objects.equals(id, genreDto.id) && name.equals(genreDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
