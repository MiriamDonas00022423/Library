package com.example.library.repository;

import com.example.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

//Receives the entity managed by this repository  and the type of its identifier.
public interface BookRepository extends JpaRepository<Book, UUID> {
    Optional<Book> findByIsbn(String isbn);
    boolean existsByIsbn(String isbn);
    List<Book> findByAuthorIgnoreCase(String author);
    List<Book> findByLanguageIgnoreCase(String language);
    List<Book> findByGenreIgnoreCase(String Genre);
    List<Book> findByPagesBetween(Integer minPages, Integer maxPages);
}
