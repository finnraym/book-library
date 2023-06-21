package ru.egorov.booklibrary.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.egorov.booklibrary.domain.entity.Author;
import ru.egorov.booklibrary.exception.DataNotFoundException;
import ru.egorov.booklibrary.repository.AuthorRepository;
import ru.egorov.booklibrary.service.AuthorService;
import ru.egorov.booklibrary.utils.AppUtils;
import ru.egorov.booklibrary.web.response.DataResponse;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    private final static String FIRST_NAME = "firstName";
    private final static String SECOND_NAME = "secondName";
    @Transactional(readOnly = true)
    @Override
    public Author getById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Author with id " + id + " not found."));
    }

    @Transactional
    @Override
    public Author saveNewAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Transactional
    @Override
    public Author updateAuthor(Author author) {
        Author updatedAuthor = getById(author.getId());

        updatedAuthor.setFirstName(author.getFirstName());
        updatedAuthor.setSecondName(author.getSecondName());

        if (Objects.nonNull(author.getDateOfBirth()))
            updatedAuthor.setDateOfBirth(author.getDateOfBirth());

        return updatedAuthor;
    }

    @Transactional
    @Override
    public void deleteAuthorById(Long id) {
        authorRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public DataResponse<Author> getAll(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort); // TODO PropertyReferenceException need handle
        Page<Author> pageAuthors = authorRepository.findAll(pageable);

        return DataResponse.<Author>builder()
                .data(pageAuthors.getContent())
                .pageNo(pageAuthors.getNumber())
                .pageSize(pageAuthors.getSize())
                .totalElements(pageAuthors.getTotalElements())
                .totalPages(pageAuthors.getTotalPages())
                .last(pageAuthors.isLast())
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Author> getAllByFirstName(String firstName, String sortDir) {
        Sort sort = AppUtils.getSort(FIRST_NAME, sortDir);

        return authorRepository.findAllByFirstNameContaining(firstName, sort);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Author> getAllBySecondName(String secondName, String sortDir) {
        Sort sort = AppUtils.getSort(SECOND_NAME, sortDir);

        return authorRepository.findAllByFirstNameContaining(secondName, sort);
    }
}
