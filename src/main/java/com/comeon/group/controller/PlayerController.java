package com.comeon.group.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.comeon.group.entity.Player;
import com.comeon.group.repository.PlayerRepository;

// prerequisite controller, it's not the part of the assignment.

/**
 * @author Deepak
 */

@RestController
public class PlayerController {

	@Autowired
	private PlayerRepository playerRepository;

	@PostMapping("/players")
	public Long postPlayer(@RequestBody String name) {
		Player player = new Player();
		player.setName(name);
		playerRepository.save(player);
		return player.getId();
	}

	@GetMapping("/players")
	public List<Player> getPlayers() {
		return playerRepository.findAll();
	}

	@DeleteMapping("/players")
	public void deleteAllPlayers() {
		playerRepository.deleteAll();
	}
}
