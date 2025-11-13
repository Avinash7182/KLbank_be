package com.fortunebank.service;

import com.fortunebank.model.Account;
import com.fortunebank.model.User;
import com.fortunebank.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount(User owner, String accountNumber, BigDecimal initialBalance) {
        Account a = new Account(accountNumber, initialBalance != null ? initialBalance : BigDecimal.ZERO, owner);
        owner.getAccounts().add(a);
        return accountRepository.save(a);
    }

    public Optional<Account> findByAccountNumber(String accNum) { return accountRepository.findByAccountNumber(accNum); }

    public List<Account> findAll() { return accountRepository.findAll(); }

    @Transactional
    public void debit(Account account, BigDecimal amount) {
        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);
    }

    @Transactional
    public void credit(Account account, BigDecimal amount) {
        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);
    }
}
