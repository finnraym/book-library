package ru.egorov.booklibrary.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.egorov.booklibrary.domain.entity.Book;
import ru.egorov.booklibrary.domain.entity.Genre;
import ru.egorov.booklibrary.exception.DataAlreadyExistsException;
import ru.egorov.booklibrary.exception.DataNotFoundException;
import ru.egorov.booklibrary.repository.GenreRepository;
import ru.egorov.booklibrary.service.GenreService;
import ru.egorov.booklibrary.web.response.DataResponse;

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
    public List<Genre> getByName(String name) {
        return genreRepository.findByNameContainingIgnoreCase(name, Sort.by("name"));
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

    @Transactional(readOnly = true)
    @Override
    public DataResponse<Genre> getAll(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Genre> pagesOfGenre = genreRepository.findAll(pageable);

        return DataResponse.<Genre>builder()
                .data(pagesOfGenre.getContent())
                .last(pagesOfGenre.isLast())
                .pageNo(pagesOfGenre.getNumber())
                .pageSize(pagesOfGenre.getSize())
                .totalPages(pagesOfGenre.getTotalPages())
                .totalElements(pagesOfGenre.getTotalElements())
                .build();
    }
}
