package com.comeon.group.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.comeon.group.entity.Game;
import com.comeon.group.repository.GameRepository;
import com.comeon.group.service.LoveService;

/**
 * @author Deepak
 */

@RestController
public class LoveController {

	private static final Logger logger = LoggerFactory.getLogger(LoveController.class);

	private final LoveService loveService;
	private final GameRepository gameRepository;

	public LoveController(LoveService loveService, GameRepository gameRepository) {
		this.loveService = loveService;
		this.gameRepository = gameRepository;
	}

	@PostMapping("/love")
	public void loveGame(@RequestBody PlayerGamePayload payload) {
		logger.info("Executing love Game request");
		loveService.loveGame(payload.playerId, payload.gameId);
	}

	@DeleteMapping("/love")
	public void unloveGame(@RequestBody PlayerGamePayload payload) {
		logger.info("Executing unlove Game request");
		loveService.unloveGame(payload.playerId, payload.gameId);
	}

	@GetMapping("/players/{playerId}/loves")
	public List<Game> getLovedGames(@PathVariable Long playerId) {
		logger.info("Executing getLovedGames request");
		return loveService.getLovedGamesByPlayer(playerId);
	}

	@GetMapping("/games/top")
	public List<Map<String, Object>> getTopLovedGames(@RequestParam(defaultValue = "5") int limit) {
		logger.info("Executing getTopLovedGames request");
		return loveService.getTopLovedGames(limit);
	}

	@GetMapping("/games")
	public List<Game> getAllGames() {
		logger.info("Executing getAllGames request");
		return gameRepository.findAll();
	}

	public record PlayerGamePayload(Long playerId, Long gameId) {
	}
}
