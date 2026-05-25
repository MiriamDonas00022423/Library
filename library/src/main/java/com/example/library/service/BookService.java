package com.example.library.service;

import com.example.library.dto.request.BookRequest;
import com.example.library.dto.response.BookResponse;
import com.example.library.dto.update.BookUpdate;

import java.util.List;
import java.util.UUID;

public interface BookService {

    BookResponse createBook(BookRequest bookRequest);
    List<BookResponse> getAllBooks();
    BookResponse getBookByIsbn(String isbn);
    List<BookResponse> getBooksByAuthor(String author);
    List<BookResponse> getBooksByLanguage(String Language);
    List<BookResponse> getBooksByGenre(String Genre);
    List<BookResponse> getBooksByPageRange(Integer minPages, Integer maxPages);
    BookResponse updateBook(UUID id, BookUpdate request);
    void deleteBook(UUID id);
}
