package com.example.library.dto.update;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookUpdate {
    @NotBlank(message = "The book title is required")
    private String title;
    private String language;
}
