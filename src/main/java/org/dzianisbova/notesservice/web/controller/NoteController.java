package org.dzianisbova.notesservice.web.controller;

import org.dzianisbova.notesservice.service.NoteService;
import org.dzianisbova.notesservice.web.dto.NoteDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {
    private final NoteService service;

    public NoteController(NoteService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NoteDto createNote(@RequestBody NoteDto dto)
    {
        return service.createNote(dto);
    }

    @GetMapping
    public List<NoteDto> getAllNotes() {
        return service.getAllNotes();
    }

    @GetMapping("/{id}")
    public NoteDto getNoteById(@PathVariable Long id) {
        return service.getNoteById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNoteById(@PathVariable Long id) {
        service.deleteNoteById(id);
    }
}
