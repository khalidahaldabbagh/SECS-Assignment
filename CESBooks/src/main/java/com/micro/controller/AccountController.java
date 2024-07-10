package com.micro.controller;

import com.micro.entity.Account;
import com.micro.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/api/register")
    ResponseEntity<Account> createStudentAccount(@RequestBody Account student){
        return new ResponseEntity<>(accountService.registerStudent(student.getStudentId()), HttpStatusCode.valueOf(200));
    }
}
