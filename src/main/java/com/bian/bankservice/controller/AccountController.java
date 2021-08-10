package com.bian.bankservice.controller;

import com.bian.bankservice.dao.bean.Account;
import com.bian.bankservice.dao.bean.Transaction;
import com.bian.bankservice.dto.TransactionDTO;
import com.bian.bankservice.service.AccountService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account/")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("transaction")
    public Transaction createTransactionAndUpdateAccount(@RequestBody TransactionDTO transactionDTO) {
        return accountService.createTransactionAndUpdateAccount(transactionDTO);
    }

    @GetMapping("{accountNumber}")
    public Account getAccount(@PathVariable String accountNumber) throws NotFoundException {
        return accountService.getAccount(accountNumber);
    }

    @GetMapping("{accountNumber}/transaction")
    public List<Transaction> getTransactionsByType(@PathVariable String accountNumber, @RequestParam(required = false) String transactionType) throws NotFoundException {
        return accountService.getTransactionsByType(accountNumber, transactionType);
    }

    @GetMapping("{accountNumber}/transaction/dateFilter")
    public List<Transaction> getTransactions(@PathVariable String accountNumber,
                                             @RequestParam String startDate,
                                             @RequestParam String endDate) throws NotFoundException {
        return accountService.getTransactionsByInterval(accountNumber, startDate, endDate);
    }

    @GetMapping("{accountNumber}/transaction/dateFilter/withdraw")
    public List<Transaction> getWithdrawTransactions(@PathVariable String accountNumber,
                                             @RequestParam String startDate,
                                             @RequestParam String endDate) throws NotFoundException {
        return accountService.getTransactionsByIntervalAndWithdraw(accountNumber, startDate, endDate);
    }

}
