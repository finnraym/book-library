package ru.egorov.booklibrary.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.egorov.booklibrary.domain.entity.Genre;
import ru.egorov.booklibrary.service.GenreService;
import ru.egorov.booklibrary.utils.consts.WebConstants;
import ru.egorov.booklibrary.web.dto.GenreDto;
import ru.egorov.booklibrary.web.mapper.GenreMapper;
import ru.egorov.booklibrary.web.response.DataResponse;
import ru.egorov.booklibrary.web.validation.OnCreate;
import ru.egorov.booklibrary.web.validation.OnUpdate;

import java.util.List;

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

    @GetMapping("/all")
    public DataResponse<GenreDto> getAll(
            @RequestParam(value = "pageNo", defaultValue = WebConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = WebConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = WebConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = WebConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        DataResponse<Genre> genres = genreService.getAll(pageNo, pageSize, sortBy, sortDir);
        List<GenreDto> genreDtos = genres.getData().stream()
                .map(genreMapper::toDto)
                .toList();

        return DataResponse.<GenreDto>builder()
                .data(genreDtos)
                .last(genres.isLast())
                .pageNo(genres.getPageNo())
                .pageSize(genres.getPageSize())
                .totalPages(genres.getTotalPages())
                .totalElements(genres.getTotalElements())
                .build();
    }

    @GetMapping("/name")
    public List<GenreDto> getByName(@RequestParam(value = "name") String name) { // TODO MissingServletRequestParameterException  Required request parameter 'name' for method parameter type String is not present
        return genreService.getByName(name).stream()
                .map(genreMapper::toDto)
                .toList();
    }

}
