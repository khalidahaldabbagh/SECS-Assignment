package com.micro.service.impl;

import com.micro.entity.TransactionDetails;
import com.micro.repository.TransactionDetailsRepository;
import com.micro.service.TransactionDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionDetailsService {
    @Autowired
    TransactionDetailsRepository transactionRepository;

    @Override
    public void insertTransaction(TransactionDetails transaction) {

    }

    @Override
    public List<TransactionDetails> getTransaction() {
        return null;
    }

    @Override
    public void deleteTransactionById(long id) {

    }

    @Override
    public TransactionDetails findById(long id) {
        return null;
    }


    public List<TransactionDetails> getStudentTransactions(String studentId) {
        return transactionRepository.findByStudentIdAndDateReturnedIsNull(studentId);
    }

    public void saveTransaction(TransactionDetails transaction) {
        transactionRepository.save(transaction);
    }
}
