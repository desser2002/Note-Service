package org.dzianisbova.notesservice.repository;

import org.dzianisbova.notesservice.domain.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note,Long> {
}
