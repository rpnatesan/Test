package com.bian.bankservice.service;

import com.bian.bankservice.constants.TransactionTypeEnum;
import com.bian.bankservice.dao.bean.Account;
import com.bian.bankservice.dao.bean.Transaction;
import com.bian.bankservice.dao.repository.AccountRepository;
import com.bian.bankservice.dao.repository.TransactionRepository;
import com.bian.bankservice.dto.TransactionDTO;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    private Account createOrUpdateAccount(TransactionDTO transactionDTO) {
        Account account = accountRepository.findByAccountNumber(transactionDTO.getAccountNumber());
        if (account == null) {
            account = new Account();
            account.setAccountNumber(transactionDTO.getAccountNumber());
            account.setBalance(transactionDTO.getAmount());
        } else {
            if (transactionDTO.getTransactionType().equals("DEPOSIT")) {
                account.setBalance(account.getBalance() + transactionDTO.getAmount());
            } else if (transactionDTO.getTransactionType().equals("WITHDRAW")) {
                account.setBalance(account.getBalance() - transactionDTO.getAmount());
            } else {
                throw new IllegalArgumentException("Invalid Transaction Type : " + transactionDTO.getTransactionType());
            }
        }
        accountRepository.save(account);
        return account;
    }

    public Transaction createTransactionAndUpdateAccount(TransactionDTO transactionDTO) {

        createOrUpdateAccount(transactionDTO);
        Transaction transaction = new Transaction();
        transaction.setAccountNumber(transactionDTO.getAccountNumber());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setTransactionType(TransactionTypeEnum.valueOf(transactionDTO.getTransactionType()));
        transaction = transactionRepository.save(transaction);
        return transaction;
    }

    public Account getAccount(String accountNumber) throws NotFoundException {
        Account account = accountRepository.findByAccountNumber(accountNumber);
        if (account == null) {
            throw new NotFoundException("No account found for account number : " + accountNumber);
        }
        return account;
    }

    public List<Transaction> getTransactionsByType(String accountNumber, String transactionType) throws NotFoundException {
        List<Transaction> transactions;
        if (transactionType == null) {
            transactions = transactionRepository.findAllByAccountNumber(accountNumber);
        } else {
            transactions = transactionRepository.findAllByAccountNumberAndTransactionType(accountNumber, TransactionTypeEnum.valueOf(transactionType));
        }

        if (transactions.isEmpty()) {
            throw new NotFoundException("No transactions found for this account.");
        }
        return transactions;
    }

    public List<Transaction> getTransactionsByInterval(String accountNumber, String startDate, String endDate) {
        List<Transaction> transactions = new ArrayList<>();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        if ((startDate == null || endDate == null)) {
            throw new IllegalArgumentException("Missing Start or End date");
        } else if (endDate.compareTo(startDate) < 0) {
            throw new IllegalArgumentException("Please check start date and end date");
        }
        transactions = transactionRepository.findAllByAccountNumberAndTransactionTimeStampBetween(accountNumber, startDate, endDate);
        return transactions;
    }

    public List<Transaction> getTransactionsByIntervalAndWithdraw(String accountNumber, String startDate, String endDate) {
        List<Transaction> transactions = new ArrayList<>();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        if ((startDate == null || endDate == null)) {
            throw new IllegalArgumentException("Missing Start or End date");
        } else if (endDate.compareTo(startDate) < 0) {
            throw new IllegalArgumentException("Please check start date and end date");
        }
        transactions = transactionRepository.findAllByAccountNumberAndTransactionTypeAndTransactionTimeStampBetween(accountNumber, TransactionTypeEnum.WITHDRAW.name(), startDate, endDate);
        return transactions;
    }

}
