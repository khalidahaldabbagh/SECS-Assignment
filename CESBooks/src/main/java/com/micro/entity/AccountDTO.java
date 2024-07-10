package com.micro.entity;

import jakarta.persistence.*;
import lombok.Data;


@Data
public class AccountDTO {

    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(unique = true)
    private String studentId;

    public AccountDTO() {
    }

    public AccountDTO(String studentId) {
        this.studentId = studentId;
    }
}