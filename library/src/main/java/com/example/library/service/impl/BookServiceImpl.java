package com.example.library.service.impl;

import com.example.library.dto.request.BookRequest;
import com.example.library.dto.response.BookResponse;
import com.example.library.dto.update.BookUpdate;
import com.example.library.entity.Book;
import com.example.library.exception.BookNotFoundException;
import com.example.library.exception.DuplicateIsbnException;
import com.example.library.exception.InvalidBookDataException;
import com.example.library.repository.BookRepository;
import com.example.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookResponse createBook(BookRequest bookRequest){
        validateBookRequest(bookRequest);

        if(bookRepository.existsByIsbn(bookRequest.getIsbn())){
            throw new DuplicateIsbnException("Book with ISBN already exists");
        }
        Book book = Book.builder()
                .title(bookRequest.getTitle())
                .author(bookRequest.getAuthor())
                .isbn(bookRequest.getIsbn())
                .publicationYear(bookRequest.getPublicationYear())
                .language(bookRequest.getLanguage())
                .pages(bookRequest.getPages())
                .genre(bookRequest.getGenre())
                .build();
        Book savedBook = bookRepository.save(book);
        return mapToResponseDTO(savedBook);
    }

    @Override
    public List<BookResponse> getAllBooks(){
        return bookRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BookResponse getBookByIsbn(String isbn){
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException("Book with ISBN " + isbn + " not found"));
        return mapToResponseDTO(book);
    }

    @Override
    public List<BookResponse> getBooksByAuthor(String author){
        return bookRepository.findByAuthorIgnoreCase(author)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookResponse> getBooksByLanguage(String Language){
        return bookRepository.findByLanguageIgnoreCase(Language)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookResponse> getBooksByGenre(String Genre){
        return bookRepository.findByGenreIgnoreCase(Genre)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookResponse> getBooksByPageRange(Integer minPages, Integer maxPages){
        return bookRepository.findByPagesBetween(minPages,maxPages)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BookResponse updateBook(UUID id, BookUpdate request){
            Book book = bookRepository.findById(id)
                    .orElseThrow(() -> new BookNotFoundException("Book with ID " + id + " not found"));
            validateTitle(request.getTitle());
            book.setTitle(request.getTitle());
            book.setLanguage(request.getLanguage());
            Book updateBook = bookRepository.save(book);
            return mapToResponseDTO(updateBook);
    }

    @Override
    public void deleteBook(UUID id){
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with ID " + id + " not found"));
        bookRepository.delete(book);
    }

    private void validateBookRequest(BookRequest bookRequest) {

        validateTitle(bookRequest.getTitle());

        int currentYear = Year.now().getValue();

        if(bookRequest.getPublicationYear() > currentYear) {
            throw new InvalidBookDataException("Publication year cannot be greater than current year");
        }
        if(bookRequest.getPublicationYear() < 1900){
            throw new InvalidBookDataException("Publication year cannot be less than 1900");
        }

        if(bookRequest.getPages() < 10){
            throw new InvalidBookDataException("Pages cannot be less than 10");
        }

    }

    private void validateTitle(String title){
        if(title.matches("\\d+")){
            throw new RuntimeException("Book title cannot contain only numbers");
        }
    }

    private BookResponse mapToResponseDTO(Book book){
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .publicationYear(book.getPublicationYear())
                .language(book.getLanguage())
                .pages(book.getPages())
                .genre(book.getGenre())
                .build();
    }

}
