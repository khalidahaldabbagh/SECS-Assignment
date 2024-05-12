package com.student.model;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class Enrolment {
    @EmbeddedId
    private EnrolmentKey id = new EnrolmentKey();

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name="student_id")
    private Student student;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name="course_id")
    private Course course;

    public Enrolment(Student student, Course course) {
        this.student = student;
        this.course = course;
    }

    public Enrolment() {

    }
}
