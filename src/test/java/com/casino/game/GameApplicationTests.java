package com.casino.game;

import com.casino.game.entity.Player;
import com.casino.game.repository.CasinoRepository;
import com.casino.game.service.CasinoService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class GameApplicationTests {

	@Autowired
	private CasinoService casinoService;

	@Autowired
	private CasinoRepository casinoRepo;

	@Before("")
	void before(){

	}

	@Test
	void testGetPresentPlayer(){
		Player player = new Player("Dineo", 50);
		casinoRepo.save(player);
		Player actual = casinoService.getPlayerBalance(1L, 1L);
		assertEquals(player.getUsername(), actual.getUsername());
	}

	@Test
	void testWager(){
		Player player = new Player("Onkabetse", 100);
		casinoRepo.save(player);
		casinoService.wager(100L, 2L, new BigDecimal(20));

		Player actual = casinoService.getPlayerBalance(200L, 2L);

		assertEquals( "80.00" , actual.getBalance().toString());

	}

	@Test
	void testWin(){

		Player player = new Player("Tumi", 200);
		casinoRepo.save(player);
		casinoService.win(200L, 3L, new BigDecimal(20));

		Player actual = casinoService.getPlayerBalance(200L, 3L);

		assertEquals( "220.00" , actual.getBalance().toString());

	}

	//Extend tests



}
