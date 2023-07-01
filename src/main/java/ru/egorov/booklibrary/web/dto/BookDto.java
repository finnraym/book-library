package ru.egorov.booklibrary.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.egorov.booklibrary.web.validation.OnCreate;
import ru.egorov.booklibrary.web.validation.OnUpdate;

import java.util.Set;

@Data
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Book DTO")
public class BookDto {

    @NotNull(message = "Id must be not null.", groups = OnUpdate.class)
    @Schema(description = "Book id", example = "1")
    private Long id;
    @NotNull(message = "Book's title must not be null.")
    @Schema(description = "Book title", example = "Effective Java")
    @Length(max = 255, message = "Book title length must be smaller than 255 symbols.", groups = {OnUpdate.class, OnCreate.class})
    private String title;
    @Schema(description = "Book year of issue", example = "2019")
    @NotNull(message = "Year of issue must not be null.")
    private Integer yearOfIssue;
    @NotNull(message = "Number of pages must not be null.")
    @Schema(description = "Book number of pages", example = "466")
    private Integer numberOfPages;

    @Schema(description = "Book's authors")
    private Set<AuthorDto> authors;

    @Schema(description = "Book's genres")
    private Set<GenreDto> genres;
}
