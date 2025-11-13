package com.fortunebank.controller;

import com.fortunebank.dto.CreateAccountRequest;
import com.fortunebank.model.Account;
import com.fortunebank.model.User;
import com.fortunebank.repository.UserRepository;
import com.fortunebank.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService accountService;
    private final UserRepository userRepository;

    public AccountController(AccountService accountService, UserRepository userRepository) {
        this.accountService = accountService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<List<Account>> allAccounts() {
        return ResponseEntity.ok(accountService.findAll());
    }

    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody CreateAccountRequest req, Authentication authentication) {
        String username = (String) authentication.getPrincipal();
        User user = userRepository.findByUsername(username).orElseThrow();
        Account a = accountService.createAccount(user, req.getAccountNumber(), req.getInitialBalance());
        return ResponseEntity.ok(a);
    }
}
