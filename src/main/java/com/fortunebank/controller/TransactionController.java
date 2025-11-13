package com.fortunebank.controller;

import com.fortunebank.dto.TransferRequest;
import com.fortunebank.model.Account;
import com.fortunebank.model.Transaction;
import com.fortunebank.repository.AccountRepository;
import com.fortunebank.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;
    private final AccountRepository accountRepository;

    public TransactionController(TransactionService transactionService, AccountRepository accountRepository) {
        this.transactionService = transactionService;
        this.accountRepository = accountRepository;
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestBody TransferRequest req, Authentication authentication) {
        // Basic checks and transfer logic
        if (req.getAmount() == null || req.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            return ResponseEntity.badRequest().body("Amount must be positive");
        }
        Account from = accountRepository.findByAccountNumber(req.getFromAccount()).orElseThrow();
        Account to = accountRepository.findByAccountNumber(req.getToAccount()).orElseThrow();

        if (from.getBalance().compareTo(req.getAmount()) < 0) {
            return ResponseEntity.badRequest().body("Insufficient funds");
        }

        // perform debit and credit through transaction service
        Transaction t1 = transactionService.createTransaction(from, req.getAmount(), "DEBIT", "Transfer to " + to.getAccountNumber());
        Transaction t2 = transactionService.createTransaction(to, req.getAmount(), "CREDIT", "Transfer from " + from.getAccountNumber());

        return ResponseEntity.ok("OK");
    }
}
