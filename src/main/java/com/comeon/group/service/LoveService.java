package com.comeon.group.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.comeon.group.entity.Game;
import com.comeon.group.entity.Love;
import com.comeon.group.entity.Player;
import com.comeon.group.exception.AlreadyLoveException;
import com.comeon.group.exception.GameNotExistException;
import com.comeon.group.exception.PlayerNotExistException;
import com.comeon.group.repository.GameRepository;
import com.comeon.group.repository.LoveRepository;
import com.comeon.group.repository.PlayerRepository;

@Service
public class LoveService {

	private static final Logger logger = LoggerFactory.getLogger(LoveService.class);
	
	private static final String LOVE_COUNT = "loveCount";
	private static final String GAME_ID = "gameId";
	
	private final LoveRepository loveRepository;
	private final PlayerRepository playerRepository;
	private final GameRepository gameRepository;

	public LoveService(LoveRepository loveRepository, PlayerRepository playerRepository,
			GameRepository gameRepository) {
		this.loveRepository = loveRepository;
		this.playerRepository = playerRepository;
		this.gameRepository = gameRepository;
	}

	public void loveGame(Long playerId, Long gameId) {
		
		if (logger.isDebugEnabled()) {
			logger.debug("Processing loveGame request for playerId [{}] and gameId [{}]", playerId, gameId);
		}
		
		Player player = playerRepository.findById(playerId)
				.orElseThrow(() -> new PlayerNotExistException("Player does not exist with Id : " + playerId));

		Game game = gameRepository.findById(gameId)
				.orElseThrow(() -> new GameNotExistException("Game does not exist with Id : " + gameId));

		if (loveRepository.findByPlayerIdAndGameId(playerId, gameId).isPresent()) {
			throw new AlreadyLoveException("Player Id : " + playerId + " already loved game Id : " + gameId);
		}

		Love love = new Love();
		love.setPlayer(player);
		love.setGame(game);

		loveRepository.save(love);
	}

	public void unloveGame(Long playerId, Long gameId) {
		validateGame(gameId);
		validatePlayer(playerId);
		
		if (logger.isDebugEnabled()) {
			logger.debug("Processing unloveGame request for playerId [{}] and gameId [{}]", playerId, gameId);
		}
		
		loveRepository.findByPlayerIdAndGameId(playerId, gameId).ifPresent(loveRepository::delete);
	}

	public List<Game> getLovedGamesByPlayer(Long playerId) {
		validatePlayer(playerId);
		return loveRepository.findByPlayerId(playerId).stream().map(Love::getGame).toList();
	}

	public List<Map<String, Object>> getTopLovedGames(int limit) {
		return loveRepository.findMostLovedGames(PageRequest.of(0, limit)).stream().map(row -> {
			Map<String, Object> map = new HashMap<>();
			map.put(GAME_ID, row[0]);
			map.put(LOVE_COUNT, row[1]);
			return map;
		}).toList();
	}

	private void validatePlayer(Long playerId) {
		playerRepository.findById(playerId)
				.orElseThrow(() -> new PlayerNotExistException("Player does not exist with Id: " + playerId));

	}

	private void validateGame(Long gameId) {
		gameRepository.findById(gameId)
				.orElseThrow(() -> new GameNotExistException("Game does not exist with Id: " + gameId));
	}

}
