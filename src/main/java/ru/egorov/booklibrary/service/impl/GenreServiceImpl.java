package ru.egorov.booklibrary.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.egorov.booklibrary.domain.entity.Genre;
import ru.egorov.booklibrary.exception.DataAlreadyExistsException;
import ru.egorov.booklibrary.exception.DataNotFoundException;
import ru.egorov.booklibrary.repository.GenreRepository;
import ru.egorov.booklibrary.service.GenreService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    @Transactional(readOnly = true)
    @Override
    public Genre getById(Long id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Genre with id " + id + " not found!"));
    }

    @Override
    public Genre getByName(String name) {
        return genreRepository.findByName(name, Sort.by("name"))
                .orElseThrow(() -> new DataNotFoundException("Genre with name " + name + " not found!"));
    }

    @Transactional
    @Override
    public Genre saveNewGenre(Genre genre) {
        List<Genre> genres = genreRepository.findAll();

        if (genres.contains(genre)) {
            throw new DataAlreadyExistsException("Genre with name " + genre.getName() + " already exists.");
        }
        return genreRepository.save(genre);
    }

    @Transactional
    @Override
    public Genre updateGenre(Genre genre) {
        Genre detachedGenre = getById(genre.getId());

        detachedGenre.setName(genre.getName());
        return detachedGenre;
    }
    @Transactional
    @Override
    public void deleteGenreById(Long id) {
        genreRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Genre with id " + id + " not found!"));
        genreRepository.deleteById(id);
    }
}
