package com.bian.bankservice.dao.repository;

import com.bian.bankservice.constants.TransactionTypeEnum;
import com.bian.bankservice.dao.bean.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findAllByAccountNumberAndTransactionType(String accountNumber, TransactionTypeEnum transactionType);

    List<Transaction> findAllByAccountNumber(String accountNumber);

    List<Transaction> findAllByAccountNumberAndTransactionTimeStampBetween(String accountNumber, String startDate, String endDate);

    List<Transaction> findAllByAccountNumberAndTransactionTypeAndTransactionTimeStampBetween(String accountNumber, String transactionType, String startDate, String endDate);

}
