package ru.egorov.booklibrary.web.mapper;

import org.mapstruct.Mapper;
import ru.egorov.booklibrary.domain.entity.User;
import ru.egorov.booklibrary.web.dto.UserDto;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserDto dto);

    UserDto toDto(User user);
}
