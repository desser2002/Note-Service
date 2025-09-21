package org.dzianisbova.notesservice.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class NoteDto {
    private Long id; // для новых заметок может быть null

    @NotBlank(message = "Title must not be blank")
    private String title;

    private String content;

    private LocalDateTime createdAt;

    @NotNull(message = "Author ID must not be null")
    private Long authorId;

    public NoteDto() {
    }

    public NoteDto(Long id, String title, String content, LocalDateTime createdAt, Long authorId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.authorId = authorId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
}