package com.fortunebank.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Instant timestamp = Instant.now();

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String type;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    private String note;

    public Transaction() {}

    public Transaction(BigDecimal amount, String type, Account account, String note) {
        this.amount = amount;
        this.type = type;
        this.account = account;
        this.note = note;
    }

    public Long getId() { return id; }
    public Instant getTimestamp() { return timestamp; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public Account getAccount() { return account; }
    public void setAccount(Account account) { this.account = account; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}
