package com.casino.game.service;

import com.casino.game.entity.Player;
import com.casino.game.entity.Transaction;
import com.casino.game.TransactionType;
import com.casino.game.TransactionsRequest;
import com.casino.game.repository.TransactionRepository;
import com.casino.game.repository.CasinoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CasinoService {

    private CasinoRepository casinoRepository;
    private TransactionRepository transactionRepository;
    private final String password = "swordfish";


    @Autowired
    public CasinoService(CasinoRepository casinoRepository,TransactionRepository transactionRepository){
        this.casinoRepository = casinoRepository;
        this.transactionRepository = transactionRepository;
    }

    public Player getPlayerBalance(Long transactionId, Long playerId){
        Player player = getPlayerById(playerId);
        BigDecimal balance =  player.getBalance();
        Transaction transaction = new Transaction(transactionId, balance, TransactionType.BALANCE);
        transaction.setPlayer(player);
        transactionRepository.save(transaction);
        return player;
    }

    public void wager(Long transactionId, Long playerId, BigDecimal wagerAmount) {
        if(transactionExists(transactionId)){
            return;
        }

        Player player = getPlayerById(playerId);

        BigDecimal balance = player.getBalance();

        if(balance.compareTo(wagerAmount) < 0){
            throw new ResponseStatusException(HttpStatus.REQUEST_TIMEOUT, "Insufficient funds");
        }

        player.decrementBalance(transactionId, wagerAmount,TransactionType.WAGER);

        casinoRepository.save(player);
        Transaction transaction =  new Transaction(transactionId, wagerAmount, TransactionType.WAGER, LocalDateTime.now());
        transaction.setPlayer(player);
        transactionRepository.save(transaction);
    }

    public void win(Long transactionId, Long playerId, BigDecimal winAmount) {
        if(transactionExists(transactionId)){
            return;
        }

        Player player = getPlayerById(playerId);
        player.incrementBalance(winAmount);

        casinoRepository.save(player);
        Transaction transaction =  new Transaction(transactionId, winAmount, TransactionType.WIN, LocalDateTime.now());
        transaction.setPlayer(player);
        transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactions(TransactionsRequest request) {

        List<Transaction> transactions = Collections.emptyList();
        Player player  = getPlayerByName(request.getUsername());
        if(request.getPassword().equals(password)){
            transactions =
                player.getTransactions()
                    .stream()
                    .filter(t -> !t.getType().equals(TransactionType.BALANCE))
                    .sorted(Comparator.comparing(Transaction :: getTransactionTime).reversed())
                    .limit(10)
                    .collect(Collectors.toList());
        }

        return transactions;
    }

    private Boolean transactionExists(Long transactionId){
        return transactionRepository.findById(transactionId).isPresent();
    }

    private Player getPlayerById(Long playerId){
        return  casinoRepository
            .findById(playerId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "player " + playerId + " does not exist"));
    }

    private Player getPlayerByName(String username){
      return  casinoRepository
            .findByUsername(username)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "player " + username + " does not exist"));
    }
}
