package ru.egorov.booklibrary.web.mapper;

import org.mapstruct.Mapper;
import ru.egorov.booklibrary.domain.entity.Book;
import ru.egorov.booklibrary.web.dto.BookDto;

@Mapper(componentModel = "spring")
public interface BookMapper {

    Book toEntity(BookDto bookDto);

    BookDto toDto(Book book);
}
