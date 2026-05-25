package com.example.library.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

//Le dice a Spring que esta clase representa una tabla en la base de datos
@Entity
//Indica el nombre de la tabla en la base de datos
@Table(name = "books")
//Métodos get
@Getter
//Métodos set
@Setter
//Constructor vacío
@NoArgsConstructor
//Constructor con todos los campos
@AllArgsConstructor
//Permite construir objetos de forma más ordenada
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false, unique = true)
    private String isbn;

    @Column(nullable = false)
    private Integer publicationYear;

    @Column(nullable = true)
    private String language;

    @Column(nullable = false)
    private Integer pages;

    @Column(nullable = false)
    private String genre;
}
