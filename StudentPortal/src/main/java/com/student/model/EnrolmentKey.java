package com.student.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class EnrolmentKey implements Serializable {
    @Column(name = "student_id")
    Long studentId;

    @Column(name = "course_id")
    Long courseId;
}
