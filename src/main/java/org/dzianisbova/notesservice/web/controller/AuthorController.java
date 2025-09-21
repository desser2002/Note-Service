package org.dzianisbova.notesservice.web.controller;

import org.dzianisbova.notesservice.service.AuthorService;
import org.dzianisbova.notesservice.web.dto.AuthorDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService service;

    public AuthorController(AuthorService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDto createAuthor(@RequestBody AuthorDto dto) {
        return service.createAuthor(dto);
    }

    @GetMapping
    public List<AuthorDto> getAllAuthors() {
        return service.getAllAuthors();
    }

    @GetMapping("/{id}")
    public AuthorDto getAuthorById(@PathVariable Long id) {
        return service.getAuthorById(id);
    }
}
