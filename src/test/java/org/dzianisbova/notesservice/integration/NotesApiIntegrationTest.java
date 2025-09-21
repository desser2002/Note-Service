package org.dzianisbova.notesservice.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.dzianisbova.notesservice.web.dto.AuthorDto;
import org.dzianisbova.notesservice.web.dto.NoteDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class NotesApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Create and retrieve author")
    void createAndRetrieveAuthor() throws Exception {
        String name = "Author Independent";
        AuthorDto createdAuthor = createAuthor(name);

        mockMvc.perform(get("/authors/{id}", createdAuthor.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdAuthor.getId()))
                .andExpect(jsonPath("$.name").value(name));
    }

    @Test
    @DisplayName("Create and retrieve note")
    void createAndRetrieveNote() throws Exception {
        AuthorDto createdAuthor = createAuthor("Note Author");
        NoteDto createdNote = createNote("Test Title", "Test Content", createdAuthor.getId());

        mockMvc.perform(get("/notes/{id}", createdNote.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdNote.getId()))
                .andExpect(jsonPath("$.title").value("Test Title"))
                .andExpect(jsonPath("$.authorId").value(createdAuthor.getId()));
    }

    @Test
    @DisplayName("Delete note and verify it is gone")
    void deleteNoteAndVerifyDeletion() throws Exception {
        AuthorDto createdAuthor = createAuthor("DeleteTest Author");
        NoteDto createdNote = createNote("Delete Title", "Delete Content", createdAuthor.getId());

        mockMvc.perform(delete("/notes/{id}", createdNote.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/notes/{id}", createdNote.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Creating note with non-existent author should return 400")
    void createNoteWithInvalidAuthor_shouldReturnBadRequest() throws Exception {
        NoteDto noteDto = new NoteDto(null, "Bad Note", null, null, 9999L);

        mockMvc.perform(post("/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(noteDto)))
                .andExpect(status().isNotFound());
    }

    private AuthorDto createAuthor(String name) throws Exception {
        AuthorDto authorDto = new AuthorDto(null, name);
        String response = mockMvc.perform(post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorDto)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        return objectMapper.readValue(response, AuthorDto.class);
    }

    private NoteDto createNote(String title, String content, Long authorId) throws Exception {
        NoteDto noteDto = new NoteDto(null, title, content, null, authorId);
        String response = mockMvc.perform(post("/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(noteDto)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        return objectMapper.readValue(response, NoteDto.class);
    }
}
