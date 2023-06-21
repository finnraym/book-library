package ru.egorov.booklibrary.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.egorov.booklibrary.domain.entity.Genre;
import ru.egorov.booklibrary.service.GenreService;
import ru.egorov.booklibrary.web.dto.GenreDto;
import ru.egorov.booklibrary.web.mapper.GenreMapper;
import ru.egorov.booklibrary.web.validation.OnCreate;
import ru.egorov.booklibrary.web.validation.OnUpdate;

@RestController
@RequestMapping("api/v1/genre")
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;
    private final GenreMapper genreMapper;

    @GetMapping("/{id}")
    public GenreDto getById(@PathVariable Long id) {
        Genre entity = genreService.getById(id);
        return genreMapper.toDto(entity);
    }

    @PostMapping
    public GenreDto addNewGenre(@Validated(OnCreate.class) @RequestBody GenreDto genreDto) {
        Genre genre = genreMapper.toEntity(genreDto);
        Genre entity = genreService.saveNewGenre(genre);
        return genreMapper.toDto(entity);
    }

    @PutMapping
    public GenreDto updateGenre(@Validated(OnUpdate.class) @RequestBody GenreDto genreDto) {
        Genre genre = genreMapper.toEntity(genreDto);
        Genre entity = genreService.updateGenre(genre);
        return genreMapper.toDto(entity);
    }

    @DeleteMapping("/{id}")
    public void deleteGenre(@PathVariable Long id) {
        genreService.deleteGenreById(id);
    }
}
