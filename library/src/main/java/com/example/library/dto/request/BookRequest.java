package com.example.library.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookRequest {
    @NotBlank(message = "The book title is required")
    private String title;
    @NotBlank(message = "The book author is required")
    private String author;
    @NotBlank(message = "The book isbn is required")
    private String isbn;
    @NotNull(message = "The book publication year is required")
    @Min(value = 1900, message = "Year of publication must be greater or equal to 1900")
    private Integer publicationYear;
    private String language;
    @NotNull(message = "The book number of pages is required")
    @Min(value = 11, message = "The number of pages must be greater than 10")
    private Integer pages;
    @NotBlank(message = "The book genre is required")
    private String genre;

}
