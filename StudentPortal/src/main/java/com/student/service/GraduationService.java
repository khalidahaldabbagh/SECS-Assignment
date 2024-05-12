package com.student.service;


import com.student.exception.StudentNotFoundException;
import com.student.model.Account;
import com.student.model.Student;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.servlet.ModelAndView;
@Validated
@Component
public class GraduationService {

    private final IntegrationService integrationService;

    public GraduationService(IntegrationService integrationService) {
        this.integrationService = integrationService;
    }

    public ModelAndView getGraduationStatus(Student student) {
        if (student == null) {
            throw new StudentNotFoundException();
        }
        Account account = integrationService.getStudentPaymentStatus(student);
        ModelAndView modelAndView = new ModelAndView("graduation");
        modelAndView.addObject("balanceOutstanding", account.isHasOutstandingBalance());
        modelAndView.addObject("message", account.isHasOutstandingBalance() ? "ineligible to graduate" : "eligible to graduate");
        return modelAndView;
    }
}