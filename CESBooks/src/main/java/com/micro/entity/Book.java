package com.micro.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long isbn;
//    @NotNull
//    String isbn;
    String title;
    String author;
    int year;
    int copies;
}
