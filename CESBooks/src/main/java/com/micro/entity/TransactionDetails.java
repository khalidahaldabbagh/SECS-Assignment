package com.micro.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDetails {
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name="student_id")
    String studentId;
    @Column(name="book_isbn")
    String bookIsbn;
    @Column(name="date_borrowed")
    Date dateBorrowed;
    @Column(name="date_returned")
    Date dateReturned;
}
