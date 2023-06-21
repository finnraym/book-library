package ru.egorov.booklibrary.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.egorov.booklibrary.domain.entity.Book;
import ru.egorov.booklibrary.service.BookService;
import ru.egorov.booklibrary.web.dto.AuthorDto;
import ru.egorov.booklibrary.web.dto.BookDto;
import ru.egorov.booklibrary.web.mapper.BookMapper;
import ru.egorov.booklibrary.web.validation.OnCreate;
import ru.egorov.booklibrary.web.validation.OnUpdate;

import java.util.List;

@RestController
@RequestMapping("api/v1/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    @GetMapping("/{id}")
    public BookDto getById(@PathVariable Long id) {
        Book entity = bookService.getById(id);
        return bookMapper.toDto(entity);
    }

    @PostMapping
    public BookDto addNewBook(@Validated(OnCreate.class) @RequestBody BookDto bookDto) {
        Book newBook = bookMapper.toEntity(bookDto);
        Book entity = bookService.saveNewBook(newBook);
        return bookMapper.toDto(entity);
    }

    @PutMapping
    public BookDto updateBook(@Validated(OnUpdate.class) @RequestBody BookDto bookDto) {
        Book updatedBook = bookMapper.toEntity(bookDto);
        Book entity = bookService.updateBook(updatedBook);
        return bookMapper.toDto(entity);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        bookService.deleteBookById(id);
    }

    @PatchMapping("/{bookId}/author/{authorId}")
    public Boolean addAuthorForBook(@PathVariable Long bookId, @PathVariable Long authorId) {
        return bookService.addAuthorForBook(bookId, authorId);
    }

    @PatchMapping("/{bookId}/genre/{genreId}")
    public Boolean addGenreForBook(@PathVariable Long bookId, @PathVariable Long genreId) {
        return bookService.addGenreForBook(bookId, genreId);
    }
}
