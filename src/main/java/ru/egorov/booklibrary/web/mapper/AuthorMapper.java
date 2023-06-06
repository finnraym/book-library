package ru.egorov.booklibrary.web.mapper;

import org.mapstruct.Mapper;
import ru.egorov.booklibrary.domain.entity.Author;
import ru.egorov.booklibrary.web.dto.AuthorDto;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    AuthorDto toDto(Author author);

    Author toEntity(AuthorDto authorDto);
}
