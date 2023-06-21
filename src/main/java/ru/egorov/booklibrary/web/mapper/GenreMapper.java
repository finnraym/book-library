package ru.egorov.booklibrary.web.mapper;

import org.mapstruct.Mapper;
import ru.egorov.booklibrary.domain.entity.Genre;
import ru.egorov.booklibrary.web.dto.GenreDto;

@Mapper(componentModel = "spring")
public interface GenreMapper {

    Genre toEntity(GenreDto genreDto);

    GenreDto toDto(Genre genre);
}
