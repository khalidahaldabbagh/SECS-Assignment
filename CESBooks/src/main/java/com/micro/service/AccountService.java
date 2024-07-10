package com.micro.service;

import com.micro.entity.Account;

public interface AccountService {

    Account createStudentAccount(Account student);

    Account registerStudent(String studentId);
}
