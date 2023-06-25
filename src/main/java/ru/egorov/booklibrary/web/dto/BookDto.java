package ru.egorov.booklibrary.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import ru.egorov.booklibrary.web.validation.OnUpdate;

import java.util.Set;

@Data
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookDto {

    @NotNull(message = "Id must be not null.", groups = OnUpdate.class)
    private Long id;
    @NotNull(message = "Book's title must not be null.")
    private String title;
    @NotNull(message = "Year of issue must not be null.")
    private Integer yearOfIssue;
    @NotNull(message = "Number of pages must not be null.")
    private Integer numberOfPages;


    private Set<AuthorDto> authors;

    private Set<GenreDto> genres;
}
