package com.student.service;


import com.student.model.Account;
import com.student.model.Invoice;
import com.student.model.Student;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class IntegrationService {

    private final RestTemplate restTemplate;
    @Value("${finance.host}")
    private String financeHost;
    @Value("${finance.student.create}")
    private String studentCreatedFinanceSubscriber;
    @Value("${library.host}")
    private String libraryHost;
    @Value("${library.student.create}")
    private String studentCreatedLibrarySubscriber;
    @Value("${finance.course.enrol}")
    private String courseEnrolmentFinanceSubscriber;
    @Value("${finance.account.status}")
    private String accountStatusPublisher;

    public IntegrationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void notifyStudentCreated(@NotNull Account account) {
        restTemplate.postForObject(financeHost + studentCreatedFinanceSubscriber, account, Account.class);
//        restTemplate.postForObject(libraryHost + studentCreatedLibrarySubscriber, account, Account.class);
    }

    public Invoice createCourseFeeInvoice(@NotNull Invoice invoice) {
        return restTemplate.postForObject(financeHost + courseEnrolmentFinanceSubscriber, invoice, Invoice.class);
    }

    public Account getStudentPaymentStatus(@NotNull Student student) {
        return restTemplate.getForObject(financeHost + accountStatusPublisher + student.getStudentId(), Account.class);
    }
}
