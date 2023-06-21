package ru.egorov.booklibrary.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.egorov.booklibrary.domain.entity.Book;
import ru.egorov.booklibrary.service.BookService;
import ru.egorov.booklibrary.utils.consts.WebConstants;
import ru.egorov.booklibrary.web.dto.BookDto;
import ru.egorov.booklibrary.web.mapper.BookMapper;
import ru.egorov.booklibrary.web.response.DataResponse;
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

    @GetMapping("/all")
    public DataResponse<BookDto> getAll(
            @RequestParam(value = "pageNo", defaultValue = WebConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = WebConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = WebConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = WebConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        DataResponse<Book> books = bookService.getAll(pageNo, pageSize, sortBy, sortDir);
        List<BookDto> bookDtos = books.getData().stream()
                .map(bookMapper::toDto)
                .toList();

        return DataResponse.<BookDto>builder()
                .data(bookDtos)
                .last(books.isLast())
                .pageNo(books.getPageNo())
                .pageSize(books.getPageSize())
                .totalPages(books.getTotalPages())
                .totalElements(books.getTotalElements())
                .build();
    }
}
