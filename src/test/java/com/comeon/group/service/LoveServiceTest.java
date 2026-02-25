package com.comeon.group.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.comeon.group.entity.Game;
import com.comeon.group.entity.Player;
import com.comeon.group.repository.GameRepository;
import com.comeon.group.repository.LoveRepository;
import com.comeon.group.repository.PlayerRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class LoveServiceTest {

    @Autowired
    private LoveService loveService;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private LoveRepository loveRepository;

    private Player player1;
    private Game game1;
    private Game game2;

    @BeforeEach
    void setup() {
        loveRepository.deleteAll();
        playerRepository.deleteAll();
        gameRepository.deleteAll();

        player1 = playerRepository.save(new Player(null, "Deepak"));
        game1 = gameRepository.save(new Game(null, "Cricket"));
        game2 = gameRepository.save(new Game(null, "Football"));
    }

    @Test
    void testLoveGame() {
        loveService.loveGame(player1.getId(), game1.getId());
        List<Game> lovedGames = loveService.getLovedGamesByPlayer(player1.getId());

        assertEquals(1, lovedGames.size());
        assertEquals("Cricket", lovedGames.get(0).getName());
    }

    @Test
    void testUnloveGame() {
        loveService.loveGame(player1.getId(), game1.getId());
        loveService.unloveGame(player1.getId(), game1.getId());

        List<Game> lovedGames = loveService.getLovedGamesByPlayer(player1.getId());
        assertTrue(lovedGames.isEmpty());
    }

    @Test
    void testTopLovedGames() {
        Player player2 = playerRepository.save(new Player(null, "Kumar"));
        loveService.loveGame(player1.getId(), game1.getId());
        loveService.loveGame(player2.getId(), game1.getId());
        loveService.loveGame(player1.getId(), game2.getId());

        List<Map<String, Object>> topGames = loveService.getTopLovedGames(2);

        assertEquals(2, topGames.size());
        assertEquals(game1.getId(), topGames.get(0).get("gameId"));
        assertEquals(2L, topGames.get(0).get("loveCount"));
        assertEquals(game2.getId(), topGames.get(1).get("gameId"));
        assertEquals(1L, topGames.get(1).get("loveCount"));
    }
}