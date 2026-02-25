package com.comeon.group.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.comeon.group.entity.Game;
import com.comeon.group.repository.GameRepository;

//prerequisite controller, it's not the part of the assignment.
// temporary created.
/**
 * @author Deepak
 */

@RestController
public class GameController {

	@Autowired
	private GameRepository gameRepository;

	@PostMapping("/games")
	public Long postPlayer(@RequestBody String name) {
		Game game = new Game();
		game.setName(name);
		gameRepository.save(game);
		return game.getId();
	}

	@DeleteMapping("/games")
	public void deleteAllPlayers() {
		gameRepository.deleteAll();
	}
}
