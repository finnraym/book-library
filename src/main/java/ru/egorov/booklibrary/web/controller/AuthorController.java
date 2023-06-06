package ru.egorov.booklibrary.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.egorov.booklibrary.domain.entity.Author;
import ru.egorov.booklibrary.service.AuthorService;
import ru.egorov.booklibrary.web.dto.AuthorDto;
import ru.egorov.booklibrary.web.mapper.AuthorMapper;
import ru.egorov.booklibrary.web.validation.OnCreate;
import ru.egorov.booklibrary.web.validation.OnUpdate;

@RestController
@RequestMapping("api/v1/author")
@RequiredArgsConstructor
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
}
