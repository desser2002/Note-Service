package org.dzianisbova.notesservice.exception;

public class NoteNotFoundException extends RuntimeException {
    public NoteNotFoundException(Long id) {
        super("Note not found with id: " + id);
    }
}
