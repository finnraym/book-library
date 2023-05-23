package ru.egorov.booklibrary.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.egorov.booklibrary.domain.entity.Genre;
import ru.egorov.booklibrary.repository.GenreRepository;
import ru.egorov.booklibrary.service.GenreService;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    @Override
    public Genre getById(Long id) {
        return null;
    }

    @Override
    public void saveNewGenre(Genre genre) {

    }

    @Override
    public Genre updateGenre(Genre genre) {
        return null;
    }

    @Override
    public void deleteGenreById(Long id) {

    }
}
