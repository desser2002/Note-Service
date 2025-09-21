package org.dzianisbova.notesservice.repository;

import org.dzianisbova.notesservice.domain.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author,Long> {
}
