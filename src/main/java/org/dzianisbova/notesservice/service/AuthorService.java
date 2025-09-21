package org.dzianisbova.notesservice.service;

import org.dzianisbova.notesservice.domain.model.Author;
import org.dzianisbova.notesservice.repository.AuthorRepository;
import org.dzianisbova.notesservice.web.dto.AuthorDto;
import org.dzianisbova.notesservice.web.mapper.AuthorMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    private final AuthorRepository repository;

    public AuthorService(AuthorRepository repository) {
        this.repository = repository;
    }

    public AuthorDto createAuthor(AuthorDto dto) {
        Author author = AuthorMapper.toEntity(dto);
        Author saved = repository.save(author);
        return AuthorMapper.toDto(saved);
    }

    public List<AuthorDto> getAllAuthors() {
        return repository.findAll().stream()
                .map(AuthorMapper::toDto)
                .toList();
    }

    public AuthorDto getAuthorById(Long id) {
        Author author = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));
        return AuthorMapper.toDto(author);
    }
}
