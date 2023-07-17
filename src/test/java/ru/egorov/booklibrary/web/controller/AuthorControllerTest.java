package ru.egorov.booklibrary.web.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.egorov.booklibrary.domain.entity.Author;
import ru.egorov.booklibrary.service.AuthorService;
import ru.egorov.booklibrary.service.BookService;
import ru.egorov.booklibrary.web.dto.AuthorDto;
import ru.egorov.booklibrary.web.mapper.AuthorMapper;
import ru.egorov.booklibrary.web.mapper.BookMapper;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
@WebMvcTest(AuthorController.class)
public class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AuthorService authorService;
    @MockBean
    private BookService bookService;
    @MockBean
    private AuthorMapper authorMapper;
    @MockBean
    private BookMapper bookMapper;

    @Test
    @WithMockUser
    void testGetById() throws Exception {
        Author author = new Author(1L, "Joshua", "Long", null);

        when(authorService.getById(1L)).thenReturn(author);
        AuthorDto authorDto = AuthorDto.builder()
                .id(author.getId())
                .firstName(author.getFirstName())
                .secondName(author.getSecondName())
                .dateOfBirth(author.getDateOfBirth())
                .build();

        when(authorMapper.toDto(author)).thenReturn(authorDto);
        MvcResult mvcResult = this.mockMvc.perform(get("/api/v1/authors/" + 1)).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        AuthorDto actual = mapFromJson(contentAsString, AuthorDto.class);
        assertEquals(authorDto, actual);
    }

    protected <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }
}
