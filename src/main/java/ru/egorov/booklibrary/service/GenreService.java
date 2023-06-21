package ru.egorov.booklibrary.service;

import ru.egorov.booklibrary.domain.entity.Genre;
import ru.egorov.booklibrary.web.response.DataResponse;

import java.util.List;

public interface GenreService {

    Genre getById(Long id);
    List<Genre> getByName(String name);
    Genre saveNewGenre(Genre genre);
    Genre updateGenre(Genre genre);
    void deleteGenreById(Long id);

    DataResponse<Genre> getAll(int pageNumber, int pageSize, String sortBy, String sortDirection);
}
