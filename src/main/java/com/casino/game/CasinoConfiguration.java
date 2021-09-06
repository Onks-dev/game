package com.casino.game;

import com.casino.game.entity.Player;
import com.casino.game.repository.CasinoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class CasinoConfiguration {

    @Bean
    CommandLineRunner commandLineRunner(CasinoRepository repository){
        return args -> {
            Player dineo = new Player("Dineo", 50);
            Player onkabetse = new Player("Onkabetse", 100);

            repository.saveAll(Arrays.asList(dineo, onkabetse));
        };
    }
}
