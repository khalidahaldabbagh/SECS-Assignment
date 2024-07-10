package com.micro.controller;


import com.micro.entity.TransactionDetails;
import com.micro.service.TransactionDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionDetailsService transactionService;

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDetails> findTransaction(@PathVariable long id){
        return new ResponseEntity<>( transactionService.findById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable long id){
        transactionService.deleteTransactionById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<List<TransactionDetails>> findTransactions(){
        return new ResponseEntity<>(transactionService.getTransaction(),HttpStatus.OK);
    }

}
