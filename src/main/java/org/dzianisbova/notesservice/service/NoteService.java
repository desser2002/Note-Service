package org.dzianisbova.notesservice.service;

import org.dzianisbova.notesservice.domain.model.Note;
import org.dzianisbova.notesservice.exception.AuthorNotFoundException;
import org.dzianisbova.notesservice.exception.NoteNotFoundException;
import org.dzianisbova.notesservice.repository.AuthorRepository;
import org.dzianisbova.notesservice.repository.NoteRepository;
import org.dzianisbova.notesservice.web.dto.NoteDto;
import org.dzianisbova.notesservice.web.mapper.NoteMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private final NoteRepository noteRepository;
    private final AuthorRepository authorRepository;

    public NoteService(NoteRepository noteRepository, AuthorRepository authorRepository) {
        this.noteRepository = noteRepository;
        this.authorRepository = authorRepository;
    }

    public NoteDto createNote(NoteDto noteDto) {
        if (noteDto.getAuthorId() == null || !authorRepository.existsById(noteDto.getAuthorId())) {
            throw new AuthorNotFoundException(noteDto.getAuthorId());
        }
        Note note = NoteMapper.toEntity(noteDto);
        Note saved = noteRepository.save(note);
        return NoteMapper.toDto(saved);
    }

    public List<NoteDto> getAllNotes() {
        return noteRepository.findAll().stream()
                .map(NoteMapper::toDto)
                .toList();
    }

    public NoteDto getNoteById(Long id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException(id));
        return NoteMapper.toDto(note);
    }

    public void deleteNoteById(Long id) {
        if (!noteRepository.existsById(id)) {
            throw new NoteNotFoundException(id);
        }
        noteRepository.deleteById(id);
    }
}
