package com.fortunebank.util;

import com.fortunebank.model.Account;
import com.fortunebank.model.User;
import com.fortunebank.repository.AccountRepository;
import com.fortunebank.repository.UserRepository;
import com.fortunebank.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserService userService;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public DataLoader(UserService userService, AccountRepository accountRepository, UserRepository userRepository) {
        this.userService = userService;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // create sample users
        var u1 = userService.createUser("alice", "password");
        var u2 = userService.createUser("bob", "password");

        // create accounts for them
        accountRepository.save(new Account("ACC1001", new BigDecimal("10000"), u1));
        accountRepository.save(new Account("ACC2001", new BigDecimal("2500"), u2));
    }
}
