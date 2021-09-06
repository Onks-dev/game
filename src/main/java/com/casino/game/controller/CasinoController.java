package com.casino.game.controller;

import com.casino.game.service.CasinoService;
import com.casino.game.entity.Player;
import com.casino.game.entity.Transaction;
import com.casino.game.TransactionsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(path= "casino")
public class CasinoController {

    private CasinoService casinoService;

    @Autowired
    public CasinoController(CasinoService casinoService){
        this.casinoService = casinoService;
    }

    @GetMapping(path = "{playerId}/{transactionId}")
    public Player getPlayerBalance(@PathVariable("transactionId") Long transactionId,
                                   @PathVariable("playerId") Long playerId){
        System.out.println(transactionId);
        System.out.println(playerId);
        //return "Hello WOrld";
      return casinoService.getPlayerBalance(transactionId,playerId);
    }

    @PostMapping(path = "{playerId}/{transactionId}")
    public void wager(@PathVariable("playerId") Long playerId,
                      @PathVariable("transactionId") Long transactionId,
                      @RequestParam(required = false) String wagerAmount,
                      @RequestParam(required = false) String winAmount){

        if(wagerAmount != null && !wagerAmount.isEmpty()){
            casinoService.wager(transactionId, playerId, getDecimal(wagerAmount));
        } else if(winAmount != null && !winAmount.isEmpty()){
            casinoService.win(transactionId, playerId, getDecimal(winAmount));
        }


    }

    @GetMapping(path = "transactions")
    public List<Transaction> getTransactions(@RequestBody TransactionsRequest request){
        System.out.println("Hello");
        System.out.println(request);

        return casinoService.getTransactions(request);

    }

    //Making an assumption that the user always enters the correct parameter value;
    private static BigDecimal getDecimal(String amount){
        return new BigDecimal(Integer.parseInt(amount));
    }

}
