package ru.egorov.booklibrary.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder(toBuilder = true)
public class BookDto {
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private Integer yearOfIssue;
    @NotNull
    private Integer numberOfPages;

    private Set<AuthorDto> authors;

    private Set<GenreDto> genres;
}
