package org.dzianisbova.notesservice.service;

import org.dzianisbova.notesservice.domain.model.Author;
import org.dzianisbova.notesservice.domain.model.Note;
import org.dzianisbova.notesservice.exception.AuthorNotFoundException;
import org.dzianisbova.notesservice.repository.AuthorRepository;
import org.dzianisbova.notesservice.repository.NoteRepository;
import org.dzianisbova.notesservice.web.dto.NoteDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private NoteService noteService;
    private AutoCloseable mocks;

    @BeforeEach
    void setup() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }

    @Test
    @DisplayName("Create note with valid author should save and return DTO")
    void createNote_withValidAuthor_shouldSaveAndReturnDto() {
        final Long validAuthorId = 1L;
        final Long savedNoteId = 10L;
        final String title = "Title";
        final String content = "Content";

        Author author = createAuthor(validAuthorId);
        Note savedNote = createNote(savedNoteId, title, author);
        savedNote.setContent(content);

        NoteDto inputDto = new NoteDto(null, title, content, null, validAuthorId);

        when(authorRepository.existsById(validAuthorId)).thenReturn(true);
        when(noteRepository.save(any(Note.class))).thenReturn(savedNote);

        NoteDto result = noteService.createNote(inputDto);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(savedNoteId);
        assertThat(result.getTitle()).isEqualTo(title);
        assertThat(result.getAuthorId()).isEqualTo(validAuthorId);

        verify(authorRepository).existsById(validAuthorId);
        verify(noteRepository).save(any(Note.class));
    }

    @Test
    @DisplayName("Create note with invalid author should throw AuthorNotFoundException")
    void createNote_withInvalidAuthor_shouldThrowAuthorNotFoundException() {
        final Long invalidAuthorId = 99L;
        final String title = "Title";
        final String content = "Content";

        NoteDto inputDto = new NoteDto(null, title, content, null, invalidAuthorId);

        when(authorRepository.existsById(invalidAuthorId)).thenReturn(false);

        assertThatThrownBy(() -> noteService.createNote(inputDto))
                .isInstanceOf(AuthorNotFoundException.class)
                .hasMessage("Author not found with id: " + invalidAuthorId);

        verify(authorRepository).existsById(invalidAuthorId);
        verify(noteRepository, never()).save(any());
    }

    @Test
    @DisplayName("Get all notes should return list of DTOs")
    void getAllNotes_shouldReturnListOfDto() {
        Author author = createAuthor(1L);

        Note note1 = createNote(1L, "Note 1", author);
        Note note2 = createNote(2L, "Note 2", author);

        when(noteRepository.findAll()).thenReturn(List.of(note1, note2));

        List<NoteDto> result = noteService.getAllNotes();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getTitle()).isEqualTo("Note 1");
        assertThat(result.get(1).getTitle()).isEqualTo("Note 2");

        verify(noteRepository).findAll();
    }

    @Test
    @DisplayName("Get note by existing ID should return DTO")
    void getNoteById_existingId_shouldReturnDto() {
        final Long existingNoteId = 1L;

        Author author = createAuthor(existingNoteId);
        Note note = createNote(existingNoteId, "Note Title", author);

        when(noteRepository.findById(existingNoteId)).thenReturn(Optional.of(note));

        NoteDto result = noteService.getNoteById(existingNoteId);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(existingNoteId);
        assertThat(result.getTitle()).isEqualTo("Note Title");

        verify(noteRepository).findById(existingNoteId);
    }

    @Test
    @DisplayName("Get note by non-existing ID should throw RuntimeException")
    void getNoteById_nonExistingId_shouldThrowException() {
        final Long nonExistingNoteId = 100L;

        when(noteRepository.findById(nonExistingNoteId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> noteService.getNoteById(nonExistingNoteId))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Note not found with id: " + nonExistingNoteId);

        verify(noteRepository).findById(nonExistingNoteId);
    }

    @Test
    @DisplayName("Delete note by existing ID should perform deletion")
    void deleteNoteById_existing_shouldDelete() {
        final Long deleteNoteId = 5L;

        when(noteRepository.existsById(deleteNoteId)).thenReturn(true);

        noteService.deleteNoteById(deleteNoteId);

        verify(noteRepository).existsById(deleteNoteId);
        verify(noteRepository).deleteById(deleteNoteId);
    }

    @Test
    @DisplayName("Delete note by non-existing ID should throw RuntimeException")
    void deleteNoteById_nonExisting_shouldThrowException() {
        final Long nonExistingDeleteNoteId = 10L;

        when(noteRepository.existsById(nonExistingDeleteNoteId)).thenReturn(false);

        assertThatThrownBy(() -> noteService.deleteNoteById(nonExistingDeleteNoteId))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Note not found with id: " + nonExistingDeleteNoteId);

        verify(noteRepository).existsById(nonExistingDeleteNoteId);
        verify(noteRepository, never()).deleteById(anyLong());
    }

    private Author createAuthor(Long id) {
        Author author = new Author();
        author.setId(id);
        return author;
    }

    private Note createNote(Long noteId, String title, Author author) {
        Note note = new Note();
        note.setId(noteId);
        note.setTitle(title);
        note.setAuthor(author);
        return note;
    }
}