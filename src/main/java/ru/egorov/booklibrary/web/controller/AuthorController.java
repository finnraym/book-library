package ru.egorov.booklibrary.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.egorov.booklibrary.domain.entity.Author;
import ru.egorov.booklibrary.domain.entity.Book;
import ru.egorov.booklibrary.service.AuthorService;
import ru.egorov.booklibrary.service.BookService;
import ru.egorov.booklibrary.utils.consts.WebConstants;
import ru.egorov.booklibrary.web.dto.AuthorDto;
import ru.egorov.booklibrary.web.dto.BookDto;
import ru.egorov.booklibrary.web.mapper.AuthorMapper;
import ru.egorov.booklibrary.web.mapper.BookMapper;
import ru.egorov.booklibrary.web.response.DataResponse;
import ru.egorov.booklibrary.web.validation.OnCreate;
import ru.egorov.booklibrary.web.validation.OnUpdate;

import java.util.List;

@RestController
@RequestMapping("api/v1/authors")
@RequiredArgsConstructor
@Validated
@Tag(name = "Author controller", description = "Author API")
public class AuthorController {
    private final AuthorService authorService;
    private final AuthorMapper authorMapper;
    private final BookMapper bookMapper;
    private final BookService bookService;

    @GetMapping("/{id}")
    @Operation(summary = "Get author by id")
    public AuthorDto getById(@PathVariable Long id) {
        Author entity = authorService.getById(id);
        return authorMapper.toDto(entity);
    }

    @PostMapping
    @Operation(summary = "Add new author")
    @PreAuthorize("hasRole('ADMIN')")
    public AuthorDto addNewAuthor(@Validated(OnCreate.class) @RequestBody AuthorDto authorDto) {
        Author author = authorMapper.toEntity(authorDto);
        Author entity = authorService.saveNewAuthor(author);
        return authorMapper.toDto(entity);
    }

    @PutMapping
    @Operation(summary = "Update author")
    @PreAuthorize("hasRole('ADMIN')")
    public AuthorDto updateAuthor(@Validated(OnUpdate.class) @RequestBody AuthorDto authorDto) {
        Author author = authorMapper.toEntity(authorDto);
        Author entity = authorService.updateAuthor(author);
        return authorMapper.toDto(entity);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete author")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById(@PathVariable Long id) {
        authorService.deleteAuthorById(id);
    }

    @GetMapping
    @Operation(summary = "Get all authors")
    public DataResponse<AuthorDto> getAll(
            @Valid @RequestParam(value = "pageNo", defaultValue = WebConstants.DEFAULT_PAGE_NUMBER, required = false) @Min(value = 0, message = "Page index must not be less than zero") int pageNo,
            @Valid @RequestParam(value = "pageSize", defaultValue = WebConstants.DEFAULT_PAGE_SIZE, required = false) @Min(value = 1, message = "Page size must not be less than one") int pageSize,
            @RequestParam(value = "sortBy", defaultValue = WebConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = WebConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
        DataResponse<Author> allAuthors = authorService.getAll(pageNo, pageSize, sortBy, sortDir);

        DataResponse<AuthorDto> response = DataResponse.<AuthorDto>builder()
                .pageNo(allAuthors.getPageNo())
                .pageSize(allAuthors.getPageSize())
                .totalPages(allAuthors.getTotalPages())
                .totalElements(allAuthors.getTotalElements())
                .last(allAuthors.isLast())
                .build();

        List<AuthorDto> dtos = allAuthors.getData().stream()
                .map(authorMapper::toDto)
                .toList();

        response.setData(dtos);

        return response;
    }

    @GetMapping("/firstName")
    @Operation(summary = "Get all authors by first name")
    public List<AuthorDto> getAllByFirstName(@RequestParam(value = "name", defaultValue = "", required = false) String firstName,
                                             @RequestParam(value = "sortDir", defaultValue = WebConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
        return authorService.getAllByFirstName(firstName, sortDir).stream()
                .map(authorMapper::toDto)
                .toList();
    }

    @GetMapping("/secondName")
    @Operation(summary = "Get all authors by second name")
    public List<AuthorDto> getAllBySecondName(@RequestParam(value = "name", defaultValue = "", required = false) String secondName,
                                             @RequestParam(value = "sortDir", defaultValue = WebConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
        return authorService.getAllBySecondName(secondName, sortDir).stream()
                .map(authorMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}/books")
    @Operation(summary = "Get all books by author id")
    public List<BookDto> getBooksByAuthorId(@PathVariable Long id) {
        List<Book> books = bookService.getAllByAuthorId(id);
        return books.stream()
                .map(bookMapper::toDtoWithoutAuthorsAndGenres)
                .toList();
    }
}
