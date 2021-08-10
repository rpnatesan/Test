package com.bian.bankservice.service;

import com.bian.bankservice.constants.AppConstants;
import com.bian.bankservice.dao.bean.Account;
import com.bian.bankservice.dao.bean.Transaction;
import com.bian.bankservice.dto.AccountDTO;
import com.bian.bankservice.dto.TransactionDTO;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafKaConsumerService {
    @Autowired
    private AccountService accountService;

    private final Logger logger
            = LoggerFactory.getLogger(KafKaConsumerService.class);

    @KafkaListener(topics = AppConstants.TOPIC_NAME_TRANSACTION, groupId = AppConstants.GROUP_ID)
    public void consumeTransaction(TransactionDTO transactionDTO) {
        Transaction transaction = accountService.createTransactionAndUpdateAccount(transactionDTO);
        logger.info(String.format("Transaction created -> %s", transaction));
    }

    @KafkaListener(topics = AppConstants.TOPIC_NAME_ACCOUNT, groupId = AppConstants.GROUP_ID)
    public void consumeAccount(AccountDTO accountDTO) throws NotFoundException {
        Account account = accountService.getAccount(accountDTO.getAccountNumber());
        logger.info(String.format("Account created -> %s", account));
    }

}
