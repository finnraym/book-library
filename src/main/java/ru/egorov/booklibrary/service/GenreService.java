package ru.egorov.booklibrary.service;

import ru.egorov.booklibrary.domain.entity.Genre;

public interface GenreService {

    Genre getById(Long id);
    Genre getByName(String name);
    void saveNewGenre(Genre genre);
    Genre updateGenre(Genre genre);
    void deleteGenreById(Long id);
}
