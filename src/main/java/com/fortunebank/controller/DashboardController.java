package com.fortunebank.controller;

import com.fortunebank.repository.AccountRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    private final AccountRepository accountRepository;

    public DashboardController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @GetMapping("/stats")
    public ResponseEntity<?> stats() {
        long totalAccounts = accountRepository.count();
        return ResponseEntity.ok(java.util.Map.of("totalAccounts", totalAccounts));
    }
}
