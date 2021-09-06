package com.casino.game.entity;



import com.casino.game.TransactionType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
public class Player {

    @Id
    @GeneratedValue
    private long id;
    private String username;
    private BigDecimal balance;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "player_id", referencedColumnName = "id")
    @JsonIgnore
    private List<Transaction> transactions;

    Player(){}

    public Player(String username, int amount){
        this.username = username;
        this.balance = new BigDecimal(amount);
    }

    public String getUsername() {
        return username;
    }


    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }


    public void decrementBalance(Long transactionId, BigDecimal wagerAmount, TransactionType type) {
        BigDecimal newBalance = this.balance.subtract(wagerAmount);
        setBalance(newBalance);
    }

    public void incrementBalance(BigDecimal winAmount){
        BigDecimal newBalance = this.balance.add(winAmount);
        setBalance(newBalance);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Player player = (Player) o;
        return id == player.id &&
            Objects.equals(username, player.username) &&
            Objects.equals(balance, player.balance) &&
            Objects.equals(transactions, player.transactions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, balance, transactions);
    }

    @Override
    public String toString() {
        return "Player{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", balance=" + balance;
    }
}
