package com.micro.service;

import com.micro.entity.TransactionDetails;

import java.util.List;

public interface TransactionDetailsService {
    void insertTransaction(TransactionDetails transaction);
    List<TransactionDetails> getTransaction();
    void deleteTransactionById(long id);
    TransactionDetails findById(long id);
}
