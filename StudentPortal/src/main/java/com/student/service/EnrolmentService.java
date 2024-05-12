package com.student.service;


import com.student.exception.EnrolmentAlreadyExistsException;
import com.student.model.Course;
import com.student.model.Enrolment;
import com.student.model.Student;
import com.student.repository.EnrolmentRepository;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.servlet.ModelAndView;
import java.util.stream.Collectors;
import java.util.List;

@Validated
@Component
public class EnrolmentService {

    private final EnrolmentRepository enrolmentRepository;
    private final IntegrationService integrationService;

    public EnrolmentService(EnrolmentRepository enrolmentRepository, IntegrationService integrationService) {
        this.enrolmentRepository = enrolmentRepository;
        this.integrationService = integrationService;
    }

    public Enrolment createEnrolment(@NotNull Course course, @NotNull Student student) {
        if (enrolmentRepository.findEnrolmentByCourseAndStudent(course, student) != null) {
            throw new EnrolmentAlreadyExistsException("Student " + student.getStudentId() + " is already enrolled in course " + course.getTitle());
        }
        Enrolment enrolment = new Enrolment(student, course);
        return enrolmentRepository.save(enrolment);
    }

    public Enrolment findEnrolment(@NotNull Course course, @Nullable Student student) {
        return enrolmentRepository.findEnrolmentByCourseAndStudent(course, student);
    }

    public ModelAndView getEnrolments(@NotNull Student student) {
        ModelAndView modelAndView = new ModelAndView("courses");
        List<Course> courses = enrolmentRepository.findEnrolmentByStudent(student)
                .stream()
                .map(Enrolment::getCourse)
                .collect(Collectors.toList());
        modelAndView.addObject("courses", courses);
        return modelAndView;
    }
}
