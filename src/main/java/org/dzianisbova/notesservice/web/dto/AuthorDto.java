package org.dzianisbova.notesservice.web.dto;

import jakarta.validation.constraints.NotBlank;

public class AuthorDto {
    private Long id;

    @NotBlank(message = "Name must not be blank")
    private String name;

    public AuthorDto() {}

    public AuthorDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}