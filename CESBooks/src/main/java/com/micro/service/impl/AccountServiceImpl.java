package com.micro.service.impl;

import com.micro.entity.Account;
import com.micro.repository.AccountRepository;
import com.micro.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account createStudentAccount(Account student) {
        return accountRepository.save(student);
    }


    public Account registerStudent(String studentId) {
        if (accountRepository.findByStudentId(studentId).isPresent()) {
            return null;
        }
        Account student = new Account();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode("0000");
        student.setStudentId(studentId);

        student.setHash(encodedPassword);
        return accountRepository.save(student);
    }
}
