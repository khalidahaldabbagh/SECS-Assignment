package com.student.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Course {
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String title;
    @Column(columnDefinition = "text")
    private String description;
    private Double fee;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "course")
    @ToString.Exclude
    @JsonIgnore
    private List<Enrolment> enrolmentList = new ArrayList<>();

    public Course(String title, String description, Double fee) {
        this.title = title;
        this.description = description;
        this.fee = fee;
    }

    public Course() {

    }
}
