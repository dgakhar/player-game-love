package com.comeon.group.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.comeon.group.entity.Game;
import com.comeon.group.entity.Love;
import com.comeon.group.entity.Player;
import com.comeon.group.repository.GameRepository;
import com.comeon.group.repository.LoveRepository;
import com.comeon.group.repository.PlayerRepository;

import jakarta.transaction.Transactional;
import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class LoveControllerTest {

	private Player player;
	private Game game;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private PlayerRepository playerRepository;

	@Autowired
	private GameRepository gameRepository;

	@Autowired
	private LoveRepository loveRepository;

	@BeforeEach
	void setup() {
		loveRepository.deleteAll();
		playerRepository.deleteAll();
		gameRepository.deleteAll();

		player = playerRepository.save(new Player(null, "Deepak"));
		game = gameRepository.save(new Game(null, "Cricket"));
	}

	//@formatter:off
    @Test
    void testLoveGameEndpoint() throws Exception {
//    	 String json = "{
//    	                "playerId": 1,
//    	                "gameId": 1,
//    	            }";
    			 
        mockMvc.perform(post("/love")
        				.contentType(MediaType.APPLICATION_JSON)
                        .content(createPayload()))
                .andExpect(status().isOk());

        assertEquals(1, loveRepository.count());
    }

    @Test
    void testUnloveGameEndpoint() throws Exception {
        loveRepository.save(new Love(null, player, game));

        mockMvc.perform(delete("/love")
        		.contentType(MediaType.APPLICATION_JSON)
                .content(createPayload()))
                .andExpect(status().isOk());

        assertEquals(0, loveRepository.count());
    }
  
    //@formatter:on
	private String createPayload() {
		Map<String, Long> jsonMap = new HashMap();
		jsonMap.put("playerId", player.getId());
		jsonMap.put("gameId", game.getId());

		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(jsonMap);
		return json;
	}
}