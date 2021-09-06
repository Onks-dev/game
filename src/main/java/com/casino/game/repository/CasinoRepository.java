package com.casino.game.repository;

import com.casino.game.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CasinoRepository extends JpaRepository<Player, Long> {

    public Optional<Player> findByUsername(String username);

    //SELECT transasctions

}
