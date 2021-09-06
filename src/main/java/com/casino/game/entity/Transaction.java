package com.casino.game.entity;

import com.casino.game.TransactionType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Transaction {

    @Id
    private long id;
    private BigDecimal amount;
    private TransactionType type;
    private LocalDateTime transactionTime;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "player_id", nullable = false)
    @JsonIgnore
    private Player player;

    public Transaction(){}

    public Transaction(long id, BigDecimal amount, TransactionType type) {
        this.id = id;
        this.amount = amount;
        this.type = type;
    }

    public Transaction(long id, BigDecimal amount, TransactionType type, LocalDateTime transactionTime) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.transactionTime = transactionTime;
    }


    public TransactionType getType() {
        return type;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }

}
