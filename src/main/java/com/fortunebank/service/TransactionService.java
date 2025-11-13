package com.fortunebank.service;

import com.fortunebank.model.Account;
import com.fortunebank.model.Transaction;
import com.fortunebank.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    public TransactionService(TransactionRepository transactionRepository, AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
    }

    @Transactional
    public Transaction createTransaction(Account account, BigDecimal amount, String type, String note) {
        Transaction t = new Transaction(amount, type, account, note);
        if ("DEBIT".equals(type)) {
            accountService.debit(account, amount);
        } else if ("CREDIT".equals(type)) {
            accountService.credit(account, amount);
        }
        return transactionRepository.save(t);
    }

    public List<Transaction> getByAccount(Long accountId) {
        return transactionRepository.findByAccountIdOrderByTimestampDesc(accountId);
    }
}
