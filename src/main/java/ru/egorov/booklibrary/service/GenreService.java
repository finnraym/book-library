package ru.egorov.booklibrary.service;

import ru.egorov.booklibrary.domain.entity.Genre;

public interface GenreService {

    Genre getById(Long id);
    void saveNewGenre(Genre genre);
    Genre updateGenre(Genre genre);
    void deleteGenreById(Long id);
}
