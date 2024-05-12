package com.student.service;


import com.student.exception.StudentNotFoundException;
import com.student.model.Student;
import com.student.model.User;
import com.student.repository.StudentRepository;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.servlet.ModelAndView;

@Validated
@Component
public class ProfileService {

    private final PortalService portalService;
    private final StudentRepository studentRepository;

    public ProfileService(PortalService portalService, StudentRepository studentRepository) {
        this.portalService = portalService;
        this.studentRepository = studentRepository;
    }

    public ModelAndView getProfile(User user, Student student, @NotNull @NotEmpty String view) {
        return portalService.loadPortalUserDetails(user, student, view);
    }

    public ModelAndView getProfileToEdit(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(StudentNotFoundException::new);
        ModelAndView modelAndView = new ModelAndView("profile-edit");
        modelAndView.addObject("student", student);
        return modelAndView;
    }

    public ModelAndView editProfile(Student providedStudent) {
        Student studentFromDatabase = studentRepository.findById(providedStudent.getId()).orElseThrow(StudentNotFoundException::new);
        Student studentToSave = new Student();
        studentToSave.populateStudentId();
        BeanUtils.copyProperties(studentFromDatabase, studentToSave);

        if (providedStudent.getForename() != null && !providedStudent.getForename().isEmpty()) {
            studentToSave.setForename(providedStudent.getForename());
        }
        if (providedStudent.getSurname() != null && !providedStudent.getSurname().isEmpty()) {
            studentToSave.setSurname(providedStudent.getSurname());
        }
        Boolean changed = !studentFromDatabase.equals(studentToSave);
        Student returnedStudent = studentRepository.saveAndFlush(studentToSave);
        ModelAndView modelAndView = new ModelAndView("profile");
        modelAndView.addObject("student", returnedStudent);
        modelAndView.addObject("isStudent", true);
        modelAndView.addObject("updated", changed);
        modelAndView.addObject("message", changed ? "Profile updated" : "Profile not updated");
        return modelAndView;
    }
}