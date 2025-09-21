package org.dzianisbova.notesservice.web.mapper;

import org.dzianisbova.notesservice.domain.model.Author;
import org.dzianisbova.notesservice.domain.model.Note;
import org.dzianisbova.notesservice.web.dto.NoteDto;

public class NoteMapper {
    public static NoteDto toDto(Note note) {
        if (note == null) {
            return null;
        }
        NoteDto dto = new NoteDto();
        dto.setId(note.getId());
        dto.setTitle(note.getTitle());
        dto.setContent(note.getContent());
        dto.setCreatedAt(note.getCreatedAt());
        if (note.getAuthor() != null) {
            dto.setAuthorId(note.getAuthor().getId());
        }
        return dto;
    }

    private NoteMapper() {
    }

    public static Note toEntity(NoteDto dto) {
        if (dto == null) {
            return null;
        }
        Note note = new Note();
        note.setId(dto.getId());
        note.setTitle(dto.getTitle());
        note.setContent(dto.getContent());
        if (dto.getAuthorId() != null) {
            Author author = new Author();
            author.setId(dto.getAuthorId());
            note.setAuthor(author);
        }
        return note;
    }
}
