package ru.egorov.booklibrary.web.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.egorov.booklibrary.domain.entity.Author;
import ru.egorov.booklibrary.service.AuthorService;
import ru.egorov.booklibrary.utils.consts.WebConstants;
import ru.egorov.booklibrary.web.dto.AuthorDto;
import ru.egorov.booklibrary.web.mapper.AuthorMapper;
import ru.egorov.booklibrary.web.response.DataResponse;
import ru.egorov.booklibrary.web.validation.OnCreate;
import ru.egorov.booklibrary.web.validation.OnUpdate;

import java.util.List;

@RestController
@RequestMapping("api/v1/author")
@RequiredArgsConstructor
@Validated
public class AuthorController {
    private final AuthorService authorService;
    private final AuthorMapper authorMapper;

    @GetMapping("/{id}")
    public AuthorDto getById(@PathVariable Long id) {
        Author entity = authorService.getById(id);
        return authorMapper.toDto(entity);
    }

    @PostMapping
    public AuthorDto addNewAuthor(@Validated(OnCreate.class) @RequestBody AuthorDto authorDto) {
        Author author = authorMapper.toEntity(authorDto);
        Author entity = authorService.saveNewAuthor(author);
        return authorMapper.toDto(entity);
    }

    @PutMapping
    public AuthorDto updateAuthor(@Validated(OnUpdate.class) @RequestBody AuthorDto authorDto) {
        Author author = authorMapper.toEntity(authorDto);
        Author entity = authorService.updateAuthor(author);
        return authorMapper.toDto(entity);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        authorService.deleteAuthorById(id);
    }

    @GetMapping("/all")
    public DataResponse<AuthorDto> getAll(
            @Valid @RequestParam(value = "pageNo", defaultValue = WebConstants.DEFAULT_PAGE_NUMBER, required = false) @Min(value = 0, message = "Page index must not be less than zero") int pageNo,
            @Valid @RequestParam(value = "pageSize", defaultValue = WebConstants.DEFAULT_PAGE_SIZE, required = false) @Min(value = 1, message = "Page size must not be less than one") int pageSize,
            @RequestParam(value = "sortBy", defaultValue = WebConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = WebConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) { // TODO ConstraintViolationException need handle
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
    public List<AuthorDto> getAllByFirstName(@RequestParam(value = "name", defaultValue = "", required = false) String firstName,
                                             @RequestParam(value = "sortDir", defaultValue = WebConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
        return authorService.getAllByFirstName(firstName, sortDir).stream()
                .map(authorMapper::toDto)
                .toList();
    }

    @GetMapping("/secondName")
    public List<AuthorDto> getAllBySecondName(@RequestParam(value = "name", defaultValue = "", required = false) String secondName,
                                             @RequestParam(value = "sortDir", defaultValue = WebConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
        return authorService.getAllBySecondName(secondName, sortDir).stream()
                .map(authorMapper::toDto)
                .toList();
    }
}
