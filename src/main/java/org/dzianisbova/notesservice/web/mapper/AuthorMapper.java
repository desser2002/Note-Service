package org.dzianisbova.notesservice.web.mapper;

import org.dzianisbova.notesservice.domain.model.Author;
import org.dzianisbova.notesservice.web.dto.AuthorDto;

public class AuthorMapper {
    private AuthorMapper() {
    }

    public static AuthorDto toDto(Author author) {
        if (author == null) {
            return null;
        }
        AuthorDto dto = new AuthorDto();
        dto.setId(author.getId());
        dto.setName(author.getName());
        return dto;
    }

    public static Author toEntity(AuthorDto dto) {
        if (dto == null) {
            return null;
        }
        Author author = new Author();
        author.setId(dto.getId());
        author.setName(dto.getName());
        return author;
    }
}
